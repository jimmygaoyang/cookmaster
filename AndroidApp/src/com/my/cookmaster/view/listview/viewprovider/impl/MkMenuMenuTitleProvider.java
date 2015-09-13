package com.my.cookmaster.view.listview.viewprovider.impl;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.cookmaster.R;
import com.my.cookmaster.bean.bus_bean.BoxOperate;
import com.my.cookmaster.bean.bus_bean.MkMenuMenuTitle;
import com.my.cookmaster.bean.bus_bean.step;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.stepProvider.ViewHolder;

public class MkMenuMenuTitleProvider extends IViewProvider {

	@Override
	public View getItemView(View convertView, LayoutInflater inflater,
			Object data) {
		MkMenuMenuTitle item = (MkMenuMenuTitle)data;
		ViewHolder holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_view_menu_title_edit, null);
			holder = new ViewHolder();
			
			holder.menuTitle = (EditText)convertView.findViewById(R.id.menu_tilte);
			holder.menuTitle.setText(item.getMenuTitle());
			holder.menuTitle.addTextChangedListener(new TextWatcher(){

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					if (s != null && !"".equals(s.toString())) {  
						MkMenuMenuTitle  bean =(MkMenuMenuTitle)(adapter.mItemBeanList.get(position));
						bean.setMenuTitle(s.toString());
					}

				}
				
			});
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		holder.menuTitle.setOnTouchListener(new OnTouchListener()
		{

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
                if(event.getAction() == MotionEvent.ACTION_UP) {
                	adapter.setFocusIndex(position);
                }
				return false;
			}
			
		} );
		holder.menuTitle.clearFocus();
        if(adapter.getFocusIndex()!= -1 && adapter.getFocusIndex() == position) {
               // 如果当前的行下标和点击事件中保存的index一致，手动为EditText设置焦点。
        	holder.menuTitle.requestFocus();
        }
		return convertView;
	}
	
	class ViewHolder{
		EditText menuTitle;

	}; 
	
//	class ImageListener implements OnClickListener{
//
//		BoxOperate item;
//		int pos;
//		public ImageListener(BoxOperate data,int position)
//		{
//			item = data;
//			pos = position;
//		}
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			Log.d("cook",String.format("要设置调料盒pos = %d", pos));
//			String info = String.format("%d,%s", pos,"menuTitle");
//			v.setTag(info);
//			mCallback.click(v);
//		}
//	}


}


