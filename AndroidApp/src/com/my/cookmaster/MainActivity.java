package com.my.cookmaster;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.Header;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.bluetooth.BluetoothDevice;

import com.my.cookmaster.alogrithm.Alogrithm;
import com.my.cookmaster.bus.BTtranse;
import com.my.cookmaster.bus.HttpTranse;
import com.my.cookmaster.view.listview.viewprovider.*;
import com.my.cookmaster.view.listview.viewprovider.impl.*;
import com.my.cookmaster.bean.bus_bean.*;
import com.loopj.android.http.*;

public class MainActivity extends Activity {
	private BTtranse bttrans =new BTtranse();
	private List<BluetoothDevice> devList =null;
//	static AsyncHttpClient client = new AsyncHttpClient();
//	Button BleBtn;
//	Button NetBtn;
	private ListView mListView;
	private List<IItemBean> mList = new ArrayList<IItemBean>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		BleBtn = (Button)findViewById(R.id.ble);
//		NetBtn = (Button)findViewById(R.id.net);
		createData();
		mListView = (ListView) findViewById(R.id.my_listview);
		//不同之处在于多了一个provider集合，提供所有期望显示类型的provider class
		//getView的实现在provider中实现，和在adapter中用法一样
		List<Class<? extends IViewProvider>> providers = new ArrayList<Class<? extends IViewProvider>>();
		providers.add(FlightOrderViewProvider.class);
		providers.add(SticketOrderViewProvider.class);
		providers.add(ImageViewProvider.class);
		
		MiltilViewListAdapter adpater = new MiltilViewListAdapter(getApplication(), mList, providers);
		mListView.setAdapter(adpater);
		MyScrollListener scrollListener = new MyScrollListener(adpater);
		mListView.setOnScrollListener(scrollListener);
		bttrans.open();
		devList = bttrans.getCookDevice();
		
//		BleBtn.setOnClickListener(new View.OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				bttrans.transeWithDevice(devList.get(0), "1234567".getBytes());
//				try {
//					Thread.sleep(2000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//			}});
//		
//		NetBtn.setOnClickListener(new View.OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				HttpTranse Http = new HttpTranse();
//				byte [] sendBuf = "12345678".getBytes();			
//				int res = Http.TranseWithServer(MainActivity.this,"http://192.168.28.86/index.php/api/process", sendBuf);
//				if(res == 0)
//				{
//					String text = new String(Http.getRespData());
//					Log.d("cook",text);
//				}
//				
//				
//			}});
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
	
	private void createData(){
		Random random = new Random();
		
		FlightOrder f = new FlightOrder();
		f.airline = "东方航空-BS" + 1;
		f.from = "北京";
		f.to = "杭州";
		mList.add(f);
		TicketOrder t = new TicketOrder();
		t.expireDate = "有效时间：2014-1-1至2014-5-5";
		t.type = "类型：电子票";
		t.title = "大裤衩" + 2;
		mList.add(t);
		ImageOder img = new ImageOder();
		img.ImgURL = "http://recipe1.hoto.cn/pic/recipe/l/5b/b3/832347_53f806.jpg";
		mList.add(img);
		ImageOder img1 = new ImageOder();
		img1.ImgURL = "http://avatar1.hoto.cn/f1/65/1992177_185.jpg?v=8";
		mList.add(img1);
		ImageOder img2 = new ImageOder();
		img2.ImgURL = "http://recipe1.hoto.cn/pic/step/g_800/ff/4c/3362047.jpg";
		mList.add(img2);
		ImageOder img3 = new ImageOder();
		img3.ImgURL = "http://recipe0.hoto.cn/pic/step/g_800/00/4d/3362048.jpg";
		mList.add(img3);
		ImageOder img4 = new ImageOder();
		img4.ImgURL = "http://recipe1.hoto.cn/pic/step/g_800/01/4d/3362049.jpg";
		mList.add(img4);
		ImageOder img5 = new ImageOder();
		img5.ImgURL = "http://recipe0.hoto.cn/pic/step/g_800/02/4d/3362050.jpg";
		mList.add(img5);
		ImageOder img6 = new ImageOder();
		img6.ImgURL = "http://recipe1.hoto.cn/pic/step/g_800/03/4d/3362051.jpg";
		mList.add(img6);
		ImageOder img7 = new ImageOder();
		img7.ImgURL = "http://recipe0.hoto.cn/pic/step/g_800/04/4d/3362052.jpg";
		mList.add(img7);
		
		
//		for(int i=0; i < 15; i++){
//			int r = random.nextInt();
//			if(r%2 == 0 ){
//				FlightOrder f = new FlightOrder();
//				f.airline = "东方航空-BS" + i;
//				f.from = "北京";
//				f.to = "杭州";
//				mList.add(f);
//			}else{
//				TicketOrder t = new TicketOrder();
//				t.expireDate = "有效时间：2014-1-1至2014-5-5";
//				t.type = "类型：电子票";
//				t.title = "大裤衩" + i;
//				mList.add(t);
//			}
//		}
	}
}
