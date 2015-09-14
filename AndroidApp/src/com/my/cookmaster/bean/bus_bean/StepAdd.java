package com.my.cookmaster.bean.bus_bean;

import com.my.cookmaster.view.listview.viewprovider.IItemBean;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.StepEditProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.stepAddProvider;

public class StepAdd implements IItemBean{


	@Override
	public Class<? extends IViewProvider> getViewProviderClass() {
		// TODO Auto-generated method stub
		return stepAddProvider.class;
	}
	
	
}
