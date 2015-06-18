package com.my.cookmaster.bean.bus_bean;

import com.my.cookmaster.bean.pro_bean.Stuff;
import com.my.cookmaster.view.listview.viewprovider.IItemBean;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.mainStuffProvider;

public class mainStuff implements IItemBean{

	private Stuff mainStuff;
	@Override
	public Class<? extends IViewProvider> getViewProviderClass() {
		// TODO Auto-generated method stub
		return mainStuffProvider.class;
	}
	public Stuff getMainStuff() {
		return mainStuff;
	}
	public void setMainStuff(Stuff mainStuff) {
		this.mainStuff = mainStuff;
	}
}
