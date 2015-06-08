/***********************************************************************
 * Module:  BTtranse.java
 * Author:  Thinkpad
 * Purpose: Defines the Class BTtranse
 ***********************************************************************/

package com.my.cookmaster.bus;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

import com.my.cookmaster.alogrithm.Alogrithm;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

/** 蓝牙通信控制 */
public class BTtranse {


	//获取本地蓝牙适配器，即蓝牙设备
	private BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter();
	//SPP服务UUID号
	private final static String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB";
	//数据buf
	private static byte[] bleBuf = new byte[1024];
	private InputStream is;    //输入流，用来接收蓝牙数据
	private int timeout = 10;// 延时10个100毫秒
	BluetoothDevice _device = null;     //蓝牙设备
	BluetoothSocket _socket = null;      //蓝牙通信socket
	boolean bRun = true;
	boolean bThread = false;
	String erroStr = null;
   /** 获取蓝牙绑定的厨具设备，返回一个BlueToothDevice 链表 */
   public List<BluetoothDevice> getCookDevice() {
      // TODO: implement
	   Set<BluetoothDevice> bondedDev = _bluetooth.getBondedDevices();
	   List<BluetoothDevice> devList = new ArrayList<BluetoothDevice>();
	   Iterator<BluetoothDevice> it = bondedDev.iterator();  
	   while (it.hasNext()) {  
		 BluetoothDevice dev = it.next();  
		 if(dev.getName().equals("HC-05"))
		 {
			 devList.add(dev);
		 } 
	   } 
	   return devList;
	   
   }
   
   /** 与制定设备通信交互一次
    * 
    * @param dev 要通信的设备
    * @param sendBuf 发送的数据组 */
   public int transeWithDevice(BluetoothDevice dev, byte[] sendBuf) {
      // TODO: implement
	   int sendPos = 0;
       // 用服务号得到socket
       try{
       	_socket = dev.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
       }catch(IOException e){
    	erroStr = "创建socket失败！";
    	return -1;
       }
       //连接socket
		try {
			_socket.connect();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			closeSocket();
			return -1;
		}
   // 发送数据
       //发送数据头
       bleBuf[0]=0x1B;
       bleBuf[1]=0x10;
       //发送数据长度
       bleBuf[2]=(byte)((sendBuf.length&0xFF00)>>8);
       bleBuf[3]=(byte)((sendBuf.length&0x00FF));
       sendPos+=4;
       //填充数据
       System.arraycopy(sendBuf, 0, bleBuf, 4, sendBuf.length);
       sendPos+=sendBuf.length;
       //crc校验
       byte[] sendcrc = Alogrithm.crc16(bleBuf, sendPos);
       bleBuf[sendPos++]= sendcrc[0];
       bleBuf[sendPos++]= sendcrc[1];
       
       // 蓝牙连接输出流
       OutputStream os;
		try {
			os = _socket.getOutputStream();
			os.write(bleBuf,0,sendPos);
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			closeSocket();
			e1.printStackTrace();
		}   

		//开始接收
	   try{
	  		is = _socket.getInputStream();   //得到蓝牙数据输入流
	  		}catch(IOException e){
	  			Log.d("cook", "接收数据失败");
	  			erroStr = "接收数据失败";
	  			closeSocket();
	  			return -1;
	  		}
		
	   //读取数据头
	   int recPos = 0;
	   if(getRecLen(recPos,2)!= 0)
	   {
		   Log.d("cook", "取数据包头错误");
		   closeSocket();
		   return -1;
	   }
	   //匹配数据头
	   if(bleBuf[0]!=0x1B || bleBuf[1]!=0x10)
	   {
		   Log.d("cook", "数据包头错误");
		   erroStr = "数据包头错误";
		   closeSocket();
		   return -1;
	   }
	   recPos+=2;
	   //读取数据长度
	   if(getRecLen(recPos,2)!= 0)
	   {
		   Log.d("cook", "读取数据长度错误");
		   erroStr = "读取数据长度错误";
		   closeSocket();
		   return -1;
	   }
	   int datLen = bleBuf[recPos]*256+bleBuf[recPos+1];
	   recPos+=2;
	   //读取数据块
	   if(getRecLen(recPos,datLen)!= 0)
	   {
		   Log.d("cook", "读取数据错误");
		   erroStr = "读取数据错误";
		   closeSocket();
		   return -1;
	   }
	   recPos+=datLen;
	   //读取最后crc两位
	   if(getRecLen(recPos,2)!= 0)
	   {
		   Log.d("cook", "读取crc失败");
		   erroStr = "读crc失败";
		   closeSocket();
		   return -1;
	   }
	   //校验CRC
	   byte[] crc = Alogrithm.crc16(bleBuf, recPos);
	   if(crc[0]!=bleBuf[recPos]  || crc[1]!=bleBuf[recPos+1])
	   {
		   Log.d("cook", "crc校验错误");
		   erroStr = "crc校验错误";
		   closeSocket();
		   return -1;
	   }
	   recPos+=2;
		for(int i = 0;i < recPos; i++)
		{
			Log.d("cook", String.format("%02x", bleBuf[i]));
		}
	   closeSocket();

	   return 1;
	   
   }
   
   //在指定超时时间内接收指定数据
   private int getRecLen(int pos,int len)
   {
	   int tmpRecLen=0;
	   int recLen = 0;
	   while(recLen < len)
	   {
		   try {
			tmpRecLen = is.read(bleBuf,pos,len-recLen);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   pos +=tmpRecLen;
		   recLen +=tmpRecLen;
		   if(tmpRecLen == 0)
		   {
			   try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			   timeout--;
				if(timeout < 0)
				{
					erroStr = "接收命令超时";
					return -1;
				}		
		   }
	   }
	   return 0;
   }
   
   /** 打开蓝牙 */
   public int open() {
      // TODO: implement
       //如果打开本地蓝牙设备不成功，提示信息，结束程序
       if (_bluetooth == null){
       		erroStr = "无法打开手机蓝牙，请确认手机是否有蓝牙功能！";
            return -1;
       } 
       // 设置设备可以被搜索  
      new Thread(){
   	   public void run(){
   		   if(_bluetooth.isEnabled()==false){
       		_bluetooth.enable();
   		   }
   	   }   	   
      }.start(); 
      
      return 0;
   }
   
   /** 获取成功返回的数据 */
   public int getRspData() {
      // TODO: implement
      return 0;
   }
   
   private void closeSocket()
   {
   	try {
		is.close();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
   	try {
		_socket.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   	_socket = null;
	   return;
   }
   /** 获取错误信息*/
   public String getErroStr() {
      // TODO: implement
      return this.erroStr;
   }

}
