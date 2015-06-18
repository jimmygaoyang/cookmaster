package com.my.cookmaster;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.my.cookmaster.bean.bus_bean.mainStuff;
import com.my.cookmaster.bean.bus_bean.menuCover;
import com.my.cookmaster.bean.bus_bean.menuIntro;
import com.my.cookmaster.bean.pro_bean.AdMenuListBean;
import com.my.cookmaster.bean.pro_bean.GetMenuBean;
import com.my.cookmaster.bean.pro_bean.menuBean;
import com.my.cookmaster.bus.HttpTranse;
import com.my.cookmaster.view.listview.viewprovider.IItemBean;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.MiltilViewListAdapter;
import com.my.cookmaster.view.listview.viewprovider.MyScrollListener;
import com.my.cookmaster.view.listview.viewprovider.impl.AdMenuProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.mainStuffProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.menuCoverProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.menuIntroProider;

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
	//���׵���ϸbean
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
		
		CurTile.setText("����");
		
        Intent intent=getIntent();//getIntent������Ŀ�а�����ԭʼintent����������������������intent��ֵ��һ��Intent���͵ı���intent  
        Bundle bundle=intent.getExtras();//.getExtras()�õ�intent�������Ķ�������   
        Long menuID = (Long) bundle.get("menuID");
        Log.d("cook",String.format("����id=%d", menuID));
		//���ͻ�ȡ�����ļ�
		GetMenuBean bean = new GetMenuBean();
		bean.setMenuID(menuID+"");
		String sendDat = JSON.toJSONString(bean);
		HttpTranse Http = new HttpTranse();			
		Http.TranseWithServer(MenuShowActivity.this,"http://192.168.28.86/index.php/api/process", sendDat,responseHandler);
        
		
		List<Class<? extends IViewProvider>> providers = new ArrayList<Class<? extends IViewProvider>>();
		providers.add(menuCoverProvider.class);
		providers.add(menuIntroProider.class);
		providers.add(mainStuffProvider.class);
		
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
			Log.d("cook","���̨ͨ�Ŵ�");
			
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
	
	
	
	//��Ϣ����
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
		//���÷���
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
		
		
	}

}