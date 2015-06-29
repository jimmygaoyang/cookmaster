package com.my.cookmaster.view.listview.viewprovider.impl;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.my.cookmaster.MenuShowActivity;
import com.my.cookmaster.R;
import com.my.cookmaster.ToolAddActivity;
import com.my.cookmaster.bean.bus_bean.Box;
import com.my.cookmaster.bean.bus_bean.BoxAdd;
import com.my.cookmaster.view.listview.viewprovider.IItemBean;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.BoxProvider.ImageListener;
import com.my.cookmaster.view.listview.viewprovider.impl.subStuffTitleProvider.ViewHolder;

public class BoxAddProvider extends IViewProvider{

	private View parentView;
	@Override
	public View getItemView(View convertView, LayoutInflater inflater,
			Object data) {
		// TODO Auto-generated method stub
		BoxAdd item = (BoxAdd) data;
		ViewHolder holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_view_add_box, null);
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

		BoxAdd item;
		int pos;
		public ImageListener(BoxAdd data,int position)
		{
			item = data;
			pos = position;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.d("cook",String.format("要添加调料盒pos = %d", pos));
//			Box item1 = new Box();
//			item1.setBoxMac("未知");
//			item1.setFlavorName("未知");
//			item1.setFlavorBrand("未知");
//			adapter.mItemBeanList.add(pos,item1);
//			adapter.notifyDataSetChanged();
			Intent intent = new Intent(parentView.getContext(), ToolAddActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			parentView.getContext().startActivity(intent);
		}
	}
}
