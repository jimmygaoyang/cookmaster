package com.my.cookmaster.view.listview.viewprovider.impl;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.cookmaster.R;
import com.my.cookmaster.bean.bus_bean.menuCover;
import com.my.cookmaster.bean.bus_bean.menuIntro;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.menuCoverProvider.ViewHolderImage;

public class menuIntroProider extends IViewProvider{

	@Override
	public View getItemView(View convertView, LayoutInflater inflater,
			Object data) {
		// TODO Auto-generated method stub
		menuIntro item = (menuIntro) data;
		
		ViewHolderImage holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_view_meu_intro, null);
			holder = new ViewHolderImage();
			holder.avator = (ImageView)convertView.findViewById(R.id.menu_user_avator);
			holder.menuName = (TextView)convertView.findViewById(R.id.menu_name);
			holder.pubTime = (TextView)convertView.findViewById(R.id.menu_pubtime);
			holder.userName = (TextView)convertView.findViewById(R.id.menu_user_name);
			holder.menuIntro =  (TextView)convertView.findViewById(R.id.menu_Intro);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolderImage)convertView.getTag();
		}
		
		holder.avator.setTag(item.getAvator());
		Log.d("cook",String.format(" 属性pos = %d", position)); 
		holder.menuName.setText(item.getMenuName());
		holder.userName.setText(item.getUsrName());
		holder.pubTime.setText("发布于"+item.getPubTime());
		holder.menuIntro.setText(item.getMenuInro());
		
		adapter.loadImage.addTask(item.getAvator(), holder.avator);
		adapter.loadImage.doTask();
		return convertView;
	}

	
	class ViewHolderImage{
		public TextView menuName;
		public ImageView avator;
		public TextView userName;
		public TextView pubTime;
		public TextView menuIntro;

	}
}
