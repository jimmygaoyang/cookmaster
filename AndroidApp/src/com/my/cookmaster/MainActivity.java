package com.my.cookmaster;

import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.bluetooth.BluetoothDevice;

import com.my.cookmaster.alogrithm.Alogrithm;
import com.my.cookmaster.bus.BTtranse;
import com.my.cookmaster.bus.HttpTranse;
import com.loopj.android.http.*;

public class MainActivity extends Activity {
	private BTtranse bttrans =new BTtranse();
	private List<BluetoothDevice> devList =null;
//	static AsyncHttpClient client = new AsyncHttpClient();
	Button BleBtn;
	Button NetBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		BleBtn = (Button)findViewById(R.id.ble);
		NetBtn = (Button)findViewById(R.id.net);
		bttrans.open();
		devList = bttrans.getCookDevice();
		
		BleBtn.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bttrans.transeWithDevice(devList.get(0), "1234567".getBytes());
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}});
		
		NetBtn.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				HttpTranse Http = new HttpTranse();
				byte [] sendBuf = "12345678".getBytes();			
				int res = Http.TranseWithServer(MainActivity.this,"http://192.168.28.86/index.php/api/process", sendBuf);
				if(res == 0)
				{
					String text = new String(Http.getRespData());
					Log.d("cook",text);
				}
				
				
			}});
//		loop_thread.start();

//			client.get("http://192.168.28.86/index.php/api/process", new AsyncHttpResponseHandler() {
//				@Override
//			    public void onStart() {
//			        // called before request is started
//			    }
//
//			    @Override
//			    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
//			        // called when response HTTP status is "200 OK"
//			    	String text = new String(response);
//			    	Log.d("cook",text);
//			    }
//
//			    @Override
//			    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
//			        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
//			    	Log.d("cook",errorResponse.toString());
//			    }
//
//			    @Override
//			    public void onRetry(int retryNo) {
//			        // called when request is retried
//				}
//			});


		
	}
	
	Thread loop_thread = new Thread()
	{
		public void run()
		{
			
			while(true)
			{
//				bttrans.transeWithDevice(devList.get(0), "1234567".getBytes());
//				try {
//					Thread.sleep(2000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}


			}
		
		}
	};
}
