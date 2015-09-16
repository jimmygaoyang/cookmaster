package com.my.cookmaster;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import com.alibaba.fastjson.JSON;
import com.my.cookmaster.bean.pro_bean.UploadMenuBean;
import com.my.cookmaster.bean.pro_bean.UploadStep;
import com.my.cookmaster.view.util.FileService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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
	private String menuPath;
	private FileService fileSer;
	private boolean IsCreate;
	private int StepIndex;
	private UploadMenuBean uploadMenuBean ;
	
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
		
		
		CurTile.setText("�������ݱ༭");
		
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		publish.setText("ȷ��");
		publish.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(IsCreate == true)
				{
					UploadStep stepBean = new UploadStep();
					stepBean.setStepImgRes(null);
					stepBean.setIntro(stepContent.getText().toString());
					stepBean.setStepIndex(uploadMenuBean.getSteps().size()+1);
					stepBean.setUploadSuccess(false);				
					uploadMenuBean.getSteps().add(stepBean);
										
				}
				else
				{
					uploadMenuBean.getSteps().get(StepIndex).setStepImgRes(null);
					uploadMenuBean.getSteps().get(StepIndex).setIntro(stepContent.getText().toString());
				}
				
			    //save to json file
		        String sendDat = JSON.toJSONString(uploadMenuBean);       
		        fileSer = new FileService(SetpEditActivity.this);
		        

		        String path = Constant.DOING_MENU_PATH;

		        path = Environment.getExternalStorageDirectory()+Constant.DOING_MENU_PATH+menuPath;

		        try {
					fileSer.saveToSDCard(path,"json.txt", sendDat);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
		        
		        SetpEditActivity.this.finish();
			}
			
			
		});
		
		
	    Intent intent=getIntent();//getIntent������Ŀ�а�����ԭʼintent����������������������intent��ֵ��һ��Intent���͵ı���intent  
        Bundle bundle=intent.getExtras();//.getExtras()�õ�intent�������Ķ�������   
        menuPath = bundle.getString("menuPath");
        IsCreate = bundle.getBoolean("IsCreate");
        
        if(IsCreate == false)
        {
        	StepIndex = bundle.getInt("StepIndex")-1;
        }
        
        LoadStepContent();	
		
	}

	private void LoadStepContent() {
		// TODO Auto-generated method stub
		fileSer = new FileService(this);
		String content = null;
		try {
			content = fileSer.read(Environment.getExternalStorageDirectory()+Constant.DOING_MENU_PATH+menuPath+"/json.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		uploadMenuBean = JSON.parseObject(content,UploadMenuBean.class);
		if(IsCreate == false)
		{
			if(uploadMenuBean.getSteps().get(StepIndex).getStepImgRes() != null)
			{
				
			}
			hint.setVisibility(View.INVISIBLE);
			stepContent.setText(uploadMenuBean.getSteps().get(StepIndex).getIntro());
		}
		else
		{
			hint.setText("������ͼƬ");
			stepContent.setHint("��������Ӳ�������");
		}
		
	}
	
	
	
}
