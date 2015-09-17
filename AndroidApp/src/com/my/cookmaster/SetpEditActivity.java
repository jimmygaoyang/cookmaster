package com.my.cookmaster;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;

import com.alibaba.fastjson.JSON;
import com.my.cookmaster.bean.pro_bean.UploadMenuBean;
import com.my.cookmaster.bean.pro_bean.UploadStep;
import com.my.cookmaster.view.util.FileService;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
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
	private Bitmap myBitmap; 
	private byte[] mContent; 
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
//		hint = (TextView)findViewById(R.id.StepHint);
		stepContent = (EditText)findViewById(R.id.stepContent);
		
		
		CurTile.setText("步骤内容编辑");
		
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		publish.setText("确定");
		
		stepPhoto.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("cook","点击了图片");
		        final CharSequence[] items = { "相册", "拍照" };  
                
                AlertDialog dlg = new AlertDialog.Builder(SetpEditActivity.this).setTitle("选择照片").setItems(items,   
                        new DialogInterface.OnClickListener() {  
                              
                            @Override  
                            public void onClick(DialogInterface dialog, int which) {  
                                // TODO Auto-generated method stub   
                            //这里item是根据选择的方式，   在items数组里面定义了两种方式，拍照的下标为1所以就调用拍照方法     
                                if(which==1){  
                                    Intent getImageByCamera  = new Intent("android.media.action.IMAGE_CAPTURE");  
                                    startActivityForResult(getImageByCamera, 1);  
                                }else{  
                                    Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);  
                                    getImage.addCategory(Intent.CATEGORY_OPENABLE);  
                                    getImage.setType("image/jpeg");  
                                    startActivityForResult(getImage, 0);  
                                }  
                                  
                            }  
                        }).create();  
                dlg.show();  
			}
		});
		
		
		
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
		
		
	    Intent intent=getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent  
        Bundle bundle=intent.getExtras();//.getExtras()得到intent所附带的额外数据   
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
			
//				hint.setVisibility(View.INVISIBLE);
				
			}else
			{
//				hint.setText("点击添加图片");
			}
			stepContent.setText(uploadMenuBean.getSteps().get(StepIndex).getIntro());
		}
		else
		{
			stepContent.setHint("在这里添加步骤内容");
		}
		
	}
	
	
	
	
	
	 @Override  
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
	        // TODO Auto-generated method stub   
	        super.onActivityResult(requestCode, resultCode, data);  
	          
	        ContentResolver contentResolver  =getContentResolver();  
	         /** 
	         * 因为两种方式都用到了startActivityForResult方法，这个方法执行完后都会执行onActivityResult方法， 
	         * 所以为了区别到底选择了那个方式获取图片要进行判断，这里的requestCode跟startActivityForResult里面第二个参数对应 
	         */  
	          
	        if(requestCode==0){  
	              
	            //方式一   
	            /*try { 
	                 //获得图片的uri  
	                Uri orginalUri = data.getData(); 
	                  //将图片内容解析成字节数组  
	                mContent = readStream(contentResolver.openInputStream(Uri.parse(orginalUri.toString()))); 
	                 //将字节数组转换为ImageView可调用的Bitmap对象  
	                myBitmap  =getPicFromBytes(mContent,null); 
	                  ////把得到的图片绑定在控件上显示 
	                imageView.setImageBitmap(myBitmap); 
	            } catch (Exception e) { 
	                e.printStackTrace(); 
	                // TODO: handle exception 
	            }*/  
	              
	            //方式二   
	            try {  
	                Uri selectedImage = data.getData();  
	                String[] filePathColumn = { MediaStore.Images.Media.DATA };  
	  
	                Cursor cursor = getContentResolver().query(selectedImage,  
	                        filePathColumn, null, null, null);  
	                cursor.moveToFirst();  
	  
	                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);  
	                String picturePath = cursor.getString(columnIndex);  
	                cursor.close();  
	                stepPhoto.setImageBitmap(BitmapFactory.decodeFile(picturePath)); 
	            } catch (Exception e) {  
	                // TODO: handle exception   
	                e.printStackTrace();  
	            }  
	              
	              
	        }else if(requestCode==1){  
	            try {  
	                Bundle extras = data.getExtras();  
	                myBitmap = (Bitmap) extras.get("data");  
	                ByteArrayOutputStream baos = new ByteArrayOutputStream();       
	                myBitmap.compress(Bitmap.CompressFormat.JPEG , 100, baos);       
	                mContent=baos.toByteArray();  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	                // TODO: handle exception   
	            }  
	            stepPhoto.setImageBitmap(myBitmap);  
	        }  
	          
	    }  
	      
	   public static Bitmap getPicFromBytes(byte[] bytes, BitmapFactory.Options opts) {   
	        if (bytes != null)   
	            if (opts != null)   
	                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,opts);   
	            else   
	                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);   
	        return null;   
	    }   
	      
	      
	      
	 public static byte[] readStream(InputStream in) throws Exception{  
	     byte[] buffer  =new byte[1024];  
	     int len  =-1;  
	     ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
	       
	     while((len=in.read(buffer))!=-1){  
	         outStream.write(buffer, 0, len);  
	     }  
	     byte[] data  =outStream.toByteArray();  
	     outStream.close();  
	     in.close();  
	     return data;  
	 }  
	
	
}
