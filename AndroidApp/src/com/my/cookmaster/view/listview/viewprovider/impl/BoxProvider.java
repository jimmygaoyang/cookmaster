package com.my.cookmaster.view.listview.viewprovider.impl;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.cookmaster.BoxShowActivity;
import com.my.cookmaster.MenuShowActivity;
import com.my.cookmaster.R;
import com.my.cookmaster.bean.bus_bean.AdMenu;
import com.my.cookmaster.bean.bus_bean.Box;
import com.my.cookmaster.bean.bus_bean.ImageOder;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.AdMenuProvider.ViewHolderImage;
import com.my.cookmaster.view.listview.viewprovider.impl.ImageViewProvider.ImageListener;

public class BoxProvider extends IViewProvider{

	private View parentView;
	private String mac;
	@Override
	public View getItemView(View convertView, LayoutInflater inflater,
			Object data) {
		// TODO Auto-generated method stub
		Box item = (Box) data;
		ViewHolderImage holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_view_box, null);
			holder = new ViewHolderImage();
			holder.img = (ImageView)convertView.findViewById(R.id.box_icon);
			holder.flavorName = (TextView)convertView.findViewById(R.id.flavor_name);
			holder.flavorBrand = (TextView)convertView.findViewById(R.id.flavor_brand);
			holder.boxMac = (TextView)convertView.findViewById(R.id.box_mac);
			holder.delimg = (ImageView)convertView.findViewById(R.id.set_box);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolderImage)convertView.getTag();
		}
		
		holder.flavorName.setText("调料名称："+item.getFlavorName());
		holder.flavorBrand.setText("调料品牌："+item.getFlavorBrand());
		holder.boxMac.setText("机器编号："+item.getBoxMac());
		holder.delimg.setOnClickListener(new ImageListener(item,position));
		parentView = convertView;//寻找父类
		mac = item.getBoxMac();
		return convertView;
	}
	
	class ImageListener implements OnClickListener{

		Box item;
		int pos;
		public ImageListener(Box data,int position)
		{
			item = data;
			pos = position;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.d("cook",String.format("要设置调料盒pos = %d", pos));
			Intent intent = new Intent(parentView.getContext(), BoxShowActivity.class);
			intent.putExtra("box_mac",mac );
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			parentView.getContext().startActivity(intent);
//			adapter.mItemBeanList.remove(pos);
//			adapter.notifyDataSetChanged();
		}
	}
	
	class ViewHolderImage{
		public ImageView img;
		public TextView flavorName;
		public TextView flavorBrand;
		public TextView boxMac;
		public ImageView delimg;

	}

}
