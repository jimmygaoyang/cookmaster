package com.my.cookmaster.view.listview.viewprovider.impl;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.cookmaster.R;
import com.my.cookmaster.bean.bus_bean.step;
import com.my.cookmaster.bean.bus_bean.stepEdit;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.stepProvider.ViewHolder;

public class StepEditProvider extends IViewProvider{

	@Override
	public View getItemView(View convertView, LayoutInflater inflater,
			Object data) {
		stepEdit item = (stepEdit) data;
		
		ViewHolder holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_view_step_edit, null);
			holder = new ViewHolder();
			
			holder.photo = (ImageView)convertView.findViewById(R.id.StepPhoto);
			holder.stepIntro = (TextView)convertView.findViewById(R.id.StepContent);
			holder.stepNo = (TextView)convertView.findViewById(R.id.StepIndex);
			holder.stepDel = (ImageView)convertView.findViewById(R.id.Step_Del);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.photo.setTag(item.getPhotoURL());
		holder.stepNo.setText(item.getStepNo()+"");
		holder.stepIntro.setText(item.getIntro());
		if(item.getPhotoURL() != null)
		{
			adapter.loadImage.addTask(item.getPhotoURL(), holder.photo);
			adapter.loadImage.doTask();		
		}		
		return convertView;
	}

	
	class ViewHolder{
		ImageView photo;
		TextView stepNo;
		TextView stepIntro;
		ImageView stepDel;


	}
}
