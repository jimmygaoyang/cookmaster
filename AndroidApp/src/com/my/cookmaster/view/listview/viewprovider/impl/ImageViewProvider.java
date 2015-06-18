package com.my.cookmaster.view.listview.viewprovider.impl;

import com.my.cookmaster.R;
import com.my.cookmaster.bean.bus_bean.FlightOrder;
import com.my.cookmaster.bean.bus_bean.ImageOder;
import com.my.cookmaster.view.LoadImage;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.MiltilViewListAdapter;
import com.my.cookmaster.view.listview.viewprovider.impl.FlightOrderViewProvider.ViewHolderFlight;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageViewProvider extends IViewProvider {
	@Override
	public View getItemView(View convertView, LayoutInflater inflater,Object data) {
		ImageOder item = (ImageOder) data;
		
		ViewHolderImage holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_view_image, null);
			holder = new ViewHolderImage();
			holder.img = (ImageView)convertView.findViewById(R.id.imageView1);
			holder.delBtn = (Button)convertView.findViewById(R.id.removeView);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolderImage)convertView.getTag();
		}
		
		holder.img.setTag(item.getImgURL());
		Log.d("cook",String.format(" ÊôÐÔpos = %d", position)); 
		holder.delBtn.setOnClickListener(new ImageListener(item,position));
		
		adapter.loadImage.addTask(item.getImgURL(), holder.img);
		adapter.loadImage.doTask();
		return convertView;
	}
	class ViewHolderImage{
		public ImageView img;
		public Button delBtn;

	}
	
	class ImageListener implements OnClickListener{

		ImageOder item;
		int pos;
		public ImageListener(ImageOder data,int position)
		{
			item = data;
			pos = position;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.d("cook",String.format("ÒªÉ¾³ýÍ¼Æ¬pos = %d", pos));
			Log.d("cook",item.getImgURL());
			adapter.mItemBeanList.remove(pos);
			adapter.notifyDataSetChanged();
		}
	}

	
}
