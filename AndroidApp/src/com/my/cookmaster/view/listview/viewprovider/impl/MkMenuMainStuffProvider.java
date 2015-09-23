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
import android.widget.TextView;

import com.my.cookmaster.MakeMenuActivity;
import com.my.cookmaster.R;
import com.my.cookmaster.bean.bus_bean.BoxOperate;
import com.my.cookmaster.bean.bus_bean.MkMenuMainStuff;
import com.my.cookmaster.bean.bus_bean.MkMenuMenuTitle;
import com.my.cookmaster.bean.bus_bean.MkMenuSubStuff;
import com.my.cookmaster.bean.bus_bean.TagMessage;
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
			holder.amount = (TextView)convertView.findViewById(R.id.amount);
//			holder.material.setOnClickListener(new ImageListener(item,position));
			if(item.getMaterial()!=null)
				holder.material.setText(item.getMaterial().getName());
			holder.amount.setText(item.getAmount());
//			holder.material.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//				
//				@Override
//				public void onFocusChange(View v, boolean hasFocus) {
//					// TODO Auto-generated method stub
//					if(hasFocus)
//					{
//						Log.d("cook","  得到焦点");
//						Log.d("cook",String.format("要设置调料食材pos = %d", position));
//						v.setTag(position);
//						mCallback.click(v);
//					}
//					else
//					{
//						Log.d("cook","  失去焦点");
//					}
//				}
//			});
			holder.material.setOnTouchListener(new OnTouchListener()
			{
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					

                    if(event.getAction() == MotionEvent.ACTION_UP) {
                    	adapter.setFocusIndex(position);
    					Log.d("cook","  被点击");
    					Log.d("cook",String.format("要设置主食材"));
    					int message = 0 +(MakeMenuActivity.MAIN_STUFF_EDIT<<16);
    					v.setTag(message);
    					mCallback.click(v);
                    }
					return false;
				}});
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
//		holder.amount.setOnTouchListener(new OnTouchListener()
//		{
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				// TODO Auto-generated method stub
//                if(event.getAction() == MotionEvent.ACTION_UP) {
//                	adapter.setFocusIndex(position);
//                }
//				return false;
//			}
//			
//		});
//		holder.amount.addTextChangedListener(new TextWatcher(){
//
//			@Override
//			public void beforeTextChanged(CharSequence s, int start,
//					int count, int after) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void onTextChanged(CharSequence s, int start,
//					int before, int count) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void afterTextChanged(Editable s) {
//				// TODO Auto-generated method stub
//				if (s != null && !"".equals(s.toString())) {  
//				((MkMenuMainStuff)adapter.mItemBeanList.get(position)).setAmount(s.toString());
//				TagMessage message = new TagMessage();
//				message.Amount = s.toString();
//				message.TagId = MakeMenuActivity.MAIN_STUFF_AMOUNT_EDIT;			
//				mCallback.textChange(message);
//				
//				}
//
//			}
//			
//		});
//		
//		holder.amount.clearFocus();
//	        if(adapter.getFocusIndex()!= -1 && adapter.getFocusIndex() == position) {
//	               // 如果当前的行下标和点击事件中保存的index一致，手动为EditText设置焦点。
//	        	holder.amount.requestFocus();
//	        }
		return convertView;
	}
	
	class ViewHolder{
		EditText material;
		TextView amount;
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
