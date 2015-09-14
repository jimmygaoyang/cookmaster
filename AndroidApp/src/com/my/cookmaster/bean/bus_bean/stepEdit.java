package com.my.cookmaster.bean.bus_bean;

import com.my.cookmaster.view.listview.viewprovider.IItemBean;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.StepEditProvider;

public class stepEdit implements IItemBean{
	private String photoURL;
	private int stepNo;
	private String Intro;
	@Override
	public Class<? extends IViewProvider> getViewProviderClass() {
		// TODO Auto-generated method stub
		return StepEditProvider.class;
	}
	public String getPhotoURL() {
		return photoURL;
	}
	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}
	public int getStepNo() {
		return stepNo;
	}
	public void setStepNo(int stepNo) {
		this.stepNo = stepNo;
	}
	public String getIntro() {
		return Intro;
	}
	public void setIntro(String intro) {
		Intro = intro;
	}
}
