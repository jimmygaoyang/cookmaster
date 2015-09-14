package com.my.cookmaster;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SetpEditActivity extends Activity {
	private TextView CurTile;
	private Button backBtn;
	private TextView publish;
	private ImageView stepPhoto;
	private TextView hint;
	private EditText stepContent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.step_edit_activity);
		CurTile = (TextView)findViewById(R.id.text_title);
		backBtn = (Button)findViewById(R.id.button_backward);
		publish = (TextView)findViewById(R.id.button_forward);
		
		stepPhoto = (ImageView)findViewById(R.id.StepPhoto);
		hint = (TextView)findViewById(R.id.StepHint);
		stepContent = (EditText)findViewById(R.id.stepContent);
		
		CurTile.setText("≤Ω÷Ëƒ⁄»›±‡º≠");
		
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
		
		
		
	}
	
	
	
}
