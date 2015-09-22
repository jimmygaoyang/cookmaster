package com.my.cookmaster;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	private String BitMapurl = null;
	private UploadMenuBean uploadMenuBean ;
	private Uri imageUri; //ͼƬ·��
	private static final int CROP_BITMAP =0 ;
	private static final int SHOW_BITMAP =1 ;
	private static final int FROM_FILE =2 ;
	private static final int FROM_CAMER =3 ;

	
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
		
		
		CurTile.setText("�������ݱ༭");
		
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		publish.setText("ȷ��");
		
		stepPhoto.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("cook","�����ͼƬ");
		        final CharSequence[] items = { "���", "����" };  
                
                AlertDialog dlg = new AlertDialog.Builder(SetpEditActivity.this).setTitle("ѡ����Ƭ").setItems(items,   
                        new DialogInterface.OnClickListener() {  
                              
                            @Override  
                            public void onClick(DialogInterface dialog, int which) {  
                                // TODO Auto-generated method stub   
                            //����item�Ǹ���ѡ��ķ�ʽ��   ��items�������涨�������ַ�ʽ�����յ��±�Ϊ1���Ծ͵������շ���     
                                if(which==1){  
                    	    		//ͼƬ���� ʱ������
                    	    		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                    	            Date date = new Date(System.currentTimeMillis());
                    	            String filename = format.format(date);
                    	    		//����File�������ڴ洢���յ�ͼƬ SD����Ŀ¼           
                    	    		//File outputImage = new File(Environment.getExternalStorageDirectory(),"test.jpg");
                    	    		//�洢��DCIM�ļ���
                    	    		String path = Environment.getExternalStorageDirectory()+Constant.DOING_MENU_PATH+menuPath; 
                    	    		File outputImage = new File(path,filename+".jpg");
                    	    		try {
                    	    			if(outputImage.exists()) {
                    	     				outputImage.delete();
                    	    			}
                    	    			outputImage.createNewFile();
                    	    		} catch(IOException e) {
                    	    			e.printStackTrace();
                    	    		}
                    	    		//��File����ת��ΪUri�������������
                    	    		imageUri = Uri.fromFile(outputImage);
                                    Intent getImageByCamera  = new Intent("android.media.action.IMAGE_CAPTURE");  
                                    getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); //ָ��ͼƬ�����ַ
                                    startActivityForResult(getImageByCamera, SetpEditActivity.FROM_CAMER);  
                                }else{  
                                    Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);  
                                    getImage.addCategory(Intent.CATEGORY_OPENABLE);  
                                    getImage.setType("image/jpeg");  
                                    startActivityForResult(getImage, SetpEditActivity.FROM_FILE);  
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
					stepBean.setStepImgRes(BitMapurl);
					stepBean.setIntro(stepContent.getText().toString());
					stepBean.setStepIndex(uploadMenuBean.getSteps().size()+1);
					stepBean.setUploadSuccess(false);				
					uploadMenuBean.getSteps().add(stepBean);
										
				}
				else
				{
					uploadMenuBean.getSteps().get(StepIndex).setStepImgRes(BitMapurl);
					uploadMenuBean.getSteps().get(StepIndex).setIntro(stepContent.getText().toString());
				}

				
				
			    //save to json file
		        String sendDat = JSON.toJSONString(uploadMenuBean);       
		        fileSer = new FileService(SetpEditActivity.this);
		        

		        String path = Constant.DOING_MENU_PATH;

		        path = Environment.getExternalStorageDirectory()+Constant.DOING_MENU_PATH+menuPath;

				//delete useless jpg
				File[] uploadFiles = new File(path).listFiles();
				boolean delFlag = true;
				for(int i = 0;i<uploadFiles.length; i++)
				{
					for(int j=0;j<uploadMenuBean.getSteps().size();j++)
					{
						String fileName = uploadFiles[i].getPath();
						if(fileName.equals(uploadMenuBean.getCoverUrl()) 
								|| fileName.equals(uploadMenuBean.getSteps().get(j).getStepImgRes()))
						{//�����ļ� ɾ��
							delFlag = false;							
						}
					}
					if(delFlag == true)
						uploadFiles[i].delete();
					
				}
		        
		        
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
			
				BitMapurl = uploadMenuBean.getSteps().get(StepIndex).getStepImgRes();
                FileInputStream fis = null;
				try {
					fis = new FileInputStream(uploadMenuBean.getSteps().get(StepIndex).getStepImgRes());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                Bitmap bp = BitmapFactory.decodeStream(fis);
                stepPhoto.setImageBitmap(bp); 
				
			}else
			{
//				hint.setText("������ͼƬ");
			}
			stepContent.setText(uploadMenuBean.getSteps().get(StepIndex).getIntro());
		}
		else
		{
			stepContent.setHint("��������Ӳ�������");
		}
		
	}
	
	
	
	
	
	 @Override  
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
	        // TODO Auto-generated method stub   
	        super.onActivityResult(requestCode, resultCode, data);  
	          
	        ContentResolver contentResolver  =getContentResolver();  
	         /** 
	         * ��Ϊ���ַ�ʽ���õ���startActivityForResult�������������ִ����󶼻�ִ��onActivityResult������ 
	         * ����Ϊ�����𵽵�ѡ�����Ǹ���ʽ��ȡͼƬҪ�����жϣ������requestCode��startActivityForResult����ڶ���������Ӧ 
	         */  
	          switch(requestCode)
	          {
	          case SetpEditActivity.FROM_FILE:
	                Uri selectedImage = data.getData();  
	                String[] filePathColumn = { MediaStore.Images.Media.DATA };  
	  
	                Cursor cursor = getContentResolver().query(selectedImage,  
	                        filePathColumn, null, null, null);  
	                cursor.moveToFirst();  
	  
	                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);  
	                String picturePath = cursor.getString(columnIndex);  
	                cursor.close();
	                
	                
	                fileSer = new FileService(SetpEditActivity.this);
	                
					try {
						mContent = fileSer.readBytes(picturePath);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	                
	                
    	    		//ͼƬ���� ʱ������
    	    		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    	            Date date = new Date(System.currentTimeMillis());
    	            String filename = format.format(date);

    	    		//�洢��DCIM�ļ���
    	    		String path = Environment.getExternalStorageDirectory()+Constant.DOING_MENU_PATH+menuPath; 
					try {
						fileSer.saveToSDCard(path, filename+".jpg", mContent);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}        
	                File outputImage = new File(path+"/"+filename+".jpg");
    	    		//��File����ת��ΪUri�������������
    	    		imageUri = Uri.fromFile(outputImage);   
	        	  cropImageUri(imageUri, 360, 270, SetpEditActivity.SHOW_BITMAP);
	        	  break;
	          case SetpEditActivity.FROM_CAMER:
	        	  cropImageUri(imageUri, 360, 270, SetpEditActivity.SHOW_BITMAP);
	          case SetpEditActivity.SHOW_BITMAP:
	        	//ͼƬ������Bitmap����
		        	BitMapurl = imageUri.toString();
		        	BitMapurl = BitMapurl.substring(7);
		        	FileInputStream fis = null;
					try {
						fis = new FileInputStream(BitMapurl);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//ѹ�������ڽ�ʡBITMAP�ڴ�ռ�--���BUG�Ĺؼ�����   
					 BitmapFactory.Options opts = new BitmapFactory.Options();  
					opts.inSampleSize = 2;    //�����ֵѹ���ı�����2��������������ֵԽС��ѹ����ԽС��ͼƬԽ����   		  
					//����ԭͼ����֮���bitmap����   
					myBitmap = BitmapFactory.decodeStream(fis, null, opts);				
		            stepPhoto.setImageBitmap(myBitmap); 
		            break;
	          }
//	        if(requestCode==0){  
//	              
//	              
//	            //��ʽ��   
//	            try {  
//	                Uri selectedImage = data.getData();  
//	                String[] filePathColumn = { MediaStore.Images.Media.DATA };  
//	  
//	                Cursor cursor = getContentResolver().query(selectedImage,  
//	                        filePathColumn, null, null, null);  
//	                cursor.moveToFirst();  
//	  
//	                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);  
//	                String picturePath = cursor.getString(columnIndex);  
//	                cursor.close();  
//	                FileInputStream fis = new FileInputStream(picturePath);
//	                myBitmap = BitmapFactory.decodeStream(fis);
//	                stepPhoto.setImageBitmap(myBitmap); 
//	                BitMapurl = picturePath;
////	                stepPhoto.setImageBitmap(BitmapFactory.decodeFile(picturePath)); 
//	            } catch (Exception e) {  
//	                // TODO: handle exception   
//	                e.printStackTrace();  
//	            }  
//	              
//	              
//	        }else if(requestCode==1){  
////	            try {  
////	            	
////	            	
////	                Bundle extras = data.getExtras();  
////	                myBitmap = (Bitmap) extras.get("data");  
////	                ByteArrayOutputStream baos = new ByteArrayOutputStream();       
////	                myBitmap.compress(Bitmap.CompressFormat.JPEG , 100, baos);       
////	                mContent=baos.toByteArray();  
////	                
////	                fileSer = new FileService(SetpEditActivity.this);
////			        
////
////			        String path = Constant.DOING_MENU_PATH;
////			        SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyyMMddHHmmss");     
////			        Date   curDate   =   new   Date(System.currentTimeMillis());//��ȡ��ǰʱ��     
////			        String   str   =   formatter.format(curDate);
////			        path = Environment.getExternalStorageDirectory()+Constant.DOING_MENU_PATH+menuPath;
////
////			        try {
////						fileSer.saveToSDCard(path,str+".jpg", mContent);
////					} catch (IOException e) {
////						// TODO Auto-generated catch block
////						e.printStackTrace();
////					}	
////	            } catch (Exception e) {  
////	                e.printStackTrace();  
////	                // TODO: handle exception   
////	            }  
//				//ͼƬ������Bitmap����
//	        	BitMapurl = imageUri.toString();
//	        	BitMapurl = BitMapurl.substring(7);
//	        	FileInputStream fis = null;
//				try {
//					fis = new FileInputStream(BitMapurl);
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				//ѹ�������ڽ�ʡBITMAP�ڴ�ռ�--���BUG�Ĺؼ�����   
//				 BitmapFactory.Options opts = new BitmapFactory.Options();  
//				opts.inSampleSize = 2;    //�����ֵѹ���ı�����2��������������ֵԽС��ѹ����ԽС��ͼƬԽ����   
//				  
//				//����ԭͼ����֮���bitmap����   
//				myBitmap = BitmapFactory.decodeStream(fis, null, opts);				
//	            stepPhoto.setImageBitmap(myBitmap);  
//	            
//	        }  
	          
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
	 
	 
	 



private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode){
    Intent intent = new Intent("com.android.camera.action.CROP");
    intent.setDataAndType(uri, "image/*");
    intent.putExtra("crop", "true");
    intent.putExtra("aspectX", 4);
    intent.putExtra("aspectY", 3);
    intent.putExtra("outputX", outputX);
    intent.putExtra("outputY", outputY);
    intent.putExtra("scale", true);
    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
    intent.putExtra("return-data", false);
    intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
    intent.putExtra("noFaceDetection", true); // no face detection
    startActivityForResult(intent, requestCode);
}
	
	
}
