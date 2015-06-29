package com.my.cookmaster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.Header;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.my.cookmaster.bean.bus_bean.materialView;
import com.my.cookmaster.bean.bus_bean.subStuff;
import com.my.cookmaster.bean.pro_bean.FindMaterialBean;
import com.my.cookmaster.bean.pro_bean.FindMaterialRspBean;
import com.my.cookmaster.bean.pro_bean.GetMenuBean;
import com.my.cookmaster.bean.pro_bean.MaterialBean;
import com.my.cookmaster.bean.pro_bean.addBoxBean;
import com.my.cookmaster.bean.pro_bean.addBoxRspBean;
import com.my.cookmaster.bean.pro_bean.loginRspBean;
import com.my.cookmaster.bus.HttpTranse;
import com.my.cookmaster.view.listview.viewprovider.IItemBean;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.MiltilViewListAdapter;
import com.my.cookmaster.view.listview.viewprovider.MyScrollListener;
import com.my.cookmaster.view.listview.viewprovider.impl.materialProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.menuCoverProvider;
import com.my.cookmaster.view.util.MisposFuction;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ToolAddActivity extends Activity {
	private EditText BoxMac;
	private EditText FlavorName;
	private EditText FlavorBrand;
	private Button addBtn;
	private Button cancelBtn;
	
	private TextView CurTile;
	private Button backBtn;
	private TextView makeMenu;	
	private ListView  flavorList;
	private addBoxRspBean rspBean;
	private FindMaterialRspBean materialRspBean;
	private String flavorKey;
	
	private materialView Bean = new materialView();
	MiltilViewListAdapter adpater;
	private List<IItemBean> mList = new ArrayList<IItemBean>();
	
	private MaterialBean selectedMaterial;
	
	private boolean nameFoucs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.box_add_activity);
		
		CurTile = (TextView)findViewById(R.id.text_title);
		backBtn = (Button)findViewById(R.id.button_backward);
		makeMenu = (TextView)findViewById(R.id.button_forward);
		flavorList = (ListView)findViewById(R.id.flavorList);
		
		
		CurTile.setText("调料盒添加");
		makeMenu.setVisibility(View.INVISIBLE);
		
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
		BoxMac = (EditText)findViewById(R.id.box_mac);
		FlavorName = (EditText)findViewById(R.id.flavor_name);
		FlavorBrand = (EditText)findViewById(R.id.flavor_brand);
		
		addBtn = (Button)findViewById(R.id.add_btn);
		cancelBtn = (Button)findViewById(R.id.cancle_btn);	
		
		
		
		List<Class<? extends IViewProvider>> providers = new ArrayList<Class<? extends IViewProvider>>();
		providers.add(materialProvider.class);
		
		adpater = new MiltilViewListAdapter(this.getApplication(), mList, providers);
		flavorList.setAdapter(adpater);
		MyScrollListener scrollListener = new MyScrollListener(adpater);
		flavorList.setOnScrollListener(scrollListener);
		
		FlavorName.setOnFocusChangeListener(new View.OnFocusChangeListener(){

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if(arg1)
				{
					Log.d("cook","  得到焦点");
					nameFoucs = true;
				}
				else
				{
					Log.d("cook","  失去焦点");
					nameFoucs = false;
				}
			}
			
		});
		
		FlavorName.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				flavorKey = FlavorName.getText().toString();
				if(flavorKey!=null && !"".equals(flavorKey.trim())&&nameFoucs)
				{
					//向后台发送根据名称查调料名的信息
					//List<String> nameLst = ;
					FindMaterialBean bean = new FindMaterialBean();
					bean.setMaterialName(flavorKey);
					String sendDat = JSON.toJSONString(bean);
					HttpTranse Http = new HttpTranse();			
					Http.TranseWithServer(ToolAddActivity.this,"http://"+CookMasterApp.ServerIP+"/index.php/material_manager/fuzzy_find_by_name", sendDat,findMaterialRspHandler);
				}
				
			}
			
		});
		
		
		addBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		
				addBoxBean bean = new addBoxBean();
				bean.setBox_mac(BoxMac.getText().toString());
				CookMasterApp app =  CookMasterApp.getInstance();
				Context con = ToolAddActivity.this;
				SharedPreferences sysPre = con.getSharedPreferences(app.APPNAME,con.MODE_PRIVATE);
				bean.setUser_id(sysPre.getLong(app.UserId, 0));
				if(!FlavorName.getText().toString().equals(""))
					bean.setMaterial_name(FlavorName.getText().toString());
				if(!FlavorBrand.getText().toString().equals(""))
					bean.setBrand_name(FlavorBrand.getText().toString());
//				bean.setBrand_name("djkffj");
//				bean.setMaterial_name("中国");
				String sendDat = JSON.toJSONString(bean);
				HttpTranse Http = new HttpTranse();			
				Http.TranseWithServer(ToolAddActivity.this,"http://"+CookMasterApp.ServerIP+"/index.php/box_manager/add", sendDat,responseHandler);
			}
		});
		
		flavorList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Log.d("cook","点击第"+arg2+"个项目");
				selectedMaterial = materialRspBean.getMaterialList().get(arg2);
				FlavorName.clearFocus();
				FlavorName.setText(selectedMaterial.getName());
				FlavorBrand.setText(selectedMaterial.getBrand_name());
				
				mList.clear();
				adpater.notifyDataSetChanged();	
				
				
			}
			
		});
		
	}
	
	AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler(){

		@Override
		public void onFailure(int arg0, Header[] arg1, byte[] arg2,
				Throwable arg3) {
			// TODO Auto-generated method stub
			String text = new String(arg2);
			Log.d("cook","与后台通信错");
			
			
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
			// TODO Auto-generated method stub
			String text = new String(arg2);			
			rspBean =  JSON.parseObject(text, addBoxRspBean.class);
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
                	MisposFuction.ShowMessage("添加调料成功",ToolAddActivity.this);
                    break;
                case 2: 
                	fillMaterialList(materialRspBean);
                	adpater.notifyDataSetChanged();	

                	break;
                default:
                    break;
            }
        }
	};
	
	
	
	AsyncHttpResponseHandler findMaterialRspHandler = new AsyncHttpResponseHandler(){

		@Override
		public void onFailure(int arg0, Header[] arg1, byte[] arg2,
				Throwable arg3) {
			// TODO Auto-generated method stub
			String text = new String(arg2);
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
	
	private void fillMaterialList(FindMaterialRspBean bean)
	{
		mList.clear();
		for(int i=0;i<bean.getMaterialList().size();i++)
		{
			materialView Viewbean = new materialView();
			Viewbean.setMaterialBrand(bean.getMaterialList().get(i).getBrand_name());
			Viewbean.setMaterialName(bean.getMaterialList().get(i).getName());
			Viewbean.setMaterialRef(bean.getMaterialList().get(i).getRef()+"");
			mList.add(Viewbean);
		}
	}
}
