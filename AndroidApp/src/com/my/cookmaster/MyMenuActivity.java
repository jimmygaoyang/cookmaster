package com.my.cookmaster;

import java.io.File;

import com.my.cookmaster.MakeMenuActivity.buttonClick;
import com.my.cookmaster.view.listview.viewprovider.MiltilViewListAdapter;
import com.my.cookmaster.view.listview.viewprovider.MyScrollListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyMenuActivity extends Activity{
	private RelativeLayout MenuUpLoad;
	private RelativeLayout MenuDownLoad;
	private RelativeLayout MenuDoing;
	
	private TextView UpLoadNum;
	private TextView DownLoadNum;
	private TextView DoingNum;
	
	private TextView CurTile;
	private Button backBtn;
	private TextView makeMenu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_menu_activity);
		CurTile = (TextView)findViewById(R.id.text_title);
		backBtn = (Button)findViewById(R.id.button_backward);
		makeMenu = (TextView)findViewById(R.id.button_forward);
		MenuUpLoad = (RelativeLayout)findViewById(R.id.myUploadMenu);
		MenuDownLoad = (RelativeLayout)findViewById(R.id.myDownloadMenu);
		MenuDoing  = (RelativeLayout)findViewById(R.id.myDoingMenu);
		
		UpLoadNum = (TextView)findViewById(R.id.upLoadNum);
		DownLoadNum = (TextView)findViewById(R.id.downLoadNum);
		DoingNum = (TextView)findViewById(R.id.doingNum);
		
		
		
		CurTile.setText("我的菜谱");
		makeMenu.setText("发布菜谱");
		makeMenu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MyMenuActivity.this, MakeMenuActivity.class);
				intent.putExtra("isCreate", true);
				//				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				MyMenuActivity.this.startActivity(intent);
			}
		});
		
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		MenuDoing.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MyMenuActivity.this, MyMenuDoingActivity.class);
				MyMenuActivity.this.startActivity(intent);
			}
		});
		
		
			
		
	}
	@Override
	public void onResume() {
		super.onResume();
		loadMenuNum();
	}
	
	private void loadMenuNum() {
		// TODO Auto-generated method stub
		String SDPath = Environment.getExternalStorageDirectory()+"";
        File fd = new File(SDPath+Constant.PUBLISHED_MENU_PATH);//没有文件就创建
		if(!fd.exists()){
			fd.mkdirs();
		}
        fd = new File(SDPath+Constant.DOWNLOAD_MENU_PATH);//没有文件就创建
		if(!fd.exists()){
			fd.mkdirs();
		}
        fd = new File(SDPath+Constant.DOING_MENU_PATH);//没有文件就创建
		if(!fd.exists()){
			fd.mkdirs();
		}
		File[] uploadFiles = new File(SDPath+Constant.PUBLISHED_MENU_PATH).listFiles();
		UpLoadNum.setText(uploadFiles.length+"");
		File[] downFiles = new File(SDPath+Constant.DOWNLOAD_MENU_PATH).listFiles();
		DownLoadNum.setText(downFiles.length+"");
		File[] doingFiles = new File(SDPath+Constant.DOING_MENU_PATH).listFiles();
		DoingNum.setText(doingFiles.length+"");
		
		
	};

//	private void getFiles(ArrayList<File> fileList, String path) {  
//	    File[] allFiles = new File(path).listFiles();  
//	    for (int i = 0; i < allFiles.length; i++) {  
//	        File file = allFiles[i];  
//	        if (file.isFile()) {  
//	            fileList.add(file);  
//	        } else if (!file.getAbsolutePath().contains(".thumnail")) {  
//	            getFiles(fileList, file.getAbsolutePath());  
//	        }  
//	    } 
	
	
}
