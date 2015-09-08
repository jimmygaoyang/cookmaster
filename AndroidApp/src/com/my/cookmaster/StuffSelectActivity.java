package com.my.cookmaster;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.my.cookmaster.bean.bus_bean.MkMenuMainStuff;
import com.my.cookmaster.bean.bus_bean.brandView;
import com.my.cookmaster.bean.bus_bean.materialKind;
import com.my.cookmaster.bean.bus_bean.materialView;
import com.my.cookmaster.bean.pro_bean.BrandBean;
import com.my.cookmaster.bean.pro_bean.FindBrandBean;
import com.my.cookmaster.bean.pro_bean.FindBrandRspBean;
import com.my.cookmaster.bean.pro_bean.FindMaterialBean;
import com.my.cookmaster.bean.pro_bean.FindMaterialKindBean;
import com.my.cookmaster.bean.pro_bean.FindMaterialKindRspBean;
import com.my.cookmaster.bean.pro_bean.FindMaterialRspBean;
import com.my.cookmaster.bean.pro_bean.MaterialBean;
import com.my.cookmaster.bean.pro_bean.MaterialKindBean;
import com.my.cookmaster.bus.HttpTranse;
import com.my.cookmaster.view.listview.viewprovider.IItemBean;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.MiltilViewListAdapter;
import com.my.cookmaster.view.listview.viewprovider.MyScrollListener;
import com.my.cookmaster.view.listview.viewprovider.impl.brandProvider;
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

public class StuffSelectActivity extends Activity {

	private TextView CurTile;
	private Button backBtn;
	private TextView makeMenu;
	private EditText StuffName;
	private EditText StuffKind;
	private EditText StuffBrand;
	private ListView StuffList;
	private ListView StuffKindList;
	private ListView StuffBrandList;
	
	MiltilViewListAdapter StuffAdpater;
	private List<IItemBean> StuffSearchList = new ArrayList<IItemBean>();
	MiltilViewListAdapter KindAdpater;
	private List<IItemBean> KindSearchList = new ArrayList<IItemBean>();
	MiltilViewListAdapter BrandAdpater;
	private List<IItemBean> BrandSearchList = new ArrayList<IItemBean>();
	
	private String StuffKey;
	private String kindKey;
	private String brandKey;
	private boolean StuffFoucs;
	private boolean kindFoucs;
	private boolean brandFoucs;
	
	private FindMaterialRspBean materialRspBean;
	private FindMaterialKindRspBean kindRspBean;
	private FindBrandRspBean brandRspBean;
	
	private MaterialBean selectedMaterial =null;
	private MaterialKindBean selectedKind = null;
	private BrandBean selectedBrand = null;
	
	private int position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_stuff_select);
		CurTile = (TextView)findViewById(R.id.text_title);
		backBtn = (Button)findViewById(R.id.button_backward);
		makeMenu = (TextView)findViewById(R.id.button_forward);
		StuffName = (EditText)findViewById(R.id.name_stuff);
		StuffList = (ListView)findViewById(R.id.stuff_list);
		StuffKind = (EditText)findViewById(R.id.stuff_kind);
		StuffKindList = (ListView)findViewById(R.id.stuff_kind_list);
		StuffBrand = (EditText)findViewById(R.id.stuff_brand);
		StuffBrandList = (ListView)findViewById(R.id.stuff_brand_list);		
		
		CurTile.setText("食材选择");
		makeMenu.setText("确定");
		//食材名称
		List<Class<? extends IViewProvider>> providers = new ArrayList<Class<? extends IViewProvider>>();
		providers.add(materialProvider.class);		
		StuffAdpater = new MiltilViewListAdapter(this.getApplication(), StuffSearchList, providers);
		StuffList.setAdapter(StuffAdpater);
		MyScrollListener scrollListener = new MyScrollListener(StuffAdpater);
		StuffList.setOnScrollListener(scrollListener);
		//食材种类
		List<Class<? extends IViewProvider>> kindproviders = new ArrayList<Class<? extends IViewProvider>>();
		kindproviders.add(kindProvider.class);
		KindAdpater = new MiltilViewListAdapter(this.getApplication(), KindSearchList, kindproviders);
		StuffKindList.setAdapter(KindAdpater);	
		StuffKindList.setOnScrollListener(new MyScrollListener(KindAdpater));
		//品牌
		List<Class<? extends IViewProvider>> brandproviders = new ArrayList<Class<? extends IViewProvider>>();
		brandproviders.add(brandProvider.class);
		BrandAdpater = new MiltilViewListAdapter(this.getApplication(), BrandSearchList, brandproviders);
		StuffBrandList.setAdapter(BrandAdpater);	
		StuffBrandList.setOnScrollListener(new MyScrollListener(BrandAdpater));
		
		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();//.getExtras()得到intent所附带的额外数据  
		position = bundle.getInt("pos");
		 
		
		//食材交互
		StuffName.setOnFocusChangeListener(new  View.OnFocusChangeListener()
		{

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus)
				{
					Log.d("cook","  得到焦点");
					StuffFoucs = true;
				}
				else
				{
					Log.d("cook","  失去焦点");
					StuffFoucs = false;
				}
			}
			
		});
		
		StuffName.addTextChangedListener(new TextWatcher()
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
				StuffKey = StuffName.getText().toString();
				if(StuffKey!=null && !"".equals(StuffKey.trim())&&StuffFoucs)
				{
					//向后台发送根据名称查调料名的信息
					//List<String> nameLst = ;
					FindMaterialBean bean = new FindMaterialBean();
					bean.setMaterialName(StuffKey);
					String sendDat = JSON.toJSONString(bean);
					HttpTranse Http = new HttpTranse();			
					Http.TranseWithServer(StuffSelectActivity.this,"http://"+CookMasterApp.ServerIP+"/index.php/material_manager/fuzzy_find_by_name", sendDat,findMaterialRspHandler);
				}
				
			}
			
		});	
		
		StuffList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Log.d("cook","点击第"+arg2+"个项目");
				selectedMaterial = materialRspBean.getMaterialList().get(arg2);
				StuffName.clearFocus();
				StuffName.setText(selectedMaterial.getName());
				StuffKind.setText(selectedMaterial.getMaterial_kind_name());
				StuffBrand.setText(selectedMaterial.getBrand_name());
				
				StuffSearchList.clear();
				StuffAdpater.notifyDataSetChanged();	
			}
			
		});
		
		
		//种类交互
		StuffKind.setOnFocusChangeListener(new  View.OnFocusChangeListener()
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
		
		StuffKind.addTextChangedListener(new TextWatcher()
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
				kindKey = StuffKind.getText().toString();
				if(kindKey!=null && !"".equals(kindKey.trim())&&kindFoucs)
				{
					//向后台发送根据名称查调料种类的信息
					FindMaterialKindBean bean = new FindMaterialKindBean();
					bean.setKindName(kindKey);
					String sendDat = JSON.toJSONString(bean);
					HttpTranse Http = new HttpTranse();			
					Http.TranseWithServer(StuffSelectActivity.this,"http://"+CookMasterApp.ServerIP+"/index.php/material_kind_manager/fuzzy_find_by_name", sendDat,findKindRspHandler);
				}
				
			}
			
		});	
		
		StuffKindList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Log.d("cook","点击第"+arg2+"个项目");
				selectedKind = kindRspBean.getMaterialKindList().get(arg2);
				StuffKind.clearFocus();
				StuffKind.setText(selectedKind.getKind_name());
				
				KindSearchList.clear();
				KindAdpater.notifyDataSetChanged();		
				
			}
			
		});
		
		//品牌交互
		StuffBrand.setOnFocusChangeListener(new View.OnFocusChangeListener(){

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if(arg1)
				{
					Log.d("cook","  得到焦点");
					brandFoucs = true;
				}
				else
				{
					Log.d("cook","  失去焦点");
					brandFoucs = false;
				}
			}
			
		});
		
		StuffBrand.addTextChangedListener(new TextWatcher(){

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
				brandKey = StuffBrand.getText().toString();
				if(brandKey!=null && !"".equals(brandKey.trim())&&brandFoucs)
				{
					//向后台发送根据名称查品牌名的信息
					//List<String> nameLst = ;
					FindBrandBean bean = new FindBrandBean();
					bean.setBrandName(brandKey);
					String sendDat = JSON.toJSONString(bean);
					HttpTranse Http = new HttpTranse();			
					Http.TranseWithServer(StuffSelectActivity.this,"http://"+CookMasterApp.ServerIP+"/index.php/brand_manager/fuzzy_find_by_name", sendDat,findbrandRspHandler);
				}
				
			}
			
		});
		StuffBrandList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Log.d("cook","点击第"+arg2+"个项目");
				selectedBrand = brandRspBean.getBrandList().get(arg2);
				StuffBrand.clearFocus();
				StuffBrand.setText(selectedBrand.getName());
				
				BrandSearchList.clear();
				BrandAdpater.notifyDataSetChanged();	
				
				
			}
			
		});
		
		
		
		
		
		backBtn.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(StuffSelectActivity.this, MakeMenuActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				StuffSelectActivity.this.startActivity(intent);
			}
		});
		
		makeMenu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(selectedMaterial == null)
				{
					selectedMaterial = new MaterialBean();
					selectedMaterial.setName(StuffName.getText().toString());
					selectedMaterial.setMaterial_kind_name(StuffKind.getText().toString());
					selectedMaterial.setBrand_name(StuffBrand.getText().toString());
				}
				((MkMenuMainStuff)MakeMenuActivity.instance.mList.get(position)).setMaterial(selectedMaterial);
//				Intent intent = new Intent(StuffSelectActivity.this, MakeMenuActivity.class);
//				StuffSelectActivity.this.startActivity(intent);
				StuffSelectActivity.this.finish();
			}
		});
		
		
		
		
	}
	
	

	
	
	
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
            message.what = 1;
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
            message.what = 2;
            doActionHandler.sendMessage(message);	
			
		}
		
	};
	
	AsyncHttpResponseHandler findbrandRspHandler = new AsyncHttpResponseHandler(){

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
			brandRspBean =  JSON.parseObject(text, FindBrandRspBean.class);
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
                case 3:
                	fillBrandList(brandRspBean);
                	BrandAdpater.notifyDataSetChanged();	
                    break;
                case 1: 
                	fillMaterialList(materialRspBean);
                	StuffAdpater.notifyDataSetChanged();	
                	break;
                case 2: 
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
		StuffSearchList.clear();
		if(bean.getMaterialList().size() == 0)//没有就为空
			selectedMaterial = null;
		for(int i=0;i<bean.getMaterialList().size();i++)
		{
			materialView Viewbean = new materialView();
			Viewbean.setMaterialBrand(bean.getMaterialList().get(i).getBrand_name());
			Viewbean.setMaterialName(bean.getMaterialList().get(i).getName());
			Viewbean.setMaterialRef(bean.getMaterialList().get(i).getRef()+"");
			StuffSearchList.add(Viewbean);
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
	
	private void fillBrandList(FindBrandRspBean brandRspBean) {
		// TODO Auto-generated method stub
		BrandSearchList.clear();
		if(brandRspBean.getBrandList().size() == 0)//没有就为空
			StuffBrandList = null;
		for(int i=0;i<brandRspBean.getBrandList().size();i++)
		{
			brandView Viewbean = new brandView();
			BrandBean  bean = brandRspBean.getBrandList().get(i);
			Viewbean.setBrandName(bean.getName());
			Viewbean.setCorporName(bean.getCorpor_name());
			Viewbean.setBrandRef(bean.getRef()+"");
			BrandSearchList.add(Viewbean);
		}
	}
	
	
}
