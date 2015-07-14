package com.my.cookmaster.view.listview.viewprovider.impl;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.cookmaster.R;
import com.my.cookmaster.bean.bus_bean.advertiseView;
import com.my.cookmaster.view.bannerview.CircleFlowIndicator;
import com.my.cookmaster.view.bannerview.ImagePagerAdapter;
import com.my.cookmaster.view.bannerview.ViewFlow;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.AdMenuProvider.ViewHolderImage;

public class advertiseProvider extends IViewProvider{

	@Override
	public View getItemView(View convertView, LayoutInflater inflater,
			Object data) {
		// TODO Auto-generated method stub
		advertiseView item = (advertiseView)data;
		ViewHolderImage holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_view_advertise, null);
			holder = new ViewHolderImage();
			holder.mViewFlow = (ViewFlow) convertView.findViewById(R.id.viewflow);
			holder.mFlowIndicator = (CircleFlowIndicator) convertView.findViewById(R.id.viewflowindic);
	
			holder.mViewFlow.setAdapter(new ImagePagerAdapter(adapter.getmContext().getApplicationContext(), item.getImageUrlList(),
					item.getLinkUrlArray(), item.getTitleList()).setInfiniteLoop(true));
			holder.mViewFlow.setmSideBuffer(item.getImageUrlList().size()); // 实际图片张数，
															// 我的ImageAdapter实际图片张数为3

			holder.mViewFlow.setFlowIndicator(holder.mFlowIndicator);
			holder.mViewFlow.setTimeSpan(4500);
			holder.mViewFlow.setSelection(item.getImageUrlList().size() * 1000); // 设置初始位置
			holder.mViewFlow.startAutoFlowTimer(); // 启动自动播放
			convertView.setTag(holder);
		}else{
			holder = (ViewHolderImage)convertView.getTag();
		}
		return convertView;
	}
	
	class ViewHolderImage{
		private ViewFlow mViewFlow;
		private CircleFlowIndicator mFlowIndicator;

	}

}
