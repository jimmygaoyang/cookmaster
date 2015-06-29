package com.my.cookmaster.bean.pro_bean;

public class loginRspBean {
	private Long userID;
	private String state;
	private String erroInfo;
	public Long getUserID() {
		return userID;
	}
	public void setUserID(Long userID) {
		this.userID = userID;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getErroInfo() {
		return erroInfo;
	}
	public void setErroInfo(String erroInfo) {
		this.erroInfo = erroInfo;
	}
}
