package com.my.cookmaster.view.listview.viewprovider;

import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public class MyScrollListener implements OnScrollListener {
	private MiltilViewListAdapter viewAdapter;
	public MyScrollListener(MiltilViewListAdapter adapter)
	{
		viewAdapter = adapter;
	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		 switch (scrollState) {
	     case OnScrollListener.SCROLL_STATE_IDLE:	    	 
	    	 viewAdapter.loadImage.startRun();
	    	 viewAdapter.loadImage.doTask();
	    	 Log.d("cook","start task");
	       //��ȡ��һ��listview�ɼ�����Ŀ��λ��
	       // ��ȡlistview������ʾ����Ŀ�ĸ���.

	         break;
	     case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL: 
	    	 viewAdapter.loadImage.pauseRun();
	    	 Log.d("cook","pause task");
	         break;
	     case OnScrollListener.SCROLL_STATE_FLING:
	    	 viewAdapter.loadImage.pauseRun();
	    	 Log.d("cook","pause task");
	         break;
		
		 }
	}
}