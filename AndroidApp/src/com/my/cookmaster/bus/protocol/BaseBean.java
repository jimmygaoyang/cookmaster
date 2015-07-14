package com.my.cookmaster.bus.protocol;

public class BaseBean {
	private String macNum;
	private int rspCode;
	public String getMacNum() {
		return macNum;
	}
	public void setMacNum(String macNum) {
		this.macNum = macNum;
	}
	private String transCodeToStr(int code)
	{
		String errStr=null;
		switch(code)
		{
			case 0:
				errStr = "²Ù×÷³É¹¦";
				break;
			default:
				break;	
		}
		return errStr;
	}
	public String getRspCode() {
		return transCodeToStr(rspCode);
	}
	public void setRspCode(int rspCode) {
		this.rspCode = rspCode;
	}
	
}
