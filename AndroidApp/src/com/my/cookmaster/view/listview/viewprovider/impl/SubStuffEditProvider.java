package com.my.cookmaster.view.listview.viewprovider.impl;

import android.text.Editable;
import android.text.InputType;
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

import com.my.cookmaster.MakeMenuActivity;
import com.my.cookmaster.R;
import com.my.cookmaster.bean.bus_bean.BoxOperate;
import com.my.cookmaster.bean.bus_bean.MkMenuMainStuff;
import com.my.cookmaster.bean.bus_bean.MkMenuMenuTitle;
import com.my.cookmaster.bean.bus_bean.MkMenuSubStuff;
import com.my.cookmaster.bean.bus_bean.TagMessage;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.MkMenuMainStuffProvider.ViewHolder;

public class SubStuffEditProvider extends IViewProvider{

	MkMenuSubStuff item=null;
	@Override
	public View getItemView(View convertView, LayoutInflater inflater,
			Object data) {
		// TODO Auto-generated method stub
		item = (MkMenuSubStuff)data;
		ViewHolder holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_view_sub_stuff_edit, null);
			holder = new ViewHolder();
			
			holder.material = (EditText)convertView.findViewById(R.id.substuff);
			holder.material.setInputType(InputType.TYPE_NULL);
			holder.amount = (TextView)convertView.findViewById(R.id.amount);
			holder.delImage = (ImageView)convertView.findViewById(R.id.del_stuff);
			holder.delImage.setOnClickListener(new ImageListener(item,position));
			if(item.getMaterial()!=null)
				holder.material.setText(item.getMaterial().getName());
			holder.amount.setText(item.getAmount());
			holder.material.setOnTouchListener(new OnTouchListener()
			{

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					

                    if(event.getAction() == MotionEvent.ACTION_UP) {
                    	adapter.setFocusIndex(position);
    					Log.d("cook","  �����");
    					Log.d("cook",String.format("Ҫ���õ���ʳ��%d", item.getSuffIndex()));
    					
    					int message = item.getSuffIndex()+(MakeMenuActivity.SUB_STUFF_EDIT<<16);
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
//				((MkMenuSubStuff)adapter.mItemBeanList.get(position)).setAmount(s.toString());
//				TagMessage message = new TagMessage();
//				message.Amount = s.toString();
//				message.stuffIndex = item.getSuffIndex();
//				message.TagId = MakeMenuActivity.SUB_STUFF_AMOUNT_EDIT;			
//				mCallback.textChange(message);
//				}
//
//			}
//			
//		});
//		
//		holder.amount.clearFocus();
//	        if(adapter.getFocusIndex()!= -1 && adapter.getFocusIndex() == position) {
//	               // �����ǰ�����±�͵���¼��б����indexһ�£��ֶ�ΪEditText���ý��㡣
//	        	holder.amount.requestFocus();
//	        }
		return convertView;
	}
	
	class ViewHolder{
		EditText material;
		TextView amount;
		ImageView delImage;
	};

	
	class ImageListener implements OnClickListener{

		MkMenuSubStuff item;
		int pos;
		public ImageListener(MkMenuSubStuff data,int position)
		{
			item = data;
			pos = position;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.d("cook",String.format("Ҫɾ���ĵ��Ϻ� = %d", item.getSuffIndex()));
			int message = item.getSuffIndex()+(MakeMenuActivity.SUB_STUFF_DEL<<16);
			v.setTag(message);

			mCallback.click(v);
		}
	}
}
