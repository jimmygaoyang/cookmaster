package com.my.cookmaster.view.listview.viewprovider.impl;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.cookmaster.R;
import com.my.cookmaster.StepsEditActivity;
import com.my.cookmaster.bean.bus_bean.step;
import com.my.cookmaster.bean.bus_bean.stepEdit;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.stepProvider.ViewHolder;

public class StepEditProvider extends IViewProvider{

	private int stepIndex;
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
		stepIndex = item.getStepNo();
		
		if(item.getPhotoURL() != null)
		{
			adapter.loadImage.addTask(item.getPhotoURL(), holder.photo);
			adapter.loadImage.doTask();		
		}
		holder.stepDel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("cook",String.format("É¾³ý²½Öè%d", stepIndex));
				stepIndex = stepIndex + (StepsEditActivity.STEP_DEL<<16);
				v.setTag(stepIndex);
				mCallback.click(v);
			}
		});
		
		convertView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("cook",String.format("±à¼­²½Öè%d", stepIndex));
				stepIndex = stepIndex + (StepsEditActivity.STEP_EDIT<<16);
				v.setTag(stepIndex);
				mCallback.click(v);
			}
		});
		
		
		
		return convertView;
	}

	
	class ViewHolder{
		ImageView photo;
		TextView stepNo;
		TextView stepIntro;
		ImageView stepDel;


	}
}
