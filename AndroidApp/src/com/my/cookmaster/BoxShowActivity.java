package com.my.cookmaster;

import java.sql.SQLException;
import java.util.List;

import org.apache.http.Header;








//import com.aisino.midinvoice.global.EditFilter;
import com.my.cookmaster.view.util.ITTProgress;
import com.my.cookmaster.view.util.MyDialog;
import com.my.cookmaster.view.util.MyProgress;
import com.alibaba.fastjson.JSON;
import com.j256.ormlite.dao.Dao;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.my.cookmaster.bean.pro_bean.UpdateBoxMacBean;
import com.my.cookmaster.bean.pro_bean.UpdateBrandBean;
import com.my.cookmaster.bus.HttpTranse;
import com.my.cookmaster.bus.protocol.Command;
import com.my.cookmaster.bus.protocol.OutMaterialBean;
import com.my.cookmaster.bus.protocol.OutMaterialCommand;
import com.my.cookmaster.database.BoxDBBean;
import com.my.cookmaster.database.DBTool;
import com.my.cookmaster.database.BoxDBBean;
import com.my.cookmaster.view.util.MisposFuction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BoxShowActivity extends Activity {
	private TextView CurTile;
	private Button backBtn;
	private TextView makeMenu;
	private Button outputBtn;
	
	private TextView flavorName;
	private TextView flavorKind;
	private EditText boxNumber;
//	private EditText boxMacEdt;
	private TextView brandName;
	private TextView corporName;
	private TextView Tel;
	private TextView address;
	private TextView url;
	
	private ImageView macEdit;
	private ImageView flavorEdit;
	private ImageView brandEdit;
	private boolean macEditFlag = false;
	private UpdateBoxMacBean updateBean;
	
	private String box_mac;
	private Long box_id;
	
	LayoutInflater inflater;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.box_show_activity);
		CurTile = (TextView)findViewById(R.id.text_title);
		backBtn = (Button)findViewById(R.id.button_backward);
		makeMenu = (TextView)findViewById(R.id.button_forward);
		outputBtn = (Button)findViewById(R.id.ouput);
		
		boxNumber = (EditText)findViewById(R.id.box_mac);
		//boxMacEdt = (EditText)findViewById(R.id.mac_edit);
		flavorName = (TextView)findViewById(R.id.flavor);
		flavorKind = (TextView)findViewById(R.id.flavor_kind);
		brandName = (TextView)findViewById(R.id.brand_name);
		corporName = (TextView)findViewById(R.id.corpor_name);
		Tel = (TextView)findViewById(R.id.corpor_tel);
		address = (TextView)findViewById(R.id.corpor_addr);
		url = (TextView)findViewById(R.id.corpor_url);
		
		macEdit = (ImageView)findViewById(R.id.box_mac_edit);
		flavorEdit = (ImageView)findViewById(R.id.flavor_edit);
		brandEdit = (ImageView)findViewById(R.id.brand_edit);
		inflater = LayoutInflater.from(this);
		
		
		
		
		outputBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				outputMaterial();
				
				
				
			}
		});
		
		boxNumber.clearFocus();
		boxNumber.setOnFocusChangeListener(new View.OnFocusChangeListener()
		{

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if(arg1)
				{
					Log.d("cook","  得到焦点");
					macEdit.setBackgroundResource(R.drawable.save);
					macEditFlag = true;
				}
				else
				{
					Log.d("cook","  失去焦点");
				}
			}
			});
		macEdit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!macEditFlag)
				{
					//boxMacEdt.setVisibility(View.VISIBLE);
					boxNumber.requestFocus();
					macEdit.setBackgroundResource(R.drawable.save);
					macEditFlag = true;
				}
				else
				{
					//boxMacEdt.setVisibility(View.INVISIBLE);
					//boxNumber.setVisibility(View.VISIBLE);
					boxNumber.clearFocus();
					macEdit.setBackgroundResource(R.drawable.edit);
					macEditFlag = false;
					
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
					BoxDBBean box_bean = list.get(0);
					box_bean.setBox_mac(boxNumber.getText().toString());
					try {
						daoBoxDBBean.update(box_bean);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//上传到后台
					UpdateBoxMacBean bean = new UpdateBoxMacBean();
					bean.setBox_mac(boxNumber.getText().toString());
					bean.setBox_id(box_bean.getBox_id());
					String sendDat = JSON.toJSONString(bean);
					HttpTranse Http = new HttpTranse();			
					Http.TranseWithServer(BoxShowActivity.this,"http://"+CookMasterApp.ServerIP+"/index.php/box_manager/update_box_mac", sendDat,updateRspHandler);
				}

			}
		});
		
		brandEdit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(BoxShowActivity.this, BrandSetActivity.class);
				intent.putExtra("box_mac",boxNumber.getText().toString() );
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				BoxShowActivity.this.startActivity(intent);
			}
		});
		
		flavorEdit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(BoxShowActivity.this, FlavorSetActivity.class);
				intent.putExtra("box_id",box_id);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				BoxShowActivity.this.startActivity(intent);
			}
		});
		
		

		
		
		
		
		CurTile.setText("调料盒设置");
		
        Intent intent=getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent  
        Bundle bundle=intent.getExtras();//.getExtras()得到intent所附带的额外数据   
        box_mac = bundle.getString("box_mac");
        Log.d("cook",String.format("盒子mac=%s", box_mac));
        
//        //读取盒子信息
//        Dao<BoxDBBean, Integer> daoBoxDBBean = null;     	
//		List<BoxDBBean> list = null;
//		try {
//			daoBoxDBBean = DBTool.getDBHelper().getBoxDBBeanDao();
//			list = daoBoxDBBean.queryForEq("box_mac", box_mac);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if(list==null || list.size()<=0)
//		{
//			return;
//		}
//		BoxDBBean box_bean = list.get(0);
//		//初始化界面	
//		initView(box_bean);
		
		
		
		
        backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
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
			list = daoBoxDBBean.queryForEq("box_mac", box_mac);
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
		//后网络
	}
	
	private void initView(BoxDBBean box_bean) {
		// TODO Auto-generated method stub
		boxNumber.setText(box_bean.getBox_mac());
		flavorName.setText(box_bean.getMaterial_name());
		flavorKind.setText(box_bean.getMaterial_kind_name());
		brandName.setText(box_bean.getBrand_name());
		
		corporName.setText(box_bean.getCorpor_name());
		Tel.setText(box_bean.getCorpor_tel());
		address.setText(box_bean.getCorpor_addr());
		url.setText(box_bean.getCorpor_url());
		
		box_id = box_bean.getBox_id();
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
			updateBean =  JSON.parseObject(text, UpdateBoxMacBean.class);
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
                	MisposFuction.ShowMessage("修改编号成功",BoxShowActivity.this);
                	break;
                default:                
                    break;
            }
        }

	};
	
	
	private void outputMaterial()
	{
		final View add_dlg_layout= inflater.inflate(R.layout.add_mount, null);
		final EditText money = (EditText) add_dlg_layout.findViewById(R.id.ed_mount);
//		EditFilter.setMoneyFilter(money);
		Button btnEdit = (Button) add_dlg_layout.findViewById(R.id.btn_add);
		Button btnCancel = (Button) add_dlg_layout.findViewById(R.id.btn_cancel);
		final MyDialog add_alert_dlg = new MyDialog.Builder(BoxShowActivity.this).create();
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
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// TODO Auto-generated method stub
						OutMaterialBean sendBean = new OutMaterialBean();
						sendBean.setMacNum(box_mac);
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
							//MyDialog.show(BoxShowActivity.this,"出料成功");
							Toast.makeText(BoxShowActivity.this, "出料成功", Toast.LENGTH_SHORT).show();
						}
						else{
							Toast.makeText(BoxShowActivity.this, "出料失败", Toast.LENGTH_SHORT).show();
							//MyDialog.show(BoxShowActivity.this,"出料失败:" + err.toString());
						}
					}
				};
				MyProgress.showDlg(BoxShowActivity.this,
						"正在出料,请稍后...", _progress);

			}
		});
		add_alert_dlg.show();
	}
	
}
