package com.my.cookmaster;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.my.cookmaster.bean.bus_bean.flavor;
import com.my.cookmaster.bean.bus_bean.flavorTitle;
import com.my.cookmaster.bean.bus_bean.mainStuff;
import com.my.cookmaster.bean.bus_bean.menuCover;
import com.my.cookmaster.bean.bus_bean.menuIntro;
import com.my.cookmaster.bean.bus_bean.step;
import com.my.cookmaster.bean.bus_bean.stepTitle;
import com.my.cookmaster.bean.bus_bean.subStuff;
import com.my.cookmaster.bean.bus_bean.subStuffTitle;
import com.my.cookmaster.bean.pro_bean.AdMenuListBean;
import com.my.cookmaster.bean.pro_bean.GetMenuBean;
import com.my.cookmaster.bean.pro_bean.menuBean;
import com.my.cookmaster.bus.HttpTranse;
import com.my.cookmaster.view.listview.viewprovider.IItemBean;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.MiltilViewListAdapter;
import com.my.cookmaster.view.listview.viewprovider.MyScrollListener;
import com.my.cookmaster.view.listview.viewprovider.impl.AdMenuProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.flavorProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.flavorTitleProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.mainStuffProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.menuCoverProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.menuIntroProider;
import com.my.cookmaster.view.listview.viewprovider.impl.stepProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.stepTitleProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.subStuffProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.subStuffTitleProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MenuShowActivity extends Activity {
	
	private TextView CurTile;
	private Button backBtn;
	private TextView makeMenu;
	private ListView menuShowList;
	//菜谱的详细bean
	menuBean recMenuBean = new menuBean();
	MiltilViewListAdapter adpater;
	private List<IItemBean> mList = new ArrayList<IItemBean>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_show);
		CurTile = (TextView)findViewById(R.id.text_title);
		backBtn = (Button)findViewById(R.id.button_backward);
		makeMenu = (TextView)findViewById(R.id.button_forward);
		menuShowList = (ListView)findViewById(R.id.menushowview);
		
		CurTile.setText("菜谱");
		
        Intent intent=getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent  
        Bundle bundle=intent.getExtras();//.getExtras()得到intent所附带的额外数据   
        Long menuID = (Long) bundle.get("menuID");
        Log.d("cook",String.format("菜谱id=%d", menuID));
		//发送获取菜谱文件
		GetMenuBean bean = new GetMenuBean();
		bean.setMenuID(menuID+"");
		String sendDat = JSON.toJSONString(bean);
		HttpTranse Http = new HttpTranse();			
		Http.TranseWithServer(MenuShowActivity.this,"http://"+CookMasterApp.ServerIP+"/index.php/api/process", sendDat,responseHandler);
        
		
		List<Class<? extends IViewProvider>> providers = new ArrayList<Class<? extends IViewProvider>>();
		providers.add(menuCoverProvider.class);
		providers.add(menuIntroProider.class);
		providers.add(mainStuffProvider.class);
		providers.add(subStuffTitleProvider.class);
		providers.add(subStuffProvider.class);
		providers.add(flavorTitleProvider.class);
		providers.add(flavorProvider.class);
		providers.add(stepTitleProvider.class);
		providers.add(stepProvider.class);
		
		adpater = new MiltilViewListAdapter(this.getApplication(), mList, providers);
		menuShowList.setAdapter(adpater);
		MyScrollListener scrollListener = new MyScrollListener(adpater);
		menuShowList.setOnScrollListener(scrollListener);
		
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
		
	}
	
	
	AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler(){

		@Override
		public void onFailure(int arg0, Header[] arg1, byte[] arg2,
				Throwable arg3) {
			// TODO Auto-generated method stub
			Log.d("cook","与后台通信错");
			
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
			// TODO Auto-generated method stub
			String text = new String(arg2);
			recMenuBean =  JSON.parseObject(text, menuBean.class);
	    	Log.d("cook",text);
            Message message = new Message();
            message.what = 1;
            doActionHandler.sendMessage(message);	
		}
	};
	
	
	
	//消息处理
	private Handler doActionHandler = new Handler(){
		@Override
        public void handleMessage(Message msg) {        	
            super.handleMessage(msg);
            int msgId = msg.what;
            switch (msgId) {
                case 1:
                	loadMenuData(recMenuBean);
                	adpater.notifyDataSetChanged();
                    break;
                case 0:
                
                	break;
                default:
                    break;
            }
        }


	};
	
	private void loadMenuData(menuBean recMenuBean) {
		// TODO Auto-generated method stub
		mList.clear();
		//设置封面
		menuCover coverBean = new menuCover();
		coverBean.setCoverUrl(recMenuBean.getCover());
		coverBean.setFavorCont(recMenuBean.getFavoriteCount());
		mList.add(coverBean);
		menuIntro IntroBean = new menuIntro();
		IntroBean.setAvator(recMenuBean.getAvatar());
		IntroBean.setMenuName(recMenuBean.getTitle());
		IntroBean.setUsrName(recMenuBean.getUserName());
		IntroBean.setPubTime(recMenuBean.getReviewTime());
		IntroBean.setMenuInro(recMenuBean.getIntro());
		mList.add(IntroBean);
		mainStuff mainStuffBean = new mainStuff();
		mainStuffBean.setMainStuff(recMenuBean.getMainStuff().get(0));
		mList.add(mainStuffBean);
		
		if(recMenuBean.getMainStuff().size()>1)
		{
			subStuffTitle subStuffTitleBean = new subStuffTitle();
			mList.add(subStuffTitleBean);//添加辅料标题
			for(int i=1;i<recMenuBean.getMainStuff().size();i++)
			{
				subStuff subStuffBean = new subStuff();
				subStuffBean.setSubStuff(recMenuBean.getMainStuff().get(i));
				mList.add(subStuffBean);
			}
			
		}
		if(recMenuBean.getOtherStuff().size() > 0)
		{
			flavorTitle flavorTitleBean = new flavorTitle();
			mList.add(flavorTitleBean);
			for(int i=0;i<recMenuBean.getOtherStuff().size();i++)
			{
				flavor flavorBean = new flavor();
				flavorBean.setFlavor(recMenuBean.getOtherStuff().get(i));
				mList.add(flavorBean);
			}
			
		}
		
		if(recMenuBean.getSteps().size() > 0)
		{
			stepTitle stepTitleBean = new stepTitle();
			mList.add(stepTitleBean);
			for(int i=0; i<recMenuBean.getSteps().size();i++)
			{
				step stepBean = new step();
				stepBean.setPhotoURL(recMenuBean.getSteps().get(i).getStepPhoto());
				stepBean.setIntro(recMenuBean.getSteps().get(i).getIntro());
				stepBean.setStepNo(i+1);
				mList.add(stepBean);
				
			}
		}
		
		
	}

}
