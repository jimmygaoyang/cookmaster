package com.my.cookmaster.bean.bus_bean;

import com.my.cookmaster.view.listview.viewprovider.IItemBean;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.ImageViewProvider;

public class ImageOder implements IItemBean {

	public String ImgURL;
	
	public String getImgURL() {
		return ImgURL;
	}
	public void setImgURL(String ImgURL) {
		this.ImgURL = ImgURL;
	}
	@Override
	public Class<? extends IViewProvider> getViewProviderClass() {
		// TODO Auto-generated method stub
		return ImageViewProvider.class;
	}

}
