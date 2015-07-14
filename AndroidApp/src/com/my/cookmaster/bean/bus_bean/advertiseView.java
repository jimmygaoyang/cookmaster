package com.my.cookmaster.bean.bus_bean;

import java.util.ArrayList;

import com.my.cookmaster.view.listview.viewprovider.IItemBean;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.advertiseProvider;

public class advertiseView implements IItemBean{
	private ArrayList<String> imageUrlList = new ArrayList<String>();
	private ArrayList<String> linkUrlArray= new ArrayList<String>();
	private ArrayList<String> titleList= new ArrayList<String>();
	@Override
	public Class<? extends IViewProvider> getViewProviderClass() {
		// TODO Auto-generated method stub
		return advertiseProvider.class;
	}
	public ArrayList<String> getImageUrlList() {
		return imageUrlList;
	}
	public void setImageUrlList(ArrayList<String> imageUrlList) {
		this.imageUrlList = imageUrlList;
	}
	public ArrayList<String> getLinkUrlArray() {
		return linkUrlArray;
	}
	public void setLinkUrlArray(ArrayList<String> linkUrlArray) {
		this.linkUrlArray = linkUrlArray;
	}
	public ArrayList<String> getTitleList() {
		return titleList;
	}
	public void setTitleList(ArrayList<String> titleList) {
		this.titleList = titleList;
	}
	
}
