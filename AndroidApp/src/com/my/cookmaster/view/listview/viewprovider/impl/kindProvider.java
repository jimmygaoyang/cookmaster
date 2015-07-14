package com.my.cookmaster.view.listview.viewprovider.impl;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.my.cookmaster.R;
import com.my.cookmaster.bean.bus_bean.materialKind;
import com.my.cookmaster.bean.bus_bean.materialView;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.materialProvider.ViewHolder;

public class kindProvider extends IViewProvider{

	@Override
	public View getItemView(View convertView, LayoutInflater inflater,
			Object data) {
		// TODO Auto-generated method stub
		materialKind item = (materialKind) data;
		
		ViewHolder holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_view_kind, null);
			holder = new ViewHolder();
			holder.Name = (TextView) convertView.findViewById(R.id.name);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.Name.setText(item.getKindName());
		return convertView;
	}
	
	class ViewHolder{
		public TextView Name;

	}


}
