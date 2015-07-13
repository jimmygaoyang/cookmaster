package com.my.cookmaster;

import org.apache.http.Header;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.my.cookmaster.bean.pro_bean.AdMenuListBean;
import com.my.cookmaster.bean.pro_bean.GetAdvertiseMenu;
import com.my.cookmaster.bean.pro_bean.loginBean;
import com.my.cookmaster.bean.pro_bean.loginRspBean;
import com.my.cookmaster.bus.HttpTranse;
import com.my.cookmaster.view.util.MisposFuction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

	private TextView CurTile;
	private Button backBtn;
	private TextView makeMenu;
	private EditText nameEdt;
	private EditText passEdt;
	private Button signUpBtn;
	private Button signInBtn;
	private loginRspBean rspBean = new loginRspBean();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		CurTile = (TextView)findViewById(R.id.text_title);
		backBtn = (Button)findViewById(R.id.button_backward);
		makeMenu = (TextView)findViewById(R.id.button_forward);
		nameEdt = (EditText)findViewById(R.id.name);
		passEdt = (EditText)findViewById(R.id.pass);
		signUpBtn = (Button)findViewById(R.id.sign_up);
		signInBtn = (Button)findViewById(R.id.sign_in);
		
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});
		
		CurTile.setText("注册与登录");
		makeMenu.setVisibility(View.INVISIBLE);
		
		signUpBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//注册
				loginBean sendBean = new loginBean();
				sendBean.setName(nameEdt.getText().toString());
				sendBean.setPassword(passEdt.getText().toString());
				String sendDat = JSON.toJSONString(sendBean);
				HttpTranse Http = new HttpTranse();			
				Http.TranseWithServer(LoginActivity.this.getApplicationContext(),"http://"+CookMasterApp.ServerIP+"/index.php/user_manager/register", sendDat,responseHandler);	
			}
		});	
		
		signInBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 登陆
				loginBean sendBean = new loginBean();
				sendBean.setName(nameEdt.getText().toString());
				sendBean.setPassword(passEdt.getText().toString());
				String sendDat = JSON.toJSONString(sendBean);
				HttpTranse Http = new HttpTranse();			
				Http.TranseWithServer(LoginActivity.this.getApplicationContext(),"http://"+CookMasterApp.ServerIP+"/index.php/user_manager/login", sendDat,rspHandler);	
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
			rspBean =  JSON.parseObject(text, loginRspBean.class);
	    	Log.d("cook",text);
            Message message = new Message();
            message.what = 1;
            doActionHandler.sendMessage(message);	
		}

	};
	
	AsyncHttpResponseHandler rspHandler = new AsyncHttpResponseHandler(){

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
			rspBean =  JSON.parseObject(text, loginRspBean.class);
	    	Log.d("cook",text);
            Message message = new Message();
            message.what = 2;
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
                	MisposFuction.ShowMessage("注册成功",LoginActivity.this);
                    break;
                case 2: 
                	MisposFuction.ShowMessage("登陆成功",LoginActivity.this);
                	 CookMasterApp app =  CookMasterApp.getInstance();
                     Context con = LoginActivity.this;
             		SharedPreferences sysPre = con.getSharedPreferences(app.APPNAME,con.MODE_PRIVATE);
             		SharedPreferences.Editor editor = sysPre.edit();
             		editor.putBoolean(app.LogInState, true);
             		editor.putLong(app.UserId, rspBean.getUserID());
             		editor.putString(app.UserName, nameEdt.getText().toString());
             		editor.putString(app.PassWord, passEdt.getText().toString());
             		editor.putInt("whichPage", 3);
             		editor.commit();
                	break;
                default:
                    break;
            }
        }
	};
}
