package com.my.cookmaster.view.listview.viewprovider;

import com.my.cookmaster.bean.bus_bean.TagMessage;

import android.view.View;

/**
* �Զ���ӿڣ����ڻص���ť����¼���Activity
* @author Ivan Xu
* 2014-11-26
*/
public interface Callback{
	public void click(View v);
	public void textChange(TagMessage message);
}
