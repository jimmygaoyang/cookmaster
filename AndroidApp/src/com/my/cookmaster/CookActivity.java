package com.my.cookmaster;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.j256.ormlite.dao.Dao;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.my.cookmaster.bean.bus_bean.Box;
import com.my.cookmaster.bean.bus_bean.BoxOperate;
import com.my.cookmaster.bean.pro_bean.GetBoxBean;
import com.my.cookmaster.bean.pro_bean.GetBoxListBean;
import com.my.cookmaster.bean.pro_bean.GetBoxListRspBean;
import com.my.cookmaster.bus.HttpTranse;
import com.my.cookmaster.bus.protocol.Command;
import com.my.cookmaster.bus.protocol.OutMaterialBean;
import com.my.cookmaster.bus.protocol.OutMaterialCommand;
import com.my.cookmaster.database.BoxDBBean;
import com.my.cookmaster.database.DBTool;
import com.my.cookmaster.view.listview.viewprovider.Callback;
import com.my.cookmaster.view.listview.viewprovider.IItemBean;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.MiltilViewListAdapter;
import com.my.cookmaster.view.listview.viewprovider.MyScrollListener;
import com.my.cookmaster.view.listview.viewprovider.impl.BoxAddProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.BoxOperateProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.BoxProvider;
import com.my.cookmaster.view.util.ITTProgress;
import com.my.cookmaster.view.util.MyDialog;
import com.my.cookmaster.view.util.MyProgress;
import com.my.cookmaster.view.viewpagerindicator.BaseFragment;

public class CookActivity extends BaseFragment{

	private TextView CurTile;
	private Button backBtn;
	private TextView makeMenu;
	private ListView BoxList;
	
	MiltilViewListAdapter adpater;
	private List<IItemBean> mList = new ArrayList<IItemBean>();
	private GetBoxListRspBean rspBean;
	
	private Long userid;
	private String selectedMacNum;
	
	@Override
	public void onResume() {
		super.onResume();
		refreshData();		
		

	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cook_activity, null, false);
		CurTile = (TextView)view.findViewById(R.id.text_title);
		backBtn = (Button)view.findViewById(R.id.button_backward);
		makeMenu = (TextView)view.findViewById(R.id.button_forward);
		BoxList = (ListView)view.findViewById(R.id.toolview);
		
		List<Class<? extends IViewProvider>> providers = new ArrayList<Class<? extends IViewProvider>>();
		providers.add(BoxOperateProvider.class);
//		providers.add(BoxProvider.class);
		
		adpater = new MiltilViewListAdapter(this.getActivity().getApplicationContext(), mList, providers);
		BoxList.setAdapter(adpater);
		adpater.setmCallback(new buttonClick());
		MyScrollListener scrollListener = new MyScrollListener(adpater);
		BoxList.setOnScrollListener(scrollListener);
		
		CurTile.setText("做菜");
		backBtn.setVisibility(View.INVISIBLE);
		makeMenu.setVisibility(View.INVISIBLE);
		
		
		refreshData();
		
		

		
		
		
		return view;
    
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
			BoxOperate boxBean = new BoxOperate();
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
		
	};
    
    
	private void initView(List<BoxDBBean> list)
	{
		mList.clear();
		BoxDBBean bean = null;
		for(int i=0;i<list.size(); i++ )
		{
			bean = list.get(i);
			BoxOperate boxBean = new BoxOperate();
			boxBean.setFlavorName(bean.getMaterial_name());
			boxBean.setFlavorBrand(bean.getBrand_name());
			boxBean.setBoxMac(bean.getBox_mac());
			mList.add(boxBean);
		}		
	}
	
	@Override
    public void refreshData()
    {
	   	 CookMasterApp app =  CookMasterApp.getInstance();
	     Context con = this.getActivity().getApplicationContext();
		SharedPreferences sysPre = con.getSharedPreferences(app.APPNAME,con.MODE_PRIVATE);
		userid = sysPre.getLong(app.UserId, 0);
		
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
		Http.TranseWithServer(this.getActivity().getApplicationContext(),"http://"+CookMasterApp.ServerIP+"/index.php/box_manager/get_list", sendDat,responseHandler);
    }
	
	class buttonClick implements Callback
	{
		@Override
		public void click(View v) {
			// TODO Auto-generated method stub
			outputMaterial();
			selectedMacNum = (String)v.getTag();
			//Toast.makeText(CookActivity.this.getActivity(),"listview的内部的按钮被点击了！，位置是-->" + (Integer) v.getTag(),Toast.LENGTH_SHORT).show();
		}
			
	};
		
	private void outputMaterial()
	{
		LayoutInflater inflater;
		inflater = LayoutInflater.from(CookActivity.this.getActivity());
		final View add_dlg_layout= inflater.inflate(R.layout.add_mount, null);
		final EditText money = (EditText) add_dlg_layout.findViewById(R.id.ed_mount);
//		EditFilter.setMoneyFilter(money);
		Button btnEdit = (Button) add_dlg_layout.findViewById(R.id.btn_add);
		Button btnCancel = (Button) add_dlg_layout.findViewById(R.id.btn_cancel);
		final MyDialog add_alert_dlg = new MyDialog.Builder(CookActivity.this.getActivity()).create();
		add_alert_dlg.setContentView(add_dlg_layout);

		btnEdit.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(money.getText().toString().equals(""))
				{
					CharSequence html1 = Html.fromHtml("<font color='blue'>不能为空</font>"); 
					money.setError(html1);
					money.requestFocus();
					return ;
				}
				else
				{
					int amount = Integer.parseInt(money.getText().toString());
					if(amount >= 500)
					{
						CharSequence html1 = Html.fromHtml("<font color='blue'>不能 超过500</font>"); 
						money.setError(html1);
						money.requestFocus();
						return ;
					}
					if(amount <= 0)
					{
						CharSequence html1 = Html.fromHtml("<font color='blue'>不能为0</font>"); 
						money.setError(html1);
						money.requestFocus();
						return ;
					}
				}


				add_alert_dlg.dismiss();
				ITTProgress _progress = new ITTProgress() {

					@Override
					public void progressRunnable(
							StringBuffer err) {
						// TODO Auto-generated method stub
						OutMaterialBean sendBean = new OutMaterialBean();
						sendBean.setMacNum(selectedMacNum);
						sendBean.setAmount(money.getText().toString());
						OutMaterialCommand output = new OutMaterialCommand(sendBean);//命令
						if (output.execute()!= Command.SUCCESS) {//执行命令
							err.append(output.getTransErr());
						}

					}

					@Override
					public void progressDismiss(String err) {
						// TODO Auto-generated method stub
						if(err.equals("")){
							Toast.makeText(CookActivity.this.getActivity(), "出料成功", Toast.LENGTH_SHORT).show();
							//MyDialog.show(CookActivity.this.getActivity(),"出料成功");
						}
						else{
							Toast.makeText(CookActivity.this.getActivity(), "出料失败", Toast.LENGTH_SHORT).show();
							//MyDialog.show(CookActivity.this.getActivity(),"出料失败:" + err.toString());
						}
					}
				};
				MyProgress.showDlg(CookActivity.this.getActivity(),
						"正在出料,请稍后...", _progress);

			}
		});
		btnCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				add_alert_dlg.dismiss();
			}
		});
		add_alert_dlg.show();
	}


	
	
}
