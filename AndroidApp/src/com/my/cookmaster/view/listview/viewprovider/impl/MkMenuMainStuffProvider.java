package com.my.cookmaster.view.listview.viewprovider.impl;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.my.cookmaster.R;
import com.my.cookmaster.bean.bus_bean.BoxOperate;
import com.my.cookmaster.bean.bus_bean.MkMenuMainStuff;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.BoxOperateProvider.ImageListener;
import com.my.cookmaster.view.listview.viewprovider.impl.MkMenuMenuTitleProvider.ViewHolder;

public class MkMenuMainStuffProvider extends IViewProvider{

	@Override
	public View getItemView(View convertView, LayoutInflater inflater,
			Object data) {
		
		MkMenuMainStuff item = (MkMenuMainStuff)data;
		ViewHolder holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_view_main_stuff_edit, null);
			holder = new ViewHolder();
			
			holder.material = (EditText)convertView.findViewById(R.id.mainstuff);
			holder.amount = (EditText)convertView.findViewById(R.id.amount);
//			holder.material.setOnClickListener(new ImageListener(item,position));
			if(item.getMaterial()!=null)
				holder.material.setText(item.getMaterial().getName());
			holder.material.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					// TODO Auto-generated method stub
					if(hasFocus)
					{
						Log.d("cook","  得到焦点");
						Log.d("cook",String.format("要设置调料食材pos = %d", position));
						v.setTag(position);
						mCallback.click(v);
					}
					else
					{
						Log.d("cook","  失去焦点");
					}
				}
			});
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		return convertView;
	}
	
	class ViewHolder{
		EditText material;
		EditText amount;
	};
	
//	class ImageListener implements OnClickListener{
//
//		MkMenuMainStuff item;
//		int pos;
//		public ImageListener(MkMenuMainStuff data,int position)
//		{
//			item = data;
//			pos = position;
//		}
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			Log.d("cook",String.format("要设置调料食材pos = %d", pos));
//			v.setTag(position);
//			mCallback.click(v);
//		}
//	}
	
 
 

}
