package com.my.cookmaster.bean.pro_bean;

import java.util.ArrayList;
import java.util.List;

public class FindBrandRspBean {
	private List<BrandBean> brandList = new ArrayList<BrandBean>();

	public List<BrandBean> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<BrandBean> brandList) {
		this.brandList = brandList;
	}
}
