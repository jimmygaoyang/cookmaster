package com.my.cookmaster.view.listview.viewprovider.impl;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.cookmaster.R;
import com.my.cookmaster.bean.bus_bean.menuIntro;
import com.my.cookmaster.bean.bus_bean.step;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.subStuffTitleProvider.ViewHolder;

public class stepProvider extends IViewProvider{

	@Override
	public View getItemView(View convertView, LayoutInflater inflater,
			Object data) {
		step item = (step) data;
		
		ViewHolder holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_view_step, null);
			holder = new ViewHolder();
			
			holder.photo = (ImageView)convertView.findViewById(R.id.step_photo);
			holder.stepIntro = (TextView)convertView.findViewById(R.id.intro);
			holder.stepNo = (TextView)convertView.findViewById(R.id.step_no);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.photo.setTag(item.getPhotoURL());
		holder.stepNo.setText(item.getStepNo()+"");
		holder.stepIntro.setText(item.getIntro());
		
		adapter.loadImage.addTask(item.getPhotoURL(), holder.photo);
		adapter.loadImage.doTask();
		
		return convertView;
	}
	class ViewHolder{
		ImageView photo;
		TextView stepNo;
		TextView stepIntro;

	}
}
