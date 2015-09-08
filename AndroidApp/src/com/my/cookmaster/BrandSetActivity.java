package com.my.cookmaster;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.alibaba.fastjson.JSON;
import com.j256.ormlite.dao.Dao;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.my.cookmaster.bean.bus_bean.brandView;
import com.my.cookmaster.bean.pro_bean.BrandBean;
import com.my.cookmaster.bean.pro_bean.FindBrandBean;
import com.my.cookmaster.bean.pro_bean.FindBrandRspBean;
import com.my.cookmaster.bean.pro_bean.GetBoxBean;
import com.my.cookmaster.bean.pro_bean.UpdateBrandBean;
import com.my.cookmaster.bus.HttpTranse;
import com.my.cookmaster.database.BoxDBBean;
import com.my.cookmaster.database.DBTool;
import com.my.cookmaster.view.listview.viewprovider.IItemBean;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.MiltilViewListAdapter;
import com.my.cookmaster.view.listview.viewprovider.MyScrollListener;
import com.my.cookmaster.view.listview.viewprovider.impl.brandProvider;
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
import android.widget.Toast;

public class BrandSetActivity extends Activity {
	private TextView CurTile;
	private Button backBtn;
	private TextView makeMenu;
	
	private EditText brandName;
	private EditText corporName;
	private EditText corporTel;
	private EditText corporAddr;
	private EditText corporUrl;
	private Boolean brandFoucs =false;
	private String brandKey;
	private BoxDBBean box_bean;
	private FindBrandRspBean brandRspBean;
	private GetBoxBean updateRspBean;
	
	private ListView  brandList;
	private brandView brandBean = new brandView();
	MiltilViewListAdapter brandAdpater;
	private List<IItemBean> brandSearchList = new ArrayList<IItemBean>();
	private BrandBean selectedBrand;
	private String selectedBoxMac;

	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.box_set_brand);
		CurTile = (TextView)findViewById(R.id.text_title);
		backBtn = (Button)findViewById(R.id.button_backward);
		makeMenu = (TextView)findViewById(R.id.button_forward);
		brandName = (EditText)findViewById(R.id.brand_name);
		corporName = (EditText)findViewById(R.id.corpor_name);
		corporTel = (EditText)findViewById(R.id.tel_num);
		corporAddr = (EditText)findViewById(R.id.address);
		corporUrl = (EditText)findViewById(R.id.url);
		brandList = (ListView)findViewById(R.id.brandList);
		CurTile.setText("品牌编辑");
		makeMenu.setText("保存");
		
		
		
		
		List<Class<? extends IViewProvider>> brandproviders = new ArrayList<Class<? extends IViewProvider>>();
		brandproviders.add(brandProvider.class);
		
		brandAdpater = new MiltilViewListAdapter(this.getApplication(), brandSearchList, brandproviders);
		brandList.setAdapter(brandAdpater);
		brandList.setOnScrollListener(new MyScrollListener(brandAdpater));
		
		
		makeMenu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
		        //读取盒子信息
		        Dao<BoxDBBean, Integer> daoBoxDBBean = null;     	
				List<BoxDBBean> list = null;
				try {
					daoBoxDBBean = DBTool.getDBHelper().getBoxDBBeanDao();
					list = daoBoxDBBean.queryForEq("box_mac", selectedBoxMac);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(list==null || list.size()<=0)
				{
					return;
				}
				box_bean = list.get(0);
				if(selectedBrand == null)
					box_bean.setBrand_id((long) -1);
				else
					box_bean.setBrand_id(selectedBrand.getBrand_id());
				box_bean.setBrand_name(brandName.getText().toString());
				box_bean.setCorpor_name(corporName.getText().toString());
				box_bean.setCorpor_tel(corporTel.getText().toString());
				box_bean.setCorpor_addr(corporAddr.getText().toString());
				box_bean.setCorpor_url(corporUrl.getText().toString());
				
				try {
					daoBoxDBBean.update(box_bean);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				UpdateBrandBean bean = new UpdateBrandBean();
				bean.setBox_mac(box_bean.getBox_mac());
				bean.setName(box_bean.getBrand_name());
				bean.setBrand_id(box_bean.getBrand_id());
				bean.setCorpor_addr(box_bean.getCorpor_addr());
				bean.setCorpor_name(box_bean.getCorpor_name());
				bean.setCorpor_tel(box_bean.getCorpor_tel());
				bean.setCorpor_url(box_bean.getCorpor_url());
				bean.setBox_id(box_bean.getBox_id());
				
				String sendDat = JSON.toJSONString(bean);
				HttpTranse Http = new HttpTranse();			
				Http.TranseWithServer(BrandSetActivity.this,"http://"+CookMasterApp.ServerIP+"/index.php/box_manager/update_box_brand", sendDat,updateRspHandler);
				
				
				
			}	

		});
		
		
		brandName.setOnFocusChangeListener(new View.OnFocusChangeListener(){

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
		
		brandName.addTextChangedListener(new TextWatcher(){

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
				brandKey = brandName.getText().toString();
				if(brandKey!=null && !"".equals(brandKey.trim())&&brandFoucs)
				{
					//向后台发送根据名称查品牌名的信息
					//List<String> nameLst = ;
					FindBrandBean bean = new FindBrandBean();
					bean.setBrandName(brandKey);
					String sendDat = JSON.toJSONString(bean);
					HttpTranse Http = new HttpTranse();			
					Http.TranseWithServer(BrandSetActivity.this,"http://"+CookMasterApp.ServerIP+"/index.php/brand_manager/fuzzy_find_by_name", sendDat,findbrandRspHandler);
				}
				
			}
			
		});
		
		
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		brandList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Log.d("cook","点击第"+arg2+"个项目");
				selectedBrand = brandRspBean.getBrandList().get(arg2);
				brandName.clearFocus();
				brandName.setText(selectedBrand.getName());
				corporName.setText(selectedBrand.getCorpor_name());
				corporTel.setText(selectedBrand.getTel_name());
				corporAddr.setText(selectedBrand.getAddress());
				corporUrl.setText(selectedBrand.getUrl());
				
				brandSearchList.clear();
				brandAdpater.notifyDataSetChanged();	
				
				
			}
			
		});
		
        Intent intent=getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent  
        Bundle bundle=intent.getExtras();//.getExtras()得到intent所附带的额外数据   
        selectedBoxMac = bundle.getString("box_mac");
        Log.d("cook",String.format("盒子mac=%s", selectedBoxMac));
        
        //读取盒子信息
        Dao<BoxDBBean, Integer> daoBoxDBBean = null;     	
		List<BoxDBBean> list = null;
		try {
			daoBoxDBBean = DBTool.getDBHelper().getBoxDBBeanDao();
			list = daoBoxDBBean.queryForEq("box_mac", selectedBoxMac);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(list==null || list.size()<=0)
		{
			return;
		}
		BoxDBBean box_bean = list.get(0);
		//初始化界面	
		initView(box_bean);
        
	}

	private void initView(BoxDBBean box_bean) {
		// TODO Auto-generated method stub
		brandName.setText(box_bean.getBrand_name());
		corporName.setText(box_bean.getCorpor_name());
		corporTel.setText(box_bean.getCorpor_tel());
		corporAddr.setText(box_bean.getCorpor_addr());
		corporUrl.setText(box_bean.getCorpor_url());
		
		
	}
	
	private Handler doActionHandler = new Handler(){
		@Override
        public void handleMessage(Message msg) {        	
            super.handleMessage(msg);
            int msgId = msg.what;
            switch (msgId) {
                case 1: 
                	fillBrandList(brandRspBean);
                	brandAdpater.notifyDataSetChanged();	
                	break;
                case 2: 
                	MisposFuction.ShowMessage("提交成功",BrandSetActivity.this);
                	saveDB(updateRspBean);
                	break;
                default:
                
                    break;
            }
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
            message.what = 1;
            doActionHandler.sendMessage(message);	
			
		}
		
	};
	
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
			updateRspBean =  JSON.parseObject(text, GetBoxBean.class);
	    	Log.d("cook",text);
            Message message = new Message();
            message.what = 2;
            doActionHandler.sendMessage(message);	
			
		}
		
	};
	
	
	private void fillBrandList(FindBrandRspBean brandRspBean) {
		// TODO Auto-generated method stub
		brandSearchList.clear();
		if(brandRspBean.getBrandList().size() == 0)//没有就为空
			selectedBrand = null;
		for(int i=0;i<brandRspBean.getBrandList().size();i++)
		{
			brandView Viewbean = new brandView();
			BrandBean  bean = brandRspBean.getBrandList().get(i);
			Viewbean.setBrandName(bean.getName());
			Viewbean.setCorporName(bean.getCorpor_name());
			Viewbean.setBrandRef(bean.getRef()+"");
			brandSearchList.add(Viewbean);
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
}
