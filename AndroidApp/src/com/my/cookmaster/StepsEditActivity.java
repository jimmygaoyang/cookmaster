package com.my.cookmaster;

import java.util.ArrayList;
import java.util.List;

import com.my.cookmaster.MakeMenuActivity.buttonClick;
import com.my.cookmaster.bean.bus_bean.StepAdd;
import com.my.cookmaster.bean.bus_bean.menuCoverEdit;
import com.my.cookmaster.view.listview.viewprovider.Callback;
import com.my.cookmaster.view.listview.viewprovider.IItemBean;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.MiltilViewListAdapter;
import com.my.cookmaster.view.listview.viewprovider.MyScrollListener;
import com.my.cookmaster.view.listview.viewprovider.impl.MkMenuMenuTitleProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.StepEditProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.menuCoverEditProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.stepAddProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class StepsEditActivity extends Activity {

	private TextView CurTile;
	private Button backBtn;
	private TextView publish;
	private ListView stepsList;
	
	List<Class<? extends IViewProvider>> providers;
	MiltilViewListAdapter adpater;
	public static List<IItemBean> mList = null;
	public int editPosition;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.make_menu_activity);
		CurTile = (TextView)findViewById(R.id.text_title);
		backBtn = (Button)findViewById(R.id.button_backward);
		publish = (TextView)findViewById(R.id.button_forward);
		stepsList = (ListView)findViewById(R.id.menuView);
		
		providers = new ArrayList<Class<? extends IViewProvider>>();
		providers.add(menuCoverEditProvider.class);	
		providers.add(StepEditProvider.class);
		providers.add(stepAddProvider.class);
		
		
		mList = new ArrayList<IItemBean>();
		CurTile.setText("菜谱编辑");
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
	
	}
	
	private void loadStepsData() {
		// TODO Auto-generated method stub
		mList.clear();
		menuCoverEdit menuCover = new menuCoverEdit();
		mList.add(menuCover);
		StepAdd stepAddBean = new  StepAdd();
		mList.add(stepAddBean);
		
		
	}

	@Override
	public void onResume() {
		super.onResume();
		adpater = new MiltilViewListAdapter(this.getApplication(), mList, providers);
		stepsList.setAdapter(adpater);
		adpater.setmCallback(new buttonClick());
		MyScrollListener scrollListener = new MyScrollListener(adpater);
		stepsList.setOnScrollListener(scrollListener);
		adpater.notifyDataSetChanged();	
		
		loadStepsData();
		

	}
	
	class buttonClick implements Callback
	{
		@Override
		public void click(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(StepsEditActivity.this, SetpEditActivity.class);
			editPosition = (Integer)v.getTag();
			int itemIndex = editPosition & 0x0000FFFF;
			int sigId = (editPosition & 0xFFFF0000)>>16; 
			if(sigId ==0)
			{
				intent.putExtra("pos",itemIndex);
				StepsEditActivity.this.startActivity(intent);
			}
			else
			{
				switch(sigId)
				{
				case 1:
					mList.remove(itemIndex);
					StepsEditActivity.this.onResume();
					break;
				}
			}



			//Toast.makeText(CookActivity.this.getActivity(),"listview的内部的按钮被点击了！，位置是-->" + (Integer) v.getTag(1,),Toast.LENGTH_SHORT).show();
		}
			
	};
}
