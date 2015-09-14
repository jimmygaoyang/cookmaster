package com.my.cookmaster.view.listview.viewprovider.impl;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.cookmaster.R;
import com.my.cookmaster.bean.bus_bean.menuCover;
import com.my.cookmaster.bean.bus_bean.menuCoverEdit;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.menuCoverProvider.ViewHolderImage;

public class menuCoverEditProvider extends IViewProvider{

	@Override
	public View getItemView(View convertView, LayoutInflater inflater,
			Object data) {
		// TODO Auto-generated method stub
		menuCoverEdit item = (menuCoverEdit) data;
		
		ViewHolderImage holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_view_menu_cover_edit, null);
			holder = new ViewHolderImage();
			holder.img = (ImageView)convertView.findViewById(R.id.menu_cover);
			holder.hint = (TextView)convertView.findViewById(R.id.menu_favorconunt);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolderImage)convertView.getTag();
		}
		if(item.getCoverUrl()==null)
		{
			holder.hint.setText(String.format("µã»÷Ìí¼Ó·âÃæ"));
			
		}
		else{
			holder.img.setTag(item.getCoverUrl());
			holder.hint.setVisibility(View.INVISIBLE);
			adapter.loadImage.addTask(item.getCoverUrl(), holder.img);
			adapter.loadImage.doTask();
		}


		return convertView;
	}
	
	class ViewHolderImage{
		public ImageView img;
		public TextView hint;

	}

}
