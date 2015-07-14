package com.my.cookmaster.bean.bus_bean;

import com.my.cookmaster.view.listview.viewprovider.IItemBean;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.BoxOperateProvider;

public class BoxOperate implements IItemBean{
	private String flavorName;
	private String flavorBrand;
	private String boxMac;
	@Override
	public Class<? extends IViewProvider> getViewProviderClass() {
		// TODO Auto-generated method stub
		return BoxOperateProvider.class;
	}
	public String getFlavorName() {
		return flavorName;
	}
	public void setFlavorName(String flavorName) {
		this.flavorName = flavorName;
	}
	public String getFlavorBrand() {
		return flavorBrand;
	}
	public void setFlavorBrand(String flavorBrand) {
		this.flavorBrand = flavorBrand;
	}
	public String getBoxMac() {
		return boxMac;
	}
	public void setBoxMac(String boxMac) {
		this.boxMac = boxMac;
	}
}
