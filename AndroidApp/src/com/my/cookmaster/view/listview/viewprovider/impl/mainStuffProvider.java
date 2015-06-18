package com.my.cookmaster.view.listview.viewprovider.impl;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.cookmaster.R;
import com.my.cookmaster.bean.bus_bean.FlightOrder;
import com.my.cookmaster.bean.bus_bean.mainStuff;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.FlightOrderViewProvider.ViewHolderFlight;

public class mainStuffProvider  extends IViewProvider{

	@Override
	public View getItemView(View convertView, LayoutInflater inflater,
			Object data) {
		mainStuff item = (mainStuff) data;
		
		ViewHolder holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_view_main_stuff, null);
			holder = new ViewHolder();
			holder.mainStuff = (TextView) convertView.findViewById(R.id.mainstuff);
			holder.amount = (TextView) convertView.findViewById(R.id.amount);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.mainStuff.setText(item.getMainStuff().getName());
		holder.amount.setText(item.getMainStuff().getWeight());
		
		return convertView;
	}
	class ViewHolder{
		public TextView mainStuff;
		public TextView amount;

	}
}
