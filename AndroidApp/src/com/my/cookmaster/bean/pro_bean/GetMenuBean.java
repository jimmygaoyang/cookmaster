package com.my.cookmaster.bean.pro_bean;

import com.alibaba.fastjson.annotation.JSONField;

public class GetMenuBean {

	private String CmdID ="4";//Э���
	private String MenuID;//���ױ��
//	@JSONField(name="CmdID")
	public String getCmdID() {
		return CmdID;
	}
	public void setCmdID(String cmdID) {
		this.CmdID = cmdID;
	}
//	@JSONField(name="MenuID")
	public String getMenuID() {
		return MenuID;
	}
	public void setMenuID(String menuID) {
		this.MenuID = menuID;
	}
}
