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

/** ����ͨ�ſ��� */
public class BTtranse {


	//��ȡ�����������������������豸
	private BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter();
	//SPP����UUID��
	private final static String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB";
	//����buf
	private static byte[] bleBuf = new byte[1024];
	private InputStream is;    //������������������������
	private int timeout = 10;// ��ʱ10��100����
	BluetoothDevice _device = null;     //�����豸
	BluetoothSocket _socket = null;      //����ͨ��socket
	boolean bRun = true;
	boolean bThread = false;
	String erroStr = null;
   /** ��ȡ�����󶨵ĳ����豸������һ��BlueToothDevice ���� */
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
   
   /** ���ƶ��豸ͨ�Ž���һ��
    * 
    * @param dev Ҫͨ�ŵ��豸
    * @param sendBuf ���͵������� */
   public int transeWithDevice(BluetoothDevice dev, byte[] sendBuf) {
      // TODO: implement
	   int sendPos = 0;
       // �÷���ŵõ�socket
       try{
       	_socket = dev.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
       }catch(IOException e){
    	erroStr = "����socketʧ�ܣ�";
    	return -1;
       }
       //����socket
		try {
			_socket.connect();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			closeSocket();
			return -1;
		}
   // ��������
       //��������ͷ
       bleBuf[0]=0x1B;
       bleBuf[1]=0x10;
       //�������ݳ���
       bleBuf[2]=(byte)((sendBuf.length&0xFF00)>>8);
       bleBuf[3]=(byte)((sendBuf.length&0x00FF));
       sendPos+=4;
       //�������
       System.arraycopy(sendBuf, 0, bleBuf, 4, sendBuf.length);
       sendPos+=sendBuf.length;
       //crcУ��
       byte[] sendcrc = Alogrithm.crc16(bleBuf, sendPos);
       bleBuf[sendPos++]= sendcrc[0];
       bleBuf[sendPos++]= sendcrc[1];
       
       // �������������
       OutputStream os;
		try {
			os = _socket.getOutputStream();
			os.write(bleBuf,0,sendPos);
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			closeSocket();
			e1.printStackTrace();
		}   

		//��ʼ����
	   try{
	  		is = _socket.getInputStream();   //�õ���������������
	  		}catch(IOException e){
	  			Log.d("cook", "��������ʧ��");
	  			erroStr = "��������ʧ��";
	  			closeSocket();
	  			return -1;
	  		}
		
	   //��ȡ����ͷ
	   int recPos = 0;
	   if(getRecLen(recPos,2)!= 0)
	   {
		   Log.d("cook", "ȡ���ݰ�ͷ����");
		   closeSocket();
		   return -1;
	   }
	   //ƥ������ͷ
	   if(bleBuf[0]!=0x1B || bleBuf[1]!=0x10)
	   {
		   Log.d("cook", "���ݰ�ͷ����");
		   erroStr = "���ݰ�ͷ����";
		   closeSocket();
		   return -1;
	   }
	   recPos+=2;
	   //��ȡ���ݳ���
	   if(getRecLen(recPos,2)!= 0)
	   {
		   Log.d("cook", "��ȡ���ݳ��ȴ���");
		   erroStr = "��ȡ���ݳ��ȴ���";
		   closeSocket();
		   return -1;
	   }
	   int datLen = bleBuf[recPos]*256+bleBuf[recPos+1];
	   recPos+=2;
	   //��ȡ���ݿ�
	   if(getRecLen(recPos,datLen)!= 0)
	   {
		   Log.d("cook", "��ȡ���ݴ���");
		   erroStr = "��ȡ���ݴ���";
		   closeSocket();
		   return -1;
	   }
	   recPos+=datLen;
	   //��ȡ���crc��λ
	   if(getRecLen(recPos,2)!= 0)
	   {
		   Log.d("cook", "��ȡcrcʧ��");
		   erroStr = "��crcʧ��";
		   closeSocket();
		   return -1;
	   }
	   //У��CRC
	   byte[] crc = Alogrithm.crc16(bleBuf, recPos);
	   if(crc[0]!=bleBuf[recPos]  || crc[1]!=bleBuf[recPos+1])
	   {
		   Log.d("cook", "crcУ�����");
		   erroStr = "crcУ�����";
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
   
   //��ָ����ʱʱ���ڽ���ָ������
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
					erroStr = "�������ʱ";
					return -1;
				}		
		   }
	   }
	   return 0;
   }
   
   /** ������ */
   public int open() {
      // TODO: implement
       //����򿪱��������豸���ɹ�����ʾ��Ϣ����������
       if (_bluetooth == null){
       		erroStr = "�޷����ֻ���������ȷ���ֻ��Ƿ����������ܣ�";
            return -1;
       } 
       // �����豸���Ա�����  
      new Thread(){
   	   public void run(){
   		   if(_bluetooth.isEnabled()==false){
       		_bluetooth.enable();
   		   }
   	   }   	   
      }.start(); 
      
      return 0;
   }
   
   /** ��ȡ�ɹ����ص����� */
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
   /** ��ȡ������Ϣ*/
   public String getErroStr() {
      // TODO: implement
      return this.erroStr;
   }

}
