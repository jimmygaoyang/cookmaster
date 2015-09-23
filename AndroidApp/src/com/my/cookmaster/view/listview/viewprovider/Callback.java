package com.my.cookmaster.view.listview.viewprovider;

import com.my.cookmaster.bean.bus_bean.TagMessage;

import android.view.View;

/**
* 自定义接口，用于回调按钮点击事件到Activity
* @author Ivan Xu
* 2014-11-26
*/
public interface Callback{
	public void click(View v);
	public void textChange(TagMessage message);
}
