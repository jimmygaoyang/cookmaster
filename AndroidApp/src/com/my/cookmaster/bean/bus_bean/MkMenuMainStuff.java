package com.my.cookmaster.bean.bus_bean;

import com.my.cookmaster.bean.pro_bean.MaterialBean;
import com.my.cookmaster.view.listview.viewprovider.IItemBean;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.MkMenuMainStuffProvider;

public class MkMenuMainStuff implements IItemBean{
	private MaterialBean material;
	private String amount;
	@Override
	public Class<? extends IViewProvider> getViewProviderClass() {
		// TODO Auto-generated method stub
		return MkMenuMainStuffProvider.class;
	}
	public MaterialBean getMaterial() {
		return material;
	}
	public void setMaterial(MaterialBean material) {
		this.material = material;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}

}
