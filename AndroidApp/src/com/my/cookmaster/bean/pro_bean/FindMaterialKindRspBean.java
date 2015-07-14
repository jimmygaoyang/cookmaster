package com.my.cookmaster.bean.pro_bean;

import java.util.ArrayList;
import java.util.List;

public class FindMaterialKindRspBean {
	private List<MaterialKindBean> materialKindList = new ArrayList<MaterialKindBean>();

	public List<MaterialKindBean> getMaterialKindList() {
		return materialKindList;
	}

	public void setMaterialKindList(List<MaterialKindBean> materialKindList) {
		this.materialKindList = materialKindList;
	}
}
