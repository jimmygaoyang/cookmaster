package com.my.cookmaster.bean.bus_bean;

import com.my.cookmaster.view.listview.viewprovider.IItemBean;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.menuIntroProider;

public class menuIntro implements IItemBean{

	private String avator;
	private String usrName;
	private String menuName;
	private String pubTime;
	private String menuInro;
	@Override
	public Class<? extends IViewProvider> getViewProviderClass() {
		// TODO Auto-generated method stub
		return menuIntroProider.class;
	}
	public String getAvator() {
		return avator;
	}
	public void setAvator(String avator) {
		this.avator = avator;
	}
	public String getUsrName() {
		return usrName;
	}
	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getPubTime() {
		return pubTime;
	}
	public void setPubTime(String pubTime) {
		this.pubTime = pubTime;
	}
	public String getMenuInro() {
		return menuInro;
	}
	public void setMenuInro(String menuInro) {
		this.menuInro = menuInro;
	}

}
