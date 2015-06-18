package com.my.cookmaster.view.listview.viewprovider.impl;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.my.cookmaster.R;
import com.my.cookmaster.view.LoadImage;
import com.my.cookmaster.view.listview.viewprovider.*;
import com.my.cookmaster.bean.bus_bean.*;

public class SticketOrderViewProvider extends IViewProvider {

	@Override
	public View getItemView(View convertView, LayoutInflater inflater,
			Object data) {
		TicketOrder item = (TicketOrder) data;
		
		ViewHolderTicket holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_view_ticket, null);
		 
			holder = new ViewHolderTicket();
			holder.expireDataTv = (TextView) convertView.findViewById(R.id.ticke_expire_date_tv);
			holder.tickeyTypeTv = (TextView) convertView.findViewById(R.id.ticke_type_tv);
			holder.titleTv = (TextView) convertView.findViewById(R.id.ticket_name_tv);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolderTicket)convertView.getTag();
		}
		
		holder.expireDataTv.setText(item.expireDate);
		holder.tickeyTypeTv.setText(item.type);
		holder.titleTv.setText(item.title);
		
		return convertView;
	}
	
	class ViewHolderTicket{
		public TextView expireDataTv;
		public TextView tickeyTypeTv;
		public TextView titleTv;
	}
}
