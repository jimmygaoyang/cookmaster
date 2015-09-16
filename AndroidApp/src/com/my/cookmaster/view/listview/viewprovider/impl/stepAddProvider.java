package com.my.cookmaster.view.listview.viewprovider.impl;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.my.cookmaster.R;
import com.my.cookmaster.StepsEditActivity;
import com.my.cookmaster.bean.bus_bean.MkMenuSubStuff;
import com.my.cookmaster.bean.bus_bean.StepAdd;
import com.my.cookmaster.bean.bus_bean.StuffAdd;
import com.my.cookmaster.bean.bus_bean.stepEdit;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.MkMenuAddStuffProvider.ImageListener;
import com.my.cookmaster.view.listview.viewprovider.impl.MkMenuAddStuffProvider.ViewHolder;

public class stepAddProvider extends IViewProvider{

	private View parentView;
	@Override
	public View getItemView(View convertView, LayoutInflater inflater,
			Object data) {
		// TODO Auto-generated method stub
		StepAdd item = (StepAdd) data;
		ViewHolder holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_view_add_step, null);
			holder = new ViewHolder();
			holder.addBoxBar = (RelativeLayout)convertView.findViewById(R.id.addboxBar);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.addBoxBar.setOnClickListener(new ImageListener(item,position));
		parentView = convertView;//寻找父类
		return convertView;
	}
	
	class ViewHolder{
		public RelativeLayout addBoxBar;

	}
	
	class ImageListener implements OnClickListener{

		StepAdd item;
		int pos;
		public ImageListener(StepAdd data,int position)
		{
			item = data;
			pos = position;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.d("cook",String.format("新加步骤pos=%d", pos));
			pos = pos + (StepsEditActivity.STEP_ADD<<16);
			v.setTag(pos);
			mCallback.click(v);
		}
	}

}