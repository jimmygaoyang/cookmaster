package com.my.cookmaster.view.listview.viewprovider.impl;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.cookmaster.MakeMenuActivity;
import com.my.cookmaster.MenuShowActivity;
import com.my.cookmaster.R;
import com.my.cookmaster.bean.bus_bean.Box;
import com.my.cookmaster.bean.bus_bean.BoxOperate;
import com.my.cookmaster.bean.bus_bean.menuDoing;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.BoxProvider.ImageListener;
import com.my.cookmaster.view.listview.viewprovider.impl.BoxProvider.ViewHolderImage;

public class menuDoingProvider extends IViewProvider{

	private View parentView;
	private String menuDir;
	@Override
	public View getItemView(View convertView, LayoutInflater inflater,
			Object data) {
		// TODO Auto-generated method stub
		menuDoing item = (menuDoing) data;
		ViewHolderImage holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_view_menu_edit, null);
			holder = new ViewHolderImage();
			holder.img = (ImageView)convertView.findViewById(R.id.menu_cover);
			holder.menuTitle = (TextView)convertView.findViewById(R.id.menu_title);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolderImage)convertView.getTag();
		}
		
		holder.menuTitle.setText(item.getMenuTitle());
		if(item.getCoverUrl()!=null)
		{
			holder.img.setTag(item.getCoverUrl());
			adapter.loadImage.addTask(item.getCoverUrl(), holder.img);
			adapter.loadImage.doTask();
		}
		
		menuDir = item.getMenuDir();
		parentView = convertView;//—∞’“∏∏¿‡
		holder.img.setOnClickListener(new ImageListener(item,position));
		holder.menuTitle.setOnClickListener(new ImageListener(item,position));
//		parentView.setOnClickListener(new ImageListener(item,position));
//		convertView.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Log.d("cook",String.format("menuID=%d", position));
//				Intent intent = new Intent(parentView.getContext(), MakeMenuActivity.class);
//				intent.putExtra("menuID", menuDir);
//				intent.putExtra("isCreate", false);
//				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				parentView.getContext().startActivity(intent);
//			}
//		});

		
		return convertView;
	}

	
	class ImageListener implements OnClickListener{

		menuDoing item;
		int pos;
		public ImageListener(menuDoing data,int position)
		{
			item = data;
			pos = position;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.d("cook",String.format("menuID=%d", position));
			Intent intent = new Intent(parentView.getContext(), MakeMenuActivity.class);
			intent.putExtra("menuID", menuDir);
			intent.putExtra("isCreate", false);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			parentView.getContext().startActivity(intent);
		}
	}
	
	class ViewHolderImage{
		public ImageView img;
		public TextView menuTitle;

	}

}
