package com.my.cookmaster.bean.bus_bean;

import com.my.cookmaster.bean.pro_bean.Stuff;
import com.my.cookmaster.view.listview.viewprovider.IItemBean;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.subStuffProvider;

public class subStuff implements IItemBean{

	private Stuff subStuff;
	@Override
	public Class<? extends IViewProvider> getViewProviderClass() {
		// TODO Auto-generated method stub
		return subStuffProvider.class;
	}
	public Stuff getSubStuff() {
		return subStuff;
	}
	public void setSubStuff(Stuff subStuff) {
		this.subStuff = subStuff;
	}

}
