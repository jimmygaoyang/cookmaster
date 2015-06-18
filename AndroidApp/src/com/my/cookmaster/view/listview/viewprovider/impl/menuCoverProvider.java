package com.my.cookmaster.view.listview.viewprovider.impl;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.cookmaster.R;
import com.my.cookmaster.bean.bus_bean.ImageOder;
import com.my.cookmaster.bean.bus_bean.menuCover;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.ImageViewProvider.ImageListener;
import com.my.cookmaster.view.listview.viewprovider.impl.ImageViewProvider.ViewHolderImage;

public class menuCoverProvider extends IViewProvider{

	
	@Override
	public View getItemView(View convertView, LayoutInflater inflater,
			Object data) {
		menuCover item = (menuCover) data;
		
		ViewHolderImage holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_view_menu_cover, null);
			holder = new ViewHolderImage();
			holder.img = (ImageView)convertView.findViewById(R.id.menu_cover);
			holder.favorCount = (TextView)convertView.findViewById(R.id.menu_favorconunt);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolderImage)convertView.getTag();
		}
		
		holder.img.setTag(item.getCoverUrl());
		Log.d("cook",String.format("  Ù–‘pos = %d", position)); 
		holder.favorCount.setText(String.format("‘ﬁ %d", item.getFavorCont()));
		
		adapter.loadImage.addTask(item.getCoverUrl(), holder.img);
		adapter.loadImage.doTask();
		return convertView;
	}

	class ViewHolderImage{
		public ImageView img;
		public TextView favorCount;

	}
}
