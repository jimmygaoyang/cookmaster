package com.my.cookmaster.bus.protocol;

import java.util.List;

import android.bluetooth.BluetoothDevice;

import com.my.cookmaster.bus.BTtranse;

public abstract class Command {
	public static int SUCCESS = 1;
	public static int FALURE = 0;
	protected BaseBean baseBean = null;
	private BTtranse bttrans =new BTtranse();
	public List<BluetoothDevice> devList =null;
	public BluetoothDevice selectedDev = null;
	private String transErr;
	
	
	public Command(BaseBean bean)
	{
		baseBean = bean;
		bttrans.open();
		devList = bttrans.getCookDevice();
		if(devList.size()==0)
			selectedDev = null;
		else
			selectedDev = devList.get(0);
	}
	
	
	abstract public byte[] construct();
	abstract public BaseBean parse(byte[] rspDat);
	
	public BaseBean getRspBean()
	{
		return baseBean;
	}
	
	
	
	public int execute()
	{
		byte [] sendDat = construct();
		if(selectedDev == null)
		{
			setTransErr("未发现调料盒蓝牙连接");
			return FALURE;
		}

		int res = bttrans.transeWithDevice(selectedDev, sendDat);
		if(res == 1)
		{
			baseBean = parse(bttrans.getRspData());
			return SUCCESS;
		}
		else
		{
			setTransErr("通信错误");
		}
			
			return FALURE;
	}


	public String getTransErr() {
		return transErr;
	}


	public void setTransErr(String transErr) {
		this.transErr = transErr;
	}
	
}
