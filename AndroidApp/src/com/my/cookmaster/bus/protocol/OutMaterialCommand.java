package com.my.cookmaster.bus.protocol;

public class OutMaterialCommand extends Command{

	private OutMaterialBean sendBean;
	
	public OutMaterialCommand(BaseBean bean) {
		super(bean);
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte[] construct() {
		// TODO Auto-generated method stub
		int i=0;
		sendBean = (OutMaterialBean)baseBean;
		byte[] sendBuf = new byte[13];
		System.arraycopy(sendBean.getMacNum().getBytes(), 0, sendBuf, i, sendBean.getMacNum().length());
		i+=10;
		sendBuf[i++] = (byte)sendBean.getCmdId();
		int IntAmount = Integer.parseInt(sendBean.getAmount());
		sendBuf[i++]=(byte)((IntAmount&0xFF00)>>8);
		sendBuf[i++]=(byte)((IntAmount&0x00FF));
		return sendBuf;
		
	}

	@Override
	public BaseBean parse(byte[] rspDat) {
		// TODO Auto-generated method stub
		int i=0;
		byte [] macNum = new byte[10];
		System.arraycopy(rspDat, 0, macNum, 0, 10);
		OutMaterialRspBean bean = new OutMaterialRspBean();
		bean.setMacNum(new String(macNum));
		bean.setRspCode(rspDat[10]);
		return bean;
		
	}

}
