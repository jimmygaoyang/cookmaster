package com.my.cookmaster.bean.pro_bean;

import java.util.ArrayList;
import java.util.List;

public class FindMaterialRspBean {
	private List<MaterialBean> materialList = new ArrayList<MaterialBean>();


	public List<MaterialBean> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<MaterialBean> materialList) {
		this.materialList = materialList;
	}
}
