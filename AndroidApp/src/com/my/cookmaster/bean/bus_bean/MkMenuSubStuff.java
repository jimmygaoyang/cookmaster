package com.my.cookmaster.bean.bus_bean;

import com.my.cookmaster.bean.pro_bean.MaterialBean;
import com.my.cookmaster.view.listview.viewprovider.IItemBean;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.MkMenuMainStuffProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.SubStuffEditProvider;

public class MkMenuSubStuff implements IItemBean{
	private MaterialBean material;
	private String amount;
	private int suffIndex;
	@Override
	public Class<? extends IViewProvider> getViewProviderClass() {
		// TODO Auto-generated method stub
		return SubStuffEditProvider.class;
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
	public int getSuffIndex() {
		return suffIndex;
	}
	public void setSuffIndex(int suffIndex) {
		this.suffIndex = suffIndex;
	}

}
