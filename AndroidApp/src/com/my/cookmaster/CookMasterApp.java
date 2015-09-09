package com.my.cookmaster;

import android.app.Application;

public class CookMasterApp extends Application {
	private static CookMasterApp instance;
	public final String APPNAME = "SystemSet";
	public final String LogInState = "loginState";
	public final String UserId = "UserId";
	public final String UserName = "UserName";
	public final String PassWord = "PassWord";
	public static final String ServerIP = "192.168.1.104";
	public static CookMasterApp getInstance()
	{
		return instance;
	}
    @Override
    public void onCreate() {
    	// TODO Auto-generated method stub
    	super.onCreate();
    	instance = this;
    }
	
}
