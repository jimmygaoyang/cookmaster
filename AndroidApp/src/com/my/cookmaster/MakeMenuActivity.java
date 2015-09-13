package com.my.cookmaster;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.my.cookmaster.CookActivity.buttonClick;
import com.my.cookmaster.bean.bus_bean.MkMenuMainStuff;
import com.my.cookmaster.bean.bus_bean.MkMenuMenuTitle;
import com.my.cookmaster.bean.bus_bean.MkMenuSubStuff;
import com.my.cookmaster.bean.bus_bean.StuffAdd;
import com.my.cookmaster.bean.bus_bean.mainStuff;
import com.my.cookmaster.bean.bus_bean.subStuffTitle;
import com.my.cookmaster.bean.pro_bean.MaterialBean;
import com.my.cookmaster.bean.pro_bean.UploadMenuBean;
import com.my.cookmaster.bean.pro_bean.UploadStuff;
import com.my.cookmaster.view.listview.viewprovider.Callback;
import com.my.cookmaster.view.listview.viewprovider.IItemBean;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.MiltilViewListAdapter;
import com.my.cookmaster.view.listview.viewprovider.MyScrollListener;
import com.my.cookmaster.view.listview.viewprovider.impl.MkMenuAddStuffProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.MkMenuMainStuffProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.MkMenuMenuTitleProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.SubStuffEditProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.subStuffTitleProvider;
import com.my.cookmaster.view.util.FileService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MakeMenuActivity extends Activity {
	private TextView CurTile;
	private Button backBtn;
	private TextView makeMenu;
	private ListView makeMenuList;
	private UploadMenuBean uploadMenuBean ;
	FileService fileSer;
	String menuPath;
	public boolean hasNextPage =false;//标识已经有下一个activity了
	
	List<Class<? extends IViewProvider>> providers;
	MiltilViewListAdapter adpater;
	public static List<IItemBean> mList = null;
	
	public int editPosition;
	public static MakeMenuActivity instance = null;
	public boolean isCreatMenu = true;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.make_menu_activity);
		CurTile = (TextView)findViewById(R.id.text_title);
		backBtn = (Button)findViewById(R.id.button_backward);
		makeMenu = (TextView)findViewById(R.id.button_forward);
		makeMenuList = (ListView)findViewById(R.id.menuView);
		

		
		providers = new ArrayList<Class<? extends IViewProvider>>();
		providers.add(MkMenuMenuTitleProvider.class);
		providers.add(MkMenuMainStuffProvider.class);
		providers.add(subStuffTitleProvider.class);
		providers.add(SubStuffEditProvider.class);
		providers.add(MkMenuAddStuffProvider.class);
		
		CurTile.setText("发布菜谱");
		makeMenu.setText("下一步");
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
//				Intent intent = new Intent(MakeMenuActivity.this, MyMenuActivity.class);
//				MakeMenuActivity.this.startActivity(intent);
			}
		});
		makeMenu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				
				IItemBean itemBean = mList.get(0);
				if(itemBean instanceof MkMenuMenuTitle)
				{
					MkMenuMenuTitle bean = (MkMenuMenuTitle)mList.get(0);
				Toast.makeText(MakeMenuActivity.this,"菜名为" + bean.getMenuTitle(),Toast.LENGTH_SHORT).show();
				}
				
				refreshSaveData();
				}
				
		});
		
		if(mList == null)//防止重写
			mList = new ArrayList<IItemBean>();

		uploadMenuBean = new UploadMenuBean();
		
        Intent intent=getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent  
        Bundle bundle=intent.getExtras();//.getExtras()得到intent所附带的额外数据   
        isCreatMenu  = bundle.getBoolean("isCreate");
		if(isCreatMenu == false)
		{
			menuPath = bundle.getString("menuID");
			
			LoadMenuData(menuPath);
		}
		else
		{
			CreateMenuData();
		}
  
		if(mList.size() == 0)//防止重写
			CreateMenuData();
		
		instance = this;
	};
	
	private void LoadMenuData(String menuPath) {
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
		MkMenuMenuTitle titleBean = new MkMenuMenuTitle();
		titleBean.setMenuTitle(uploadMenuBean.getTitle());
		mList.add(titleBean);
		MkMenuMainStuff mainStuf = new MkMenuMainStuff();
		mainStuf.setMaterial(uploadMenuBean.getMainStuff().getStuff());
		mainStuf.setAmount(uploadMenuBean.getMainStuff().getAmount());
		mList.add(mainStuf);
		subStuffTitle subStuffTitleBean = new subStuffTitle();
		mList.add(subStuffTitleBean);
		for(int i=0; i< uploadMenuBean.getSubStuff().size();i++)
		{
			MkMenuSubStuff subStuff = new MkMenuSubStuff();
			subStuff.setMaterial(uploadMenuBean.getSubStuff().get(i).getStuff());
			subStuff.setAmount(uploadMenuBean.getSubStuff().get(i).getAmount());
			mList.add(subStuff);
		}

		StuffAdd stuffAdd = new StuffAdd();
		mList.add(stuffAdd);
		
		
		
		
		
	}

	@Override
	public void onResume() {
		super.onResume();
		adpater = new MiltilViewListAdapter(this.getApplication(), mList, providers);
		adpater.setmCallback(new buttonClick());
		makeMenuList.setAdapter(adpater);
		MyScrollListener scrollListener = new MyScrollListener(adpater);
		makeMenuList.setOnScrollListener(scrollListener);
		adpater.notifyDataSetChanged();		
		

	}
	private void refreshSaveData() {
		// TODO Auto-generated method stub
		//clear menuBean
		if(uploadMenuBean.getSubStuff().size() != 0)
		{
			uploadMenuBean.getSubStuff().clear();
		}
		if(uploadMenuBean.getSteps().size()!=0)
			uploadMenuBean.getSteps().clear();
		//reload
		int subStuffIndex=0;
        for(int i=0; i<mList.size();i++)
        {
        	if(mList.get(i) instanceof MkMenuMenuTitle)//若是标题
        	{
        		
        		uploadMenuBean.setTitle(((MkMenuMenuTitle)mList.get(i)).getMenuTitle());
        	}
        	if(mList.get(i) instanceof MkMenuMainStuff)
        	{
        		UploadStuff mainstuff = new UploadStuff();
        		mainstuff.setStuff(((MkMenuMainStuff)mList.get(i)).getMaterial());
        		mainstuff.setAmount(((MkMenuMainStuff)mList.get(i)).getAmount());
        		uploadMenuBean.setMainStuff(mainstuff);
        	}
        	if(mList.get(i) instanceof MkMenuSubStuff)
        	{
        		UploadStuff substuff = new UploadStuff();
        		substuff.setStuff(((MkMenuSubStuff)mList.get(i)).getMaterial());
        		substuff.setAmount(((MkMenuSubStuff)mList.get(i)).getAmount());
        		uploadMenuBean.getSubStuff().add(substuff);
        	}
        }
        
        //save to json file
        String sendDat = JSON.toJSONString(uploadMenuBean);       
        fileSer = new FileService(this);
        
        SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyyMMddHHmmss");     
        Date   curDate   =   new   Date(System.currentTimeMillis());//获取当前时间     
        String   str   =   formatter.format(curDate);
        String path = Constant.DOING_MENU_PATH;
        if(isCreatMenu == true)
        {
        	path = path+"/"+str+"/";
        }
        else
        {
        	path = Environment.getExternalStorageDirectory()+Constant.DOING_MENU_PATH+menuPath;
        }
        try {
			fileSer.saveToSDCard(path,"json.txt", sendDat);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

        
	}

	private void CreateMenuData()
	{
		mList.clear();
		MkMenuMenuTitle titleBean = new MkMenuMenuTitle();
		mList.add(titleBean);
		MkMenuMainStuff mainStuf = new MkMenuMainStuff();
		mList.add(mainStuf);
		subStuffTitle subStuffTitleBean = new subStuffTitle();
		mList.add(subStuffTitleBean);//添加辅料标题
		MkMenuSubStuff subStuff = new MkMenuSubStuff();
		mList.add(subStuff);
		StuffAdd stuffAdd = new StuffAdd();
		mList.add(stuffAdd);
	};
	
	class buttonClick implements Callback
	{
		@Override
		public void click(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(MakeMenuActivity.this, StuffSelectActivity.class);
			editPosition = (Integer)v.getTag();
			int itemIndex = editPosition & 0x0000FFFF;
			int sigId = (editPosition & 0xFFFF0000)>>16; 
			if(sigId ==0)
			{
				intent.putExtra("pos",itemIndex);
				MakeMenuActivity.this.startActivity(intent);
				hasNextPage = true;
			}
			else
			{
				switch(sigId)
				{
				case 1:
					mList.remove(itemIndex);
					MakeMenuActivity.this.onResume();
					break;
				}
			}



			//Toast.makeText(CookActivity.this.getActivity(),"listview的内部的按钮被点击了！，位置是-->" + (Integer) v.getTag(1,),Toast.LENGTH_SHORT).show();
		}
			
	};

}
