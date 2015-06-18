package com.my.cookmaster.view.listview.viewprovider.impl;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.cookmaster.MenuShowActivity;
import com.my.cookmaster.R;
import com.my.cookmaster.bean.bus_bean.AdMenu;
import com.my.cookmaster.bean.bus_bean.ImageOder;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.ImageViewProvider.ImageListener;
import com.my.cookmaster.view.listview.viewprovider.impl.ImageViewProvider.ViewHolderImage;

public class AdMenuProvider extends IViewProvider{

	private View parentView;
	private Long Rreceip;
	@Override
	public View getItemView(View convertView, LayoutInflater inflater,
			Object data) {
		AdMenu item = (AdMenu) data;
		ViewHolderImage holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_view_admenu, null);
			holder = new ViewHolderImage();
			holder.img = (ImageView)convertView.findViewById(R.id.admenu_photo);
			holder.title = (TextView)convertView.findViewById(R.id.admenu_title);
			holder.FavoriteCount = (TextView)convertView.findViewById(R.id.favor_conunt);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolderImage)convertView.getTag();
		}
		holder.img.setTag(item.getCover());
		Log.d("cook",String.format("  Ù–‘pos = %d", position)); 
		holder.title.setText(item.getCoverTitle());
		holder.FavoriteCount.setText("µ„‘ﬁ"+item.getFavoriteCount()+"");
		
		adapter.loadImage.addTask(item.getCover(), holder.img);
		adapter.loadImage.doTask();
		parentView = convertView;//—∞’“∏∏¿‡
		Rreceip = item.getRecipeId();
		convertView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("cook",String.format("menuID=%d", Rreceip));
				Intent intent = new Intent(parentView.getContext(), MenuShowActivity.class);
				intent.putExtra("menuID", Rreceip);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				parentView.getContext().startActivity(intent);
			}
		});
		
		return convertView;
	}

	class ViewHolderImage{
		public ImageView img;
		public TextView title;
		public TextView UserName;
		public TextView FavoriteCount;

	}
	

}
