package com.my.cookmaster.view.listview.viewprovider.impl;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.my.cookmaster.R;
import com.my.cookmaster.bean.bus_bean.FlightOrder;
import com.my.cookmaster.view.LoadImage;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.MiltilViewListAdapter;

public class FlightOrderViewProvider extends IViewProvider {

	@Override
	public View getItemView(View convertView,LayoutInflater inflater,Object data) {
		FlightOrder item = (FlightOrder) data;
		
		ViewHolderFlight holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_view_flight, null);
			holder = new ViewHolderFlight();
			holder.airlineTv = (TextView) convertView.findViewById(R.id.flight_airline_tv);
			holder.fromTv = (TextView) convertView.findViewById(R.id.flight_from_tv);
			holder.toTv = (TextView) convertView.findViewById(R.id.flight_to_tv);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolderFlight)convertView.getTag();
		}
		
		holder.airlineTv.setText(item.airline);
		holder.fromTv.setText(item.from);
		holder.toTv.setText(item.to);
		
		return convertView;
	}
	
	class ViewHolderFlight{
		public TextView airlineTv;
		public TextView fromTv;
		public TextView toTv;
	}
}
