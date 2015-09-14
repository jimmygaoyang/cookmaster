package com.my.cookmaster.bean.bus_bean;

import com.my.cookmaster.view.listview.viewprovider.IItemBean;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.menuCoverEditProvider;

public class menuCoverEdit implements IItemBean{

	private String coverUrl;
	@Override
	public Class<? extends IViewProvider> getViewProviderClass() {
		// TODO Auto-generated method stub
		return menuCoverEditProvider.class;
	}
	public String getCoverUrl() {
		return coverUrl;
	}
	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}

}
