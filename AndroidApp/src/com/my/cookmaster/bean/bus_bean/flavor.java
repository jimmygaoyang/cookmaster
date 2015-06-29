package com.my.cookmaster.bean.bus_bean;

import com.my.cookmaster.bean.pro_bean.Stuff;
import com.my.cookmaster.view.listview.viewprovider.IItemBean;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.flavorProvider;

public class flavor implements IItemBean{

	private Stuff flavor;
	@Override
	public Class<? extends IViewProvider> getViewProviderClass() {
		// TODO Auto-generated method stub
		return flavorProvider.class;
	}
	public Stuff getFlavor() {
		return flavor;
	}
	public void setFlavor(Stuff flavor) {
		this.flavor = flavor;
	}

}
