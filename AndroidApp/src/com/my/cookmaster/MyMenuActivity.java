package com.my.cookmaster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyMenuActivity extends Activity{
	private RelativeLayout MenuUpLoad;
	private RelativeLayout MenuDownLoad;
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
		MenuUpLoad = (RelativeLayout)findViewById(R.id.myUploadBox);
		MenuDownLoad = (RelativeLayout)findViewById(R.id.myDownloadBox);
		
		CurTile.setText("我的菜谱");
		makeMenu.setText("发布菜谱");
		makeMenu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MyMenuActivity.this, MakeMenuActivity.class);
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
		
		
		
	};
	
	
}
