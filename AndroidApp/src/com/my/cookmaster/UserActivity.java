package com.my.cookmaster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.my.cookmaster.view.viewpagerindicator.BaseFragment;

import android.content.SharedPreferences;

public class UserActivity extends BaseFragment{

	private RelativeLayout setBar;
	private RelativeLayout toolBar;
	private Button loginBtn;
	private RelativeLayout logBox;
	private TextView logInfo;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_activity, null, false);
        TextView textView = (TextView) view.findViewById(R.id.text);
        loginBtn = (Button)view.findViewById(R.id.login);
        logBox = (RelativeLayout)view.findViewById(R.id.logBox);
        logInfo = (TextView)view.findViewById(R.id.loginHint);
        toolBar = (RelativeLayout)view.findViewById(R.id.mytoolBox);
        setBar = (RelativeLayout)view.findViewById(R.id.mysetBox);
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
        textView.setText(getTitle());
        
        CookMasterApp app =  CookMasterApp.getInstance();
        Context con = this.getActivity();
		SharedPreferences sysPre = con.getSharedPreferences(app.APPNAME,con.MODE_PRIVATE);
		SharedPreferences.Editor editor = sysPre.edit();
		Boolean res = sysPre.getBoolean(app.LogInState, false);
		if(res)
		{
			loginBtn.setVisibility(View.INVISIBLE);
			logInfo.setText("欢迎回来");
		}
		else{
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
}