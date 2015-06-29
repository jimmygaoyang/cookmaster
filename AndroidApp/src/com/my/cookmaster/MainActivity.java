package com.my.cookmaster;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.Header;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;

import com.my.cookmaster.alogrithm.Alogrithm;
import com.my.cookmaster.bus.BTtranse;
import com.my.cookmaster.bus.HttpTranse;
import com.my.cookmaster.view.listview.viewprovider.*;
import com.my.cookmaster.view.listview.viewprovider.impl.*;
import com.my.cookmaster.view.viewpagerindicator.BaseFragment;
import com.my.cookmaster.view.viewpagerindicator.IconPagerAdapter;
import com.my.cookmaster.view.viewpagerindicator.IconTabPageIndicator;
import com.my.cookmaster.bean.bus_bean.*;
import com.my.cookmaster.bean.pro_bean.GetMenuBean;
import com.my.cookmaster.bean.pro_bean.menuBean;
import com.loopj.android.http.*;
import com.my.cookmaster.R;
import com.alibaba.fastjson.JSON;

public class MainActivity extends FragmentActivity {
	private BTtranse bttrans =new BTtranse();
	private List<BluetoothDevice> devList =null;
//	static AsyncHttpClient client = new AsyncHttpClient();
//	Button BleBtn;
//	Button NetBtn;
	private ListView mListView;
	private List<IItemBean> mList = new ArrayList<IItemBean>();
	private ViewPager mViewPager;  
    private IconTabPageIndicator mIndicator;  
    private TextView mTitleTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	//super.setTitle("����");
		
//		BleBtn = (Button)findViewById(R.id.ble);
//		NetBtn = (Button)findViewById(R.id.net);
		createData();
//		mListView = (ListView) findViewById(R.id.my_listview);
//		//��֮ͬ�����ڶ���һ��provider���ϣ��ṩ����������ʾ���͵�provider class
//		//getView��ʵ����provider��ʵ�֣�����adapter���÷�һ��
//		List<Class<? extends IViewProvider>> providers = new ArrayList<Class<? extends IViewProvider>>();
//		providers.add(FlightOrderViewProvider.class);
//		providers.add(SticketOrderViewProvider.class);
//		providers.add(ImageViewProvider.class);
//		
//		MiltilViewListAdapter adpater = new MiltilViewListAdapter(getApplication(), mList, providers);
//		mListView.setAdapter(adpater);
//		MyScrollListener scrollListener = new MyScrollListener(adpater);
//		mListView.setOnScrollListener(scrollListener);
//		bttrans.open();
//		devList = bttrans.getCookDevice();
		
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


		initViews();
        CookMasterApp app =  CookMasterApp.getInstance();
        Context con = MainActivity.this;
		SharedPreferences sysPre = con.getSharedPreferences(app.APPNAME,con.MODE_PRIVATE);
		SharedPreferences.Editor editor = sysPre.edit();
 		editor.putBoolean(app.LogInState, false);
 		editor.commit();

		
	}
	
	private void initViews() {
		// TODO Auto-generated method stub
		mViewPager = (ViewPager) findViewById(R.id.view_pager);  
        mIndicator = (IconTabPageIndicator) findViewById(R.id.indicator);  
        List<BaseFragment> fragments = initFragments();  
        FragmentAdapter adapter = new FragmentAdapter(fragments, getSupportFragmentManager());  
        mViewPager.setAdapter(adapter);  
        mIndicator.setViewPager(mViewPager);  
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
		f.airline = "��������-BS" + 1;
		f.from = "����";
		f.to = "����";
		mList.add(f);
		TicketOrder t = new TicketOrder();
		t.expireDate = "��Чʱ�䣺2014-1-1��2014-5-5";
		t.type = "���ͣ�����Ʊ";
		t.title = "�����" + 2;
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
//				f.airline = "��������-BS" + i;
//				f.from = "����";
//				f.to = "����";
//				mList.add(f);
//			}else{
//				TicketOrder t = new TicketOrder();
//				t.expireDate = "��Чʱ�䣺2014-1-1��2014-5-5";
//				t.type = "���ͣ�����Ʊ";
//				t.title = "�����" + i;
//				mList.add(t);
//			}
//		}
	}
	
    private List<BaseFragment> initFragments() {
        List<BaseFragment> fragments = new ArrayList<BaseFragment>();

        MenuActivity MenuFragment = new MenuActivity();
        MenuFragment.setTitle("����");
        MenuFragment.setIconId(R.drawable.tab_menu_selector);
        fragments.add(MenuFragment);

        RankActivity RankFragment = new RankActivity();
        RankFragment.setTitle("���а�");
        RankFragment.setIconId(R.drawable.tab_rank_selector);
        fragments.add(RankFragment);

        BaseFragment contactFragment = new BaseFragment();
        contactFragment.setTitle("����");
        contactFragment.setIconId(R.drawable.tab_cook_selector);
        fragments.add(contactFragment);

        UserActivity UserFragment = new UserActivity();
        UserFragment.setTitle("�ҵ�");
        UserFragment.setIconId(R.drawable.tab_user_selector);
        fragments.add(UserFragment);

        return fragments;
    }
	class FragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
        private List<BaseFragment> mFragments;

        public FragmentAdapter(List<BaseFragment> fragments, FragmentManager fm) {
            super(fm);
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int i) {
            return mFragments.get(i);
        }

        @Override
        public int getIconResId(int index) {
            return mFragments.get(index).getIconId();
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragments.get(position).getTitle();
        }
    }
	

	
	
}
