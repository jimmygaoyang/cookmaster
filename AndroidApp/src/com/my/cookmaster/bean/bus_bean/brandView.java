package com.my.cookmaster.bean.bus_bean;

import com.my.cookmaster.view.listview.viewprovider.IItemBean;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.brandProvider;

public class brandView implements IItemBean{

	private String brandName;
	private String corporName;
	private String brandRef;
	
	@Override
	public Class<? extends IViewProvider> getViewProviderClass() {
		// TODO Auto-generated method stub
		return brandProvider.class;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCorporName() {
		return corporName;
	}

	public void setCorporName(String corporName) {
		this.corporName = corporName;
	}

	public String getBrandRef() {
		return brandRef;
	}

	public void setBrandRef(String brandRef) {
		this.brandRef = brandRef;
	}

}
