package com.my.cookmaster.database;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DBHelper extends OrmLiteSqliteOpenHelper {
	private static final String DATABASE_NAME = "cookmaster.db";  
	private static final int DATABASE_VERSION = 2; 
	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	private Dao<BoxDBBean, Integer> BoxDBBeanDao = null;

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource connectionSource) {

		try {
			TableUtils.createTable(connectionSource, BoxDBBean.class);
		} catch (SQLException e) {
			Log.e(DBHelper.class.getName(), "创建数据库失败", e);
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int arg2,
			int arg3) {

		try {
			TableUtils.dropTable(connectionSource, BoxDBBean.class, true);
			onCreate(db, connectionSource);



		} catch (SQLException e) {
			Log.e(DBHelper.class.getName(), "更新数据库失败", e);
			e.printStackTrace();
		}
	}
	
	@Override



	public void close() {
		super.close();
		BoxDBBeanDao=null;
	}


	public Dao<BoxDBBean, Integer> getBoxDBBeanDao() throws SQLException {
		if (BoxDBBeanDao == null) {

			BoxDBBeanDao = getDao(BoxDBBean.class);
		}

		return BoxDBBeanDao;
	}
	

}
