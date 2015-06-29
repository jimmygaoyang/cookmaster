package com.my.cookmaster.view.listview.viewprovider.impl;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.my.cookmaster.R;
import com.my.cookmaster.bean.bus_bean.mainStuff;
import com.my.cookmaster.bean.bus_bean.subStuff;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.mainStuffProvider.ViewHolder;

public class subStuffProvider extends IViewProvider{

	@Override
	public View getItemView(View convertView, LayoutInflater inflater,
			Object data) {
		subStuff item = (subStuff) data;
		
		ViewHolder holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_view_sub_stuff, null);
			holder = new ViewHolder();
			holder.subStuff = (TextView) convertView.findViewById(R.id.substuff);
			holder.amount = (TextView) convertView.findViewById(R.id.amount);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.subStuff.setText(item.getSubStuff().getName());
		holder.amount.setText(item.getSubStuff().getWeight());
		
		return convertView;
	}

	class ViewHolder{
		public TextView subStuff;
		public TextView amount;

	}
}
