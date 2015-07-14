package com.my.cookmaster.view.listview.viewprovider.impl;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.my.cookmaster.R;
import com.my.cookmaster.bean.bus_bean.brandView;
import com.my.cookmaster.bean.bus_bean.mainStuff;
import com.my.cookmaster.bean.bus_bean.materialView;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.mainStuffProvider.ViewHolder;

public class brandProvider extends IViewProvider{

	@Override
	public View getItemView(View convertView, LayoutInflater inflater,
			Object data) {
		// TODO Auto-generated method stub
		brandView item = (brandView) data;
		
		ViewHolder holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_view_brand, null);
			holder = new ViewHolder();
			holder.Name = (TextView) convertView.findViewById(R.id.name);
			holder.CorporName = (TextView) convertView.findViewById(R.id.corpor_name);
			holder.Ref = (TextView) convertView.findViewById(R.id.ref);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.Name.setText(item.getBrandName());
		holder.CorporName.setText(item.getCorporName());
		holder.Ref.setText(item.getBrandRef());
		
		return convertView;
	}

	class ViewHolder{
		public TextView Name;
		public TextView CorporName;
		public TextView Ref;

	}
}
