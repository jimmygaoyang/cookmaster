package com.my.cookmaster.bean.bus_bean;

import com.my.cookmaster.view.listview.viewprovider.IItemBean;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.menuCoverProvider;

public class menuCover  implements IItemBean{

	private String coverUrl;
	private Long favorCont;
	@Override
	public Class<? extends IViewProvider> getViewProviderClass() {
		// TODO Auto-generated method stub
		return menuCoverProvider.class;
	}
	public String getCoverUrl() {
		return coverUrl;
	}
	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
	public Long getFavorCont() {
		return favorCont;
	}
	public void setFavorCont(Long favorCont) {
		this.favorCont = favorCont;
	}

}
