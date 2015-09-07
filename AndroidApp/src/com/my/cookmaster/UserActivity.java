package com.my.cookmaster;

import org.apache.http.Header;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.my.cookmaster.bean.pro_bean.loginBean;
import com.my.cookmaster.bean.pro_bean.loginRspBean;
import com.my.cookmaster.bus.HttpTranse;
import com.my.cookmaster.view.util.MisposFuction;
import com.my.cookmaster.view.viewpagerindicator.BaseFragment;

import android.content.SharedPreferences;

public class UserActivity extends BaseFragment{

	private RelativeLayout MenuBar;
	private RelativeLayout setBar;
	private RelativeLayout toolBar;
	private Button loginBtn;
	private RelativeLayout logBox;
	private TextView logInfo;
	
	private TextView CurTile;
	private Button backBtn;
	private TextView makeMenu;
	private loginRspBean rspBean = new loginRspBean();
	
	private String  name;
	private String password;
	
//	@Override 
//	public void onCreate(Bundle savedInstanceState) 
//	{ 
//	// TODO Auto-generated method stub 
//	super.onCreate(savedInstanceState);
//	this.getActivity().setContentView(R.layout.user_activity);
//	
//     loginBtn = (Button)getActivity().findViewById(R.id.login);
//     logInfo = (TextView)getActivity().findViewById(R.id.loginHint);
//	} 
	

//	@Override 
//	public void onResume() 
//	{ 
//	// TODO Auto-generated method stub 
//		super.onResume();
//        CookMasterApp app =  CookMasterApp.getInstance();
//        Context con = UserActivity.this.getActivity();
//		SharedPreferences sysPre = con.getSharedPreferences(app.APPNAME,con.MODE_PRIVATE);
//		SharedPreferences.Editor editor = sysPre.edit();
//		Boolean res = sysPre.getBoolean(app.LogInState, false);
//		if(res)
//		{
//			loginBtn.setVisibility(View.INVISIBLE);
//			logInfo.setText("欢迎回来");
//		}
//		else{
//			loginBtn.setVisibility(View.VISIBLE);
//			logInfo.setText("登陆后，你可以上传菜谱、管理智能厨具");
//		}
//	} 
	
	
//	@Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            //相当于Fragment的onResume
//            CookMasterApp app =  CookMasterApp.getInstance();
//            Context con = UserActivity.this.getActivity();
//    		SharedPreferences sysPre = con.getSharedPreferences(app.APPNAME,con.MODE_PRIVATE);
//    		SharedPreferences.Editor editor = sysPre.edit();
//    		Boolean res = sysPre.getBoolean(app.LogInState, false);
//    		if(res)
//    		{
//    			loginBtn.setVisibility(View.INVISIBLE);
//    			logInfo.setText("欢迎回来");
//    		}
//    		else{
//    			loginBtn.setVisibility(View.VISIBLE);
//    			logInfo.setText("登陆后，你可以上传菜谱、管理智能厨具");
//    		}
//        	
//        } else {
//            //相当于Fragment的onPause
//        }
//    }
	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_activity, null, false);
//        TextView textView = (TextView) view.findViewById(R.id.text);
		CurTile = (TextView)view.findViewById(R.id.text_title);
		backBtn = (Button)view.findViewById(R.id.button_backward);
		makeMenu = (TextView)view.findViewById(R.id.button_forward);
        loginBtn = (Button)view.findViewById(R.id.login);
        logBox = (RelativeLayout)view.findViewById(R.id.logBox);
        logInfo = (TextView)view.findViewById(R.id.loginHint);
        toolBar = (RelativeLayout)view.findViewById(R.id.mytoolBox);
        setBar = (RelativeLayout)view.findViewById(R.id.mysetBox);
        backBtn.setVisibility(View.INVISIBLE);
        
        MenuBar = (RelativeLayout)view.findViewById(R.id.myMenuBox);
        
        MenuBar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				Intent intent = new Intent(UserActivity.this.getActivity(), MyMenuActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				UserActivity.this.getActivity().startActivity(intent);
			}
		});
        
        setBar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("cook","点击了设置");
			}
		});
        toolBar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(UserActivity.this.getActivity(), MyToolActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				UserActivity.this.getActivity().startActivity(intent);
			}
		});
        CurTile.setText(getTitle());
        
        CookMasterApp app =  CookMasterApp.getInstance();
        Context con = this.getActivity();
		SharedPreferences sysPre = con.getSharedPreferences(app.APPNAME,con.MODE_PRIVATE);
		SharedPreferences.Editor editor = sysPre.edit();
		Boolean res = sysPre.getBoolean(app.LogInState, false);
		Long Id = sysPre.getLong(app.UserId, 0);
		name = sysPre.getString(app.UserName, "null");
		password = sysPre.getString(app.PassWord, "null");
		if(res)
		{
			loginBtn.setVisibility(View.INVISIBLE);
			logInfo.setText("欢迎回来 "+name);
			makeMenu.setText("退出");
			makeMenu.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//退出
					loginBean sendBean = new loginBean();
					sendBean.setName(name);
					sendBean.setPassword(password);
					String sendDat = JSON.toJSONString(sendBean);
					HttpTranse Http = new HttpTranse();			
					Http.TranseWithServer(UserActivity.this.getActivity().getApplicationContext(),"http://"+CookMasterApp.ServerIP+"/index.php/user_manager/logout", sendDat,rspHandler);	
				}
			});
			
		}
		else{
			makeMenu.setVisibility(View.INVISIBLE);
			loginBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(UserActivity.this.getActivity(), LoginActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					UserActivity.this.getActivity().startActivity(intent);
					
				}
			});
		}
		
		

		editor.commit();
        
        return view;
    }
    
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
                	MisposFuction.ShowMessage("退出成功",UserActivity.this.getActivity());
                	 CookMasterApp app =  CookMasterApp.getInstance();
                     Context con = UserActivity.this.getActivity();
             		SharedPreferences sysPre = con.getSharedPreferences(app.APPNAME,con.MODE_PRIVATE);
             		SharedPreferences.Editor editor = sysPre.edit();
             		editor.putBoolean(app.LogInState, false);
             		editor.commit();
             		
        			makeMenu.setVisibility(View.INVISIBLE);
        			loginBtn.setVisibility(View.VISIBLE);
        			logInfo.setText(R.string.loginHint);
        			loginBtn.setOnClickListener(new View.OnClickListener() {
        				
        				@Override
        				public void onClick(View v) {
        					// TODO Auto-generated method stub
        					Intent intent = new Intent(UserActivity.this.getActivity(), LoginActivity.class);
        					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        					UserActivity.this.getActivity().startActivity(intent);
        					
        				}
        			});
                	break;
                default:
                    break;
            }
        }
	};
	
	
	@Override
    public void refreshData()
    {
    
    }

    
}