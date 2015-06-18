package com.my.cookmaster.bean.bus_bean;

import com.my.cookmaster.view.listview.viewprovider.*;
import com.my.cookmaster.view.listview.viewprovider.impl.*;

public class TicketOrder implements IItemBean {
	public String type;
	public String title;
	public String expireDate;
	public String price;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	@Override
	public Class<? extends IViewProvider> getViewProviderClass() {
		return SticketOrderViewProvider.class;
	}
	
	
	
}
