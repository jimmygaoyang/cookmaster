package com.my.cookmaster.bus;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.my.cookmaster.bean.pro_bean.menuBean;

public class HttpTranse {
	
	//静态的client
	static AsyncHttpClient client = new AsyncHttpClient();
	//接收数据缓冲区
	private byte[] recbuf = new byte[1024*512*1];
	//接收数据长度
	private int recLen=0;
	//通信响应状态 -2
	private int RespState = -2;
	//返回响应handler
//	AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler(){
//		@Override
//	    public void onStart() {
//	        // called before request is started
//			RespState = -1;
//	    }
//
//	    @Override
//	    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
//	        // called when response HTTP status is "200 OK"
//	    	String text = new String(response);
//	    	System.arraycopy(response, 0, recbuf, 0, response.length);
//	    	recLen = response.length;
//	    	Log.d("cook",text);
//			menuBean mBean =new menuBean();
//			mBean =  JSON.parseObject(text, menuBean.class);
//	    	RespState = 0;
//	    	Log.d("cook",text);
//	    }
//
//	    @Override
//	    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
//	        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
//	    	Log.d("cook",errorResponse.toString());
//	    	RespState = -1;
//	    }
//
//	    @Override
//	    public void onRetry(int retryNo) {
//	        // called when request is retried
//	    	RespState = -1;
//		}
//	};
   /** 与后台交互一次
    * 
    * @param sendBuf 发送的数据组 */
	public int TranseWithServer(Context context,String url,String sendBuf,AsyncHttpResponseHandler responseHandler){
         StringEntity stringEntity = null;
         try {
        	 stringEntity = new StringEntity(sendBuf);
             } catch (UnsupportedEncodingException e)
             { 
            	 e.printStackTrace();
             } 
		client.post(context, url, stringEntity, "application/json", responseHandler);		
		return 0;
		
	};
	

//	public byte[] getRespData()
//	{
//		byte[] RespData = new byte[recLen];
//		System.arraycopy(recbuf, 0, RespData, 0, recLen);
//		return RespData;
//	}
	
	}

