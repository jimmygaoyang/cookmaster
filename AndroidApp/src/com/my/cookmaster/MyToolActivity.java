package com.my.cookmaster;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.alibaba.fastjson.JSON;
import com.j256.ormlite.dao.Dao;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.my.cookmaster.bean.bus_bean.Box;
import com.my.cookmaster.bean.bus_bean.BoxAdd;
import com.my.cookmaster.bean.pro_bean.GetBoxBean;
import com.my.cookmaster.bean.pro_bean.GetBoxListBean;
import com.my.cookmaster.bean.pro_bean.GetBoxListRspBean;
import com.my.cookmaster.bean.pro_bean.addBoxBean;
import com.my.cookmaster.bean.pro_bean.addBoxRspBean;
import com.my.cookmaster.bus.HttpTranse;
import com.my.cookmaster.view.listview.viewprovider.IItemBean;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.MiltilViewListAdapter;
import com.my.cookmaster.view.listview.viewprovider.MyScrollListener;
import com.my.cookmaster.view.listview.viewprovider.impl.BoxAddProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.BoxProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.menuCoverProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.menuIntroProider;
import com.my.cookmaster.view.util.MisposFuction;
import com.my.cookmaster.database.DBTool;
import com.my.cookmaster.database.BoxDBBean;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MyToolActivity extends Activity {
	private TextView CurTile;
	private Button backBtn;
	private TextView makeMenu;
	private ListView toolList;
	
	MiltilViewListAdapter adpater;
	private List<IItemBean> mList = new ArrayList<IItemBean>();
	private GetBoxListRspBean rspBean;
	
	private Long userid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_tool_activity);
		CurTile = (TextView)findViewById(R.id.text_title);
		backBtn = (Button)findViewById(R.id.button_backward);
		makeMenu = (TextView)findViewById(R.id.button_forward);
		toolList = (ListView)findViewById(R.id.toolview);
		
		List<Class<? extends IViewProvider>> providers = new ArrayList<Class<? extends IViewProvider>>();
		providers.add(BoxProvider.class);
		providers.add(BoxAddProvider.class);
		
		
		CurTile.setText("我的厨具");
		
		adpater = new MiltilViewListAdapter(this.getApplication(), mList, providers);
		toolList.setAdapter(adpater);
		MyScrollListener scrollListener = new MyScrollListener(adpater);
		toolList.setOnScrollListener(scrollListener);
		
		
	
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	   	 CookMasterApp app =  CookMasterApp.getInstance();
	     Context con = MyToolActivity.this;
		SharedPreferences sysPre = con.getSharedPreferences(app.APPNAME,con.MODE_PRIVATE);
		userid = sysPre.getLong(app.UserId, 0);
		
//		GetBoxListBean bean = new GetBoxListBean();
//		bean.setUser_id(userid);
//		String sendDat = JSON.toJSONString(bean);
//		HttpTranse Http = new HttpTranse();			
//		Http.TranseWithServer(this,"http://"+CookMasterApp.ServerIP+"/index.php/box_manager/get_list", sendDat,responseHandler);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//先数据库
        
		//读取盒子信息
        Dao<BoxDBBean, Integer> daoBoxDBBean = null;     	
		List<BoxDBBean> list = null;
		try {
			daoBoxDBBean = DBTool.getDBHelper().getBoxDBBeanDao();
			list = daoBoxDBBean.queryForAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initView(list);

		//后网络
		GetBoxListBean bean = new GetBoxListBean();
		bean.setUser_id(userid);
		String sendDat = JSON.toJSONString(bean);
		HttpTranse Http = new HttpTranse();			
		Http.TranseWithServer(this,"http://"+CookMasterApp.ServerIP+"/index.php/box_manager/get_list", sendDat,responseHandler);
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
			rspBean =  JSON.parseObject(text, GetBoxListRspBean.class);
	    	Log.d("cook",text);
            Message message = new Message();
            message.what = 1;
            doActionHandler.sendMessage(message);	
		}

	};
	
	private Handler doActionHandler = new Handler(){
		@Override
        public void handleMessage(Message msg) {        	
            super.handleMessage(msg);
            int msgId = msg.what;
            switch (msgId) {

                case 1: 
                	fillBoxList(rspBean);
                	adpater.notifyDataSetChanged();	
                	break;
                default:
                    break;
            }
        }
	};
	
	
	
	private void fillBoxList(GetBoxListRspBean rspBean) {
		// TODO Auto-generated method stub
		mList.clear();
		GetBoxBean bean =  new GetBoxBean();
        Dao<BoxDBBean, Integer> daoBoxDBBean = null;
        try {
			daoBoxDBBean = DBTool.getDBHelper().getBoxDBBeanDao();
		    //删除所有
	        daoBoxDBBean.executeRaw("delete from BoxDBBean ");
	        daoBoxDBBean.executeRaw("update sqlite_sequence SET seq = 0 where name = 'boxdbbean' ");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
 
        
      
		for(int i=0;i<rspBean.getBoxList().size(); i++ )
		{
			bean = rspBean.getBoxList().get(i);
			Box boxBean = new Box();
			boxBean.setFlavorName(bean.getMaterial_name());
			boxBean.setFlavorBrand(bean.getBrand_name());
			boxBean.setBoxMac(bean.getBox_mac());
			mList.add(boxBean);
			// shujuku

			BoxDBBean BoxArg = new BoxDBBean();
			BoxArg.setBox_id(bean.getBox_id());
			BoxArg.setBox_mac(bean.getBox_mac());
			BoxArg.setBrand_id(bean.getBrand_id());
			BoxArg.setBrand_name(bean.getBrand_name());
			BoxArg.setMaterial_id(bean.getMaterial_id());
			BoxArg.setMaterial_kind_id(bean.getMaterial_kind_id());
			BoxArg.setMaterial_name(bean.getMaterial_name());
			BoxArg.setMaterial_kind_name(bean.getMaterial_kind_name());
			BoxArg.setSw_ver(bean.getSw_ver());
			BoxArg.setCorpor_name(bean.getCorpor_name());
			BoxArg.setCorpor_tel(bean.getCorpor_tel());
			BoxArg.setCorpor_addr(bean.getCorpor_addr());
			BoxArg.setCorpor_url(bean.getCorpor_url());
			//插入一条新的记录
			try {
				daoBoxDBBean.create(BoxArg);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}					

		}
		BoxAdd boxAddBean = new BoxAdd();
		mList.add(boxAddBean);
		
		
	};
	
	private void initView(List<BoxDBBean> list)
	{
		mList.clear();
		BoxDBBean bean = null;
		for(int i=0;i<list.size(); i++ )
		{
			bean = list.get(i);
			Box boxBean = new Box();
			boxBean.setFlavorName(bean.getMaterial_name());
			boxBean.setFlavorBrand(bean.getBrand_name());
			boxBean.setBoxMac(bean.getBox_mac());
			mList.add(boxBean);
		}
		BoxAdd boxAddBean = new BoxAdd();
		mList.add(boxAddBean);
		
		
		
		
		
	}
}
