package com.my.cookmaster.bean.pro_bean;

import java.util.ArrayList;
import java.util.List;

public class GetBoxListRspBean {
	private List<GetBoxBean> BoxList = new ArrayList<GetBoxBean>();

	public List<GetBoxBean> getBoxList() {
		return BoxList;
	}

	public void setBoxList(List<GetBoxBean> boxList) {
		BoxList = boxList;
	}
}
