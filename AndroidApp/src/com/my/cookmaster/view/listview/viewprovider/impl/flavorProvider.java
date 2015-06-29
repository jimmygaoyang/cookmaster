package com.my.cookmaster.view.listview.viewprovider.impl;

import com.my.cookmaster.R;
import com.my.cookmaster.bean.bus_bean.flavor;
import com.my.cookmaster.bean.bus_bean.subStuff;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.subStuffProvider.ViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class flavorProvider extends IViewProvider{

	
	class ViewHolder{
		public TextView flavor;
		public TextView amount;

	}

	@Override
	public View getItemView(View convertView, LayoutInflater inflater,
			Object data) {
		// TODO Auto-generated method stub
		flavor item = (flavor) data;
		
		ViewHolder holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_view_flavor, null);
			holder = new ViewHolder();
			holder.flavor = (TextView) convertView.findViewById(R.id.flavor);
			holder.amount = (TextView) convertView.findViewById(R.id.amount);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.flavor.setText(item.getFlavor().getName());
		holder.amount.setText(item.getFlavor().getWeight());
		
		return convertView;
	}
}
