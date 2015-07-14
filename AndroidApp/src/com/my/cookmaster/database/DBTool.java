package com.my.cookmaster.database;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateFormat;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.my.cookmaster.CookMasterApp;

/**
 * @author
 *数据库工具类
 */
public class DBTool {
	public static Context context;
	private static DBHelper databaseHelper=null;
	public static DBHelper getDBHelper() {

		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager
			.getHelper(CookMasterApp.getInstance(), DBHelper.class);
		}
		return databaseHelper;
	}


}

