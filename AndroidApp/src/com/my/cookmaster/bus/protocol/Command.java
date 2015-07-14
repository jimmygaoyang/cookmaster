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
		int res = bttrans.transeWithDevice(selectedDev, sendDat);
		if(res == 1)
		{
			baseBean = parse(bttrans.getRspData());
			return 1;
		}
		else
		{
			setTransErr("Í¨ÐÅ´íÎó");
		}
			
			return 0;
	}


	public String getTransErr() {
		return transErr;
	}


	public void setTransErr(String transErr) {
		this.transErr = transErr;
	}
	
}
