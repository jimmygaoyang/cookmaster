package com.my.cookmaster;

import java.util.ArrayList;
import java.util.List;

import com.my.cookmaster.bean.bus_bean.Box;
import com.my.cookmaster.bean.bus_bean.BoxAdd;
import com.my.cookmaster.view.listview.viewprovider.IItemBean;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.MiltilViewListAdapter;
import com.my.cookmaster.view.listview.viewprovider.MyScrollListener;
import com.my.cookmaster.view.listview.viewprovider.impl.BoxAddProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.BoxProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.menuCoverProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.menuIntroProider;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MyToolActivity extends Activity {
	private TextView CurTile;
	private Button backBtn;
	private TextView makeMenu;
	private ListView toolList;
	
	MiltilViewListAdapter adpater;
	private List<IItemBean> mList = new ArrayList<IItemBean>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_tool_activity);
		CurTile = (TextView)findViewById(R.id.text_title);
		backBtn = (Button)findViewById(R.id.button_backward);
		makeMenu = (TextView)findViewById(R.id.button_forward);
		toolList = (ListView)findViewById(R.id.toolview);
		
		List<Class<? extends IViewProvider>> providers = new ArrayList<Class<? extends IViewProvider>>();
		providers.add(BoxProvider.class);
		providers.add(BoxAddProvider.class);
		
		
		CurTile.setText("�ҵĳ���");
		initView();
		
		adpater = new MiltilViewListAdapter(this.getApplication(), mList, providers);
		toolList.setAdapter(adpater);
		MyScrollListener scrollListener = new MyScrollListener(adpater);
		toolList.setOnScrollListener(scrollListener);
		
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}
	
	private void initView()
	{
		mList.clear();
		Box boxBean = new Box();
		boxBean.setFlavorName("���ϣ���");
		boxBean.setFlavorBrand("Ʒ�ƣ�����");
		boxBean.setBoxMac("������ţ�0000000001");
		mList.add(boxBean);
		Box boxBean1 = new Box();
		boxBean1.setFlavorName("���ϣ���");
		boxBean1.setFlavorBrand("Ʒ�ƣ�����");
		boxBean1.setBoxMac("������ţ�0000000002");
		mList.add(boxBean1);
		Box boxBean2 = new Box();
		boxBean2.setFlavorName("���ϣ���");
		boxBean2.setFlavorBrand("Ʒ�ƣ�������");
		boxBean2.setBoxMac("������ţ�0000000003");
		mList.add(boxBean2);
		BoxAdd boxAddBean = new BoxAdd();
		mList.add(boxAddBean);
		
		
		
		
		
	}
}
