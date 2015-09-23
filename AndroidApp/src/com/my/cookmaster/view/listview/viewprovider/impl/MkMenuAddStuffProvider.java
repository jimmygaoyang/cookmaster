package com.my.cookmaster.view.listview.viewprovider.impl;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.my.cookmaster.MakeMenuActivity;
import com.my.cookmaster.R;
import com.my.cookmaster.ToolAddActivity;
import com.my.cookmaster.bean.bus_bean.BoxAdd;
import com.my.cookmaster.bean.bus_bean.MkMenuSubStuff;
import com.my.cookmaster.bean.bus_bean.StuffAdd;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.BoxAddProvider.ImageListener;
import com.my.cookmaster.view.listview.viewprovider.impl.BoxAddProvider.ViewHolder;

public class MkMenuAddStuffProvider  extends IViewProvider{

	private View parentView;
	private StuffAdd item = null;
	@Override
	public View getItemView(View convertView, LayoutInflater inflater,
			Object data) {
		// TODO Auto-generated method stub
		item = (StuffAdd) data;
		ViewHolder holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_view_add_stuff, null);
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

		StuffAdd item;
		int pos;
		public ImageListener(StuffAdd data,int position)
		{
			item = data;
			pos = position;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.d("cook",String.format("要添加调料pos = %d", pos));
			MkMenuSubStuff item1 = new MkMenuSubStuff();		
//			adapter.mItemBeanList.add(pos,item1);
//			adapter.notifyDataSetChanged();			
			int message = 0 +(MakeMenuActivity.SUB_STUFF_ADD<<16);
			v.setTag(message);
			mCallback.click(v);
			
		}
	}

}
