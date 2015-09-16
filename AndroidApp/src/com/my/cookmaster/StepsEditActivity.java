package com.my.cookmaster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.my.cookmaster.MakeMenuActivity.buttonClick;
import com.my.cookmaster.bean.bus_bean.StepAdd;
import com.my.cookmaster.bean.bus_bean.menuCoverEdit;
import com.my.cookmaster.bean.bus_bean.stepEdit;
import com.my.cookmaster.bean.pro_bean.UploadMenuBean;
import com.my.cookmaster.view.listview.viewprovider.Callback;
import com.my.cookmaster.view.listview.viewprovider.IItemBean;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.MiltilViewListAdapter;
import com.my.cookmaster.view.listview.viewprovider.MyScrollListener;
import com.my.cookmaster.view.listview.viewprovider.impl.MkMenuMenuTitleProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.StepEditProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.menuCoverEditProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.stepAddProvider;
import com.my.cookmaster.view.util.FileService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class StepsEditActivity extends Activity {

	private TextView CurTile;
	private Button backBtn;
	private TextView publish;
	private ListView stepsList;
	private String menuPath;
	private FileService fileSer;
	private UploadMenuBean uploadMenuBean ;
	public static final int STEP_ADD = 1;
	public static final int STEP_EDIT = 2;
	public static final int STEP_DEL = 3;
	
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
	    Intent intent=getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent  
        Bundle bundle=intent.getExtras();//.getExtras()得到intent所附带的额外数据   
        menuPath = bundle.getString("menuPath");
        
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
		
		fileSer = new FileService(this);
		String content = null;
		try {
			content = fileSer.read(Environment.getExternalStorageDirectory()+Constant.DOING_MENU_PATH+menuPath+"/json.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		uploadMenuBean = JSON.parseObject(content,UploadMenuBean.class);
		
		mList.clear();
		menuCoverEdit menuCover = new menuCoverEdit();
		if(uploadMenuBean.getCoverUrl()!= null)
			menuCover.setCoverUrl(uploadMenuBean.getCoverUrl());
		mList.add(menuCover);
		for(int i=0;i<uploadMenuBean.getSteps().size();i++)
		{
			stepEdit stepContent = new stepEdit();
			stepContent.setPhotoURL(uploadMenuBean.getSteps().get(i).getStepImgRes());
			stepContent.setStepNo(i+1);
			stepContent.setIntro(uploadMenuBean.getSteps().get(i).getIntro());
			mList.add(stepContent);
		}
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
		

			switch(sigId)
			{
			case StepsEditActivity.STEP_DEL:
			{
				uploadMenuBean.getSteps().remove(itemIndex-1);
			    //save to json file
		        String sendDat = JSON.toJSONString(uploadMenuBean);       
		        fileSer = new FileService(StepsEditActivity.this);
		        

		        String path = Constant.DOING_MENU_PATH;

		        path = Environment.getExternalStorageDirectory()+Constant.DOING_MENU_PATH+menuPath;

		        try {
					fileSer.saveToSDCard(path,"json.txt", sendDat);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
		        
				StepsEditActivity.this.onResume();
			}
				break;
			case StepsEditActivity.STEP_ADD:
				intent.putExtra("menuPath",menuPath);
				intent.putExtra("IsCreate", true);
				StepsEditActivity.this.startActivity(intent);
				break;
			case StepsEditActivity.STEP_EDIT:
				intent.putExtra("menuPath",menuPath);
				intent.putExtra("IsCreate", false);
				intent.putExtra("StepIndex", itemIndex);
				StepsEditActivity.this.startActivity(intent);
				break;
			}
				




			//Toast.makeText(CookActivity.this.getActivity(),"listview的内部的按钮被点击了！，位置是-->" + (Integer) v.getTag(1,),Toast.LENGTH_SHORT).show();
		}
			
	};
}
