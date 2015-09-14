package com.my.cookmaster;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.my.cookmaster.bean.bus_bean.menuDoing;
import com.my.cookmaster.bean.pro_bean.UploadMenuBean;
import com.my.cookmaster.view.listview.viewprovider.IItemBean;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.MiltilViewListAdapter;
import com.my.cookmaster.view.listview.viewprovider.MyScrollListener;
import com.my.cookmaster.view.listview.viewprovider.impl.menuDoingProvider;
import com.my.cookmaster.view.util.FileService;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MyMenuDoingActivity extends Activity {
	private TextView CurTile;
	private Button backBtn;
	private TextView makeMenu;
	private ListView menuList;
	FileService fileSer;
	UploadMenuBean menuBean;
	List<Class<? extends IViewProvider>> providers;
	
	MiltilViewListAdapter adpater;
	private List<IItemBean> mList = new ArrayList<IItemBean>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_menu_doing_activity);
		CurTile = (TextView)findViewById(R.id.text_title);
		backBtn = (Button)findViewById(R.id.button_backward);
		makeMenu = (TextView)findViewById(R.id.button_forward);
		menuList = (ListView)findViewById(R.id.menuview);
		CurTile.setText("待发布菜谱");
		
		fileSer = new FileService(this);
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				
			}
		});
		makeMenu.setVisibility(View.INVISIBLE);
		
		providers = new ArrayList<Class<? extends IViewProvider>>();
		providers.add(menuDoingProvider.class);
		
		adpater = new MiltilViewListAdapter(this.getApplication(), mList, providers);
		menuList.setAdapter(adpater);
		MyScrollListener scrollListener = new MyScrollListener(adpater);
		menuList.setOnScrollListener(scrollListener);
		
//		menuList.setOnItemClickListener(new OnItemClickListener()
//		{
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,  
//                    int position, long id) {
//				// TODO Auto-generated method stub
//				Log.d("cook",String.format("选中了第%d个", position));
//			}
//			
//		});
		
		loadMenuData();
	}
	@Override
	public void onResume() {
		super.onResume();
		

		loadMenuData();
	}
	private void loadMenuData() {
		// TODO Auto-generated method stub
		mList.clear();
		String SDPath = Environment.getExternalStorageDirectory()+"";
		File[] allFiles = new File(SDPath+Constant.DOING_MENU_PATH).listFiles();  
	    for (int i = 0; i < allFiles.length; i++) {  
	        File file = allFiles[i];
	        String content=null;
	        try {
				 content = fileSer.read(SDPath+Constant.DOING_MENU_PATH+file.getName()+"/json.txt");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        menuBean = JSON.parseObject(content,UploadMenuBean.class);
	        
	       menuDoing menuDoingBean = new menuDoing();
	       menuDoingBean.setCoverUrl(menuBean.getCoverUrl());
	       menuDoingBean.setMenuDir(file.getName());
	       menuDoingBean.setMenuTitle(menuBean.getTitle());
	       mList.add(menuDoingBean);
	        
	        
	    }
		
		
		
	}



}
