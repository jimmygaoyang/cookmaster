package com.my.cookmaster.view.listview.viewprovider;

import com.my.cookmaster.view.LoadImage;

import android.view.LayoutInflater;
import android.view.View;

public abstract class IViewProvider {
	public MiltilViewListAdapter adapter = null;
	public int position;
	public Callback mCallback;
	public void setAdapter(MiltilViewListAdapter adapter,int position,Callback mCall)
	{
		this.adapter=adapter;
		this.position = position;
		this.mCallback = mCall;
	}
	public abstract View getItemView(View convertView,LayoutInflater inflater, Object data);
}
