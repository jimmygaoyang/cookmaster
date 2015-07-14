package com.my.cookmaster;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.alibaba.fastjson.JSON;
import com.j256.ormlite.dao.Dao;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.my.cookmaster.bean.bus_bean.materialKind;
import com.my.cookmaster.bean.bus_bean.materialView;
import com.my.cookmaster.bean.pro_bean.BrandBean;
import com.my.cookmaster.bean.pro_bean.FindBrandRspBean;
import com.my.cookmaster.bean.pro_bean.FindMaterialBean;
import com.my.cookmaster.bean.pro_bean.FindMaterialKindBean;
import com.my.cookmaster.bean.pro_bean.FindMaterialKindRspBean;
import com.my.cookmaster.bean.pro_bean.FindMaterialRspBean;
import com.my.cookmaster.bean.pro_bean.GetBoxBean;
import com.my.cookmaster.bean.pro_bean.MaterialBean;
import com.my.cookmaster.bean.pro_bean.MaterialKindBean;
import com.my.cookmaster.bean.pro_bean.UpdateBoxMaterialBean;
import com.my.cookmaster.bus.HttpTranse;
import com.my.cookmaster.database.BoxDBBean;
import com.my.cookmaster.database.DBTool;
import com.my.cookmaster.view.listview.viewprovider.IItemBean;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.MiltilViewListAdapter;
import com.my.cookmaster.view.listview.viewprovider.MyScrollListener;
import com.my.cookmaster.view.listview.viewprovider.impl.kindProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.materialProvider;
import com.my.cookmaster.view.util.MisposFuction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class FlavorSetActivity extends Activity{
	private TextView CurTile;
	private Button backBtn;
	private TextView makeMenu;
	
	private EditText FlavorName;
	private EditText FlavorKind;
	private ListView FlavorList;
	private ListView FlavorKindList;
	private Button addBtn;
	private Button cancelBtn;
	
	MiltilViewListAdapter FlavorAdpater;
	private List<IItemBean> FlavorSearchList = new ArrayList<IItemBean>();
	private MaterialBean selectedFlavor;
	
	MiltilViewListAdapter KindAdpater;
	private List<IItemBean> KindSearchList = new ArrayList<IItemBean>();
	
	private String flavorKey;
	private String kindKey;
	
	private boolean flavorFoucs;
	private boolean kindFoucs;
	
	private Long box_id;
	private BoxDBBean box_bean;
	
	private FindMaterialRspBean materialRspBean;
	private FindMaterialKindRspBean kindRspBean;
	private GetBoxBean updateRspBean;
	private MaterialBean selectedMaterial =null;
	private MaterialKindBean selectedKind = null;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.box_set_flavor);
		CurTile = (TextView)findViewById(R.id.text_title);
		backBtn = (Button)findViewById(R.id.button_backward);
		makeMenu = (TextView)findViewById(R.id.button_forward);
		FlavorName =(EditText)findViewById(R.id.name_flavor);
		FlavorList = (ListView)findViewById(R.id.flavor_list);
		FlavorKind = (EditText)findViewById(R.id.flavor_kind); 
		FlavorKindList = (ListView)findViewById(R.id.flavor_kind_list);
		addBtn = (Button)findViewById(R.id.add_btn);
		CurTile.setText("调料编辑");
		
		
		List<Class<? extends IViewProvider>> providers = new ArrayList<Class<? extends IViewProvider>>();
		providers.add(materialProvider.class);
		
		FlavorAdpater = new MiltilViewListAdapter(this.getApplication(), FlavorSearchList, providers);
		FlavorList.setAdapter(FlavorAdpater);
		MyScrollListener scrollListener = new MyScrollListener(FlavorAdpater);
		FlavorList.setOnScrollListener(scrollListener);
		
		List<Class<? extends IViewProvider>> kindproviders = new ArrayList<Class<? extends IViewProvider>>();
		kindproviders.add(kindProvider.class);
		KindAdpater = new MiltilViewListAdapter(this.getApplication(), KindSearchList, kindproviders);
		FlavorKindList.setAdapter(KindAdpater);	
		FlavorKindList.setOnScrollListener(new MyScrollListener(KindAdpater));
		
		
        Intent intent=getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent  
        Bundle bundle=intent.getExtras();//.getExtras()得到intent所附带的额外数据   
        box_id = bundle.getLong("box_id");
        Log.d("cook",String.format("盒子id=%d", box_id));
		
        //读取盒子信息
        Dao<BoxDBBean, Integer> daoBoxDBBean = null;     	
		List<BoxDBBean> list = null;
		try {
			daoBoxDBBean = DBTool.getDBHelper().getBoxDBBeanDao();
			list = daoBoxDBBean.queryForEq("box_id", box_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(list==null || list.size()<=0)
		{
			return;
		}
		box_bean = list.get(0);
		initView(box_bean);
		
		addBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UpdateBoxMaterialBean bean = new UpdateBoxMaterialBean();
				bean.setBox_id(box_bean.getBox_id());
				bean.setMaterial_name(FlavorName.getText().toString());
				bean.setMaterial_kind_name(FlavorKind.getText().toString());
				if(selectedMaterial!=null)
					bean.setMaterial_id(selectedMaterial.getMaterial_id());
				if(selectedKind!=null)
					bean.setMaterial_kind_id(selectedKind.getMaterial_kind_id());
				String sendDat = JSON.toJSONString(bean);
				HttpTranse Http = new HttpTranse();			
				Http.TranseWithServer(FlavorSetActivity.this,"http://"+CookMasterApp.ServerIP+"/index.php/box_manager/update_box_material", sendDat,updateRspHandler);
			}
		});
		
		
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		FlavorName.setOnFocusChangeListener(new  View.OnFocusChangeListener()
		{

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus)
				{
					Log.d("cook","  得到焦点");
					flavorFoucs = true;
				}
				else
				{
					Log.d("cook","  失去焦点");
					flavorFoucs = false;
				}
			}
			
		});
		
		FlavorName.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				flavorKey = FlavorName.getText().toString();
				if(flavorKey!=null && !"".equals(flavorKey.trim())&&flavorFoucs)
				{
					//向后台发送根据名称查调料名的信息
					//List<String> nameLst = ;
					FindMaterialBean bean = new FindMaterialBean();
					bean.setMaterialName(flavorKey);
					String sendDat = JSON.toJSONString(bean);
					HttpTranse Http = new HttpTranse();			
					Http.TranseWithServer(FlavorSetActivity.this,"http://"+CookMasterApp.ServerIP+"/index.php/material_manager/fuzzy_find_by_name", sendDat,findMaterialRspHandler);
				}
				
			}
			
		});	
		
		FlavorList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Log.d("cook","点击第"+arg2+"个项目");
				selectedMaterial = materialRspBean.getMaterialList().get(arg2);
				FlavorName.clearFocus();
				FlavorName.setText(selectedMaterial.getName());
				FlavorKind.setText(selectedMaterial.getMaterial_kind_name());
				
				FlavorSearchList.clear();
				FlavorAdpater.notifyDataSetChanged();	
				
				
			}
			
		});
		
		
		FlavorKind.setOnFocusChangeListener(new  View.OnFocusChangeListener()
		{

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus)
				{
					Log.d("cook","  得到焦点");
					kindFoucs = true;
				}
				else
				{
					Log.d("cook","  失去焦点");
					kindFoucs = false;
				}
			}
			
		});
		
		FlavorKind.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				kindKey = FlavorKind.getText().toString();
				if(kindKey!=null && !"".equals(kindKey.trim())&&kindFoucs)
				{
					//向后台发送根据名称查调料种类的信息
					FindMaterialKindBean bean = new FindMaterialKindBean();
					bean.setKindName(kindKey);
					String sendDat = JSON.toJSONString(bean);
					HttpTranse Http = new HttpTranse();			
					Http.TranseWithServer(FlavorSetActivity.this,"http://"+CookMasterApp.ServerIP+"/index.php/material_kind_manager/fuzzy_find_by_name", sendDat,findKindRspHandler);
				}
				
			}
			
		});	
		
		FlavorKindList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Log.d("cook","点击第"+arg2+"个项目");
				selectedKind = kindRspBean.getMaterialKindList().get(arg2);
				FlavorKind.clearFocus();
				FlavorKind.setText(selectedKind.getKind_name());
				
				KindSearchList.clear();
				KindAdpater.notifyDataSetChanged();		
				
			}
			
		});
		
		
		
		
		
		
		
		
		
	}
	
	AsyncHttpResponseHandler updateRspHandler = new AsyncHttpResponseHandler(){

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
			updateRspBean  =  JSON.parseObject(text, GetBoxBean.class);
            Message message = new Message();
            message.what = 1;
            doActionHandler.sendMessage(message);	
			
		}
		
	};
	
	AsyncHttpResponseHandler findMaterialRspHandler = new AsyncHttpResponseHandler(){

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
			materialRspBean =  JSON.parseObject(text, FindMaterialRspBean.class);
	    	Log.d("cook",text);
            Message message = new Message();
            message.what = 2;
            doActionHandler.sendMessage(message);	
			
		}
		
	};
	
	AsyncHttpResponseHandler findKindRspHandler = new AsyncHttpResponseHandler(){

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
			kindRspBean =  JSON.parseObject(text, FindMaterialKindRspBean.class);
	    	Log.d("cook",text);
            Message message = new Message();
            message.what = 3;
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
                	MisposFuction.ShowMessage("编辑调料成功",FlavorSetActivity.this);
                	saveDB(updateRspBean);
                    break;
                case 2: 
                	fillMaterialList(materialRspBean);
                	FlavorAdpater.notifyDataSetChanged();	
                	break;
                case 3: 
                	fillKindList(kindRspBean);
                	KindAdpater.notifyDataSetChanged();	
                	break;
                default:
                    break;
            }
        }





	};
	
	
	private void fillMaterialList(FindMaterialRspBean bean)
	{
		FlavorSearchList.clear();
		if(bean.getMaterialList().size() == 0)//没有就为空
			selectedMaterial = null;
		for(int i=0;i<bean.getMaterialList().size();i++)
		{
			materialView Viewbean = new materialView();
			Viewbean.setMaterialBrand(bean.getMaterialList().get(i).getBrand_name());
			Viewbean.setMaterialName(bean.getMaterialList().get(i).getName());
			Viewbean.setMaterialRef(bean.getMaterialList().get(i).getRef()+"");
			FlavorSearchList.add(Viewbean);
		}
	}
	
	private void fillKindList(FindMaterialKindRspBean bean) {
		// TODO Auto-generated method stub
		KindSearchList.clear();
		if(bean.getMaterialKindList().size() == 0)//没有就为空
			selectedKind = null;
		for(int i=0;i<bean.getMaterialKindList().size();i++)
		{
			materialKind Viewbean = new materialKind();
			Viewbean.setKindName(bean.getMaterialKindList().get(i).getKind_name());		
			KindSearchList.add(Viewbean);
		}
	}
	
	private void saveDB(GetBoxBean updateRspBean) {
		// TODO Auto-generated method stub
		box_bean.setMaterial_id(updateRspBean.getMaterial_id());
		box_bean.setMaterial_kind_id(updateRspBean.getMaterial_kind_id());
		box_bean.setMaterial_name(updateRspBean.getMaterial_name());
		box_bean.setMaterial_kind_name(updateRspBean.getMaterial_kind_name());
		box_bean.setBrand_id(updateRspBean.getBrand_id());
		box_bean.setBrand_name(updateRspBean.getBrand_name());
		box_bean.setCorpor_name(updateRspBean.getCorpor_name());
		box_bean.setCorpor_tel(updateRspBean.getCorpor_tel());
		box_bean.setCorpor_addr(updateRspBean.getCorpor_addr());
		box_bean.setCorpor_url(updateRspBean.getCorpor_url());
		
		Dao<BoxDBBean, Integer> daoBoxDBBean;
		try {
			daoBoxDBBean = DBTool.getDBHelper().getBoxDBBeanDao();
			daoBoxDBBean.update(box_bean);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	private void initView(BoxDBBean box_bean) {
		// TODO Auto-generated method stub
		FlavorName.setText(box_bean.getMaterial_name());
		FlavorKind.setText(box_bean.getMaterial_kind_name());		
	}
	
	
}
