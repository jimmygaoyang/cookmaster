package com.my.cookmaster;

import android.app.Activity;
import android.os.Bundle;
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
		MenuUpLoad = (RelativeLayout)findViewById(R.id.button_backward);
	};
	
	
}
