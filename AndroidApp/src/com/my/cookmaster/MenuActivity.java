package com.my.cookmaster;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.http.Header;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;












import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.my.cookmaster.R;
import com.my.cookmaster.bean.bus_bean.AdMenu;
import com.my.cookmaster.bean.bus_bean.FlightOrder;
import com.my.cookmaster.bean.bus_bean.ImageOder;
import com.my.cookmaster.bean.bus_bean.TicketOrder;
import com.my.cookmaster.bean.pro_bean.AdMenuBean;
import com.my.cookmaster.bean.pro_bean.AdMenuListBean;
import com.my.cookmaster.bean.pro_bean.GetAdvertiseMenu;
import com.my.cookmaster.bean.pro_bean.GetMenuBean;
import com.my.cookmaster.bean.pro_bean.menuBean;
import com.my.cookmaster.bus.HttpTranse;
import com.my.cookmaster.view.bannerview.CircleFlowIndicator;
import com.my.cookmaster.view.bannerview.ImagePagerAdapter;
import com.my.cookmaster.view.bannerview.ViewFlow;
import com.my.cookmaster.view.listview.viewprovider.IItemBean;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.MiltilViewListAdapter;
import com.my.cookmaster.view.listview.viewprovider.MyScrollListener;
import com.my.cookmaster.view.listview.viewprovider.impl.AdMenuProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.FlightOrderViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.ImageViewProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.SticketOrderViewProvider;
import com.my.cookmaster.view.viewpagerindicator.BaseFragment;

public class MenuActivity extends BaseFragment{
	private ListView mListView;
	private List<IItemBean> mList = new ArrayList<IItemBean>();
	private EditText menuName;
	private Button menuFind;
	private ViewFlow mViewFlow;
	private CircleFlowIndicator mFlowIndicator;
	private ArrayList<String> imageUrlList = new ArrayList<String>();
	ArrayList<String> linkUrlArray= new ArrayList<String>();
	ArrayList<String> titleList= new ArrayList<String>();
	//推荐菜谱的bean
	AdMenuListBean mBean =new AdMenuListBean();
	MiltilViewListAdapter adpater;
	
	private Boolean getFlag = false;//已经获取到后台的信息列表
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View view = inflater.inflate(R.layout.menu_activity, null, false);
	        TextView textView = (TextView) view.findViewById(R.id.text);
	        mListView = (ListView)view.findViewById(R.id.menu_listview);
	        menuName = (EditText)view.findViewById(R.id.menu_name_Edt);
	        menuFind = (Button)view.findViewById(R.id.menu_find_Btn);
	        textView.setText(getTitle());
			mViewFlow = (ViewFlow) view.findViewById(R.id.viewflow);
			mFlowIndicator = (CircleFlowIndicator) view.findViewById(R.id.viewflowindic);
			imageUrlList.clear();
			imageUrlList
			.add("http://b.hiphotos.baidu.com/image/pic/item/d01373f082025aaf95bdf7e4f8edab64034f1a15.jpg");
			imageUrlList
					.add("http://g.hiphotos.baidu.com/image/pic/item/6159252dd42a2834da6660c459b5c9ea14cebf39.jpg");
			imageUrlList
					.add("http://d.hiphotos.baidu.com/image/pic/item/adaf2edda3cc7cd976427f6c3901213fb80e911c.jpg");
			imageUrlList
					.add("http://g.hiphotos.baidu.com/image/pic/item/b3119313b07eca80131de3e6932397dda1448393.jpg");
		
			linkUrlArray
					.add("http://blog.csdn.net/finddreams/article/details/44301359");
			linkUrlArray
					.add("http://blog.csdn.net/finddreams/article/details/43486527");
			linkUrlArray
					.add("http://blog.csdn.net/finddreams/article/details/44648121");
			linkUrlArray
					.add("http://blog.csdn.net/finddreams/article/details/44619589");
			titleList.add("常见Android进阶笔试题");
			titleList.add("GridView之仿支付宝钱包首页");
			titleList.add("仿手机QQ网络状态条的显示与消失 ");
			titleList.add("Android循环滚动广告条的完美实现 ");
			initBanner(imageUrlList);
//	        createData();
			//不同之处在于多了一个provider集合，提供所有期望显示类型的provider class
			//getView的实现在provider中实现，和在adapter中用法一样
			List<Class<? extends IViewProvider>> providers = new ArrayList<Class<? extends IViewProvider>>();
			providers.add(AdMenuProvider.class);
			
			adpater = new MiltilViewListAdapter(this.getActivity().getApplication(), mList, providers);
			mListView.setAdapter(adpater);
			MyScrollListener scrollListener = new MyScrollListener(adpater);
			mListView.setOnScrollListener(scrollListener);
			
			if(getFlag == false)
			{
				Log.d("cook","向后台要数据");
				//获取推荐菜谱
				GetAdvertiseMenu sendBean = new GetAdvertiseMenu();
				String sendDat = JSON.toJSONString(sendBean);
				HttpTranse Http = new HttpTranse();			
				Http.TranseWithServer(this.getActivity().getApplicationContext(),"http://192.168.28.86/index.php/api/process", sendDat,responseHandler);	
			
			}

	        return view;
	    }
	 
	 
	 private void loadAdMenuData(AdMenuListBean AdMenuList){
		 	mList.clear();
		 	
	        for (int i=0; i < AdMenuList.getAd_menu().size(); i++) {
	            AdMenu adMenuData = new AdMenu();
	            AdMenuBean bean= AdMenuList.getAd_menu().get(i);
	            adMenuData.setCover(bean.getCover());
	            adMenuData.setCoverTitle(bean.getCoverTitle());
	            adMenuData.setFavoriteCount(bean.getFavoriteCount());
	            adMenuData.setRecipeId(bean.getRecipeId()); 
	            mList.add(adMenuData);
	        }
//			ImageOder img = new ImageOder();
//			img.ImgURL = "http://recipe1.hoto.cn/pic/recipe/l/5b/b3/832347_53f806.jpg";
//			mList.add(img);
//			ImageOder img1 = new ImageOder();
//			img1.ImgURL = "http://avatar1.hoto.cn/f1/65/1992177_185.jpg?v=8";
//			mList.add(img1);
//			ImageOder img2 = new ImageOder();
//			img2.ImgURL = "http://recipe1.hoto.cn/pic/step/g_800/ff/4c/3362047.jpg";
//			mList.add(img2);
//			ImageOder img3 = new ImageOder();
//			img3.ImgURL = "http://recipe0.hoto.cn/pic/step/g_800/00/4d/3362048.jpg";
//			mList.add(img3);
//			ImageOder img4 = new ImageOder();
//			img4.ImgURL = "http://recipe1.hoto.cn/pic/step/g_800/01/4d/3362049.jpg";
//			mList.add(img4);
//			ImageOder img5 = new ImageOder();
//			img5.ImgURL = "http://recipe0.hoto.cn/pic/step/g_800/02/4d/3362050.jpg";
//			mList.add(img5);
//			ImageOder img6 = new ImageOder();
//			img6.ImgURL = "http://recipe1.hoto.cn/pic/step/g_800/03/4d/3362051.jpg";
//			mList.add(img6);
//			ImageOder img7 = new ImageOder();
//			img7.ImgURL = "http://recipe0.hoto.cn/pic/step/g_800/04/4d/3362052.jpg";
//			mList.add(img7);
			
			
//			for(int i=0; i < 15; i++){
//				int r = random.nextInt();
//				if(r%2 == 0 ){
//					FlightOrder f = new FlightOrder();
//					f.airline = "东方航空-BS" + i;
//					f.from = "北京";
//					f.to = "杭州";
//					mList.add(f);
//				}else{
//					TicketOrder t = new TicketOrder();
//					t.expireDate = "有效时间：2014-1-1至2014-5-5";
//					t.type = "类型：电子票";
//					t.title = "大裤衩" + i;
//					mList.add(t);
//				}
//			}
		}
	 

		private void initBanner(ArrayList<String> imageUrlList) {
			
			mViewFlow.setAdapter(new ImagePagerAdapter(this.getActivity().getApplication(), imageUrlList,
					linkUrlArray, titleList).setInfiniteLoop(true));
			mViewFlow.setmSideBuffer(imageUrlList.size()); // 实际图片张数，
															// 我的ImageAdapter实际图片张数为3

			mViewFlow.setFlowIndicator(mFlowIndicator);
			mViewFlow.setTimeSpan(4500);
			mViewFlow.setSelection(imageUrlList.size() * 1000); // 设置初始位置
			mViewFlow.startAutoFlowTimer(); // 启动自动播放
		}
	
		
		AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler(){

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				Log.d("cook","与后台通信错");
				
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				String text = new String(arg2);			
				mBean =  JSON.parseObject(text, AdMenuListBean.class);
		    	Log.d("cook",text);
                Message message = new Message();
                message.what = 1;
                doActionHandler.sendMessage(message);	
			}

		};
		//消息处理
		private Handler doActionHandler = new Handler(){
			@Override
	        public void handleMessage(Message msg) {        	
	            super.handleMessage(msg);
	            int msgId = msg.what;
	            switch (msgId) {
	                case 1:
	                	loadAdMenuData(mBean);
	                	adpater.notifyDataSetChanged();
	                	getFlag =true;
	                    break;
	                case 0:
	                
	                	break;
	                default:
	                    break;
	            }
	        }
		};
		
		
		
		
		
}
