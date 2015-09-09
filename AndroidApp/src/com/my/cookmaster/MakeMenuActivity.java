package com.my.cookmaster;

import java.util.ArrayList;
import java.util.List;

import com.my.cookmaster.CookActivity.buttonClick;
import com.my.cookmaster.bean.bus_bean.MkMenuMainStuff;
import com.my.cookmaster.bean.bus_bean.MkMenuMenuTitle;
import com.my.cookmaster.bean.bus_bean.MkMenuSubStuff;
import com.my.cookmaster.bean.bus_bean.StuffAdd;
import com.my.cookmaster.bean.bus_bean.subStuffTitle;
import com.my.cookmaster.bean.pro_bean.MaterialBean;
import com.my.cookmaster.view.listview.viewprovider.Callback;
import com.my.cookmaster.view.listview.viewprovider.IItemBean;
import com.my.cookmaster.view.listview.viewprovider.IViewProvider;
import com.my.cookmaster.view.listview.viewprovider.MiltilViewListAdapter;
import com.my.cookmaster.view.listview.viewprovider.MyScrollListener;
import com.my.cookmaster.view.listview.viewprovider.impl.MkMenuAddStuffProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.MkMenuMainStuffProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.MkMenuMenuTitleProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.SubStuffEditProvider;
import com.my.cookmaster.view.listview.viewprovider.impl.subStuffTitleProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MakeMenuActivity extends Activity {
	private TextView CurTile;
	private Button backBtn;
	private TextView makeMenu;
	private ListView makeMenuList;
	public boolean hasNextPage =false;//��ʶ�Ѿ�����һ��activity��
	
	List<Class<? extends IViewProvider>> providers;
	MiltilViewListAdapter adpater;
	public static List<IItemBean> mList = null;
	
	public int editPosition;
	public static MakeMenuActivity instance = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.make_menu_activity);
		CurTile = (TextView)findViewById(R.id.text_title);
		backBtn = (Button)findViewById(R.id.button_backward);
		makeMenu = (TextView)findViewById(R.id.button_forward);
		makeMenuList = (ListView)findViewById(R.id.menuView);
		

		
		providers = new ArrayList<Class<? extends IViewProvider>>();
		providers.add(MkMenuMenuTitleProvider.class);
		providers.add(MkMenuMainStuffProvider.class);
		providers.add(subStuffTitleProvider.class);
		providers.add(SubStuffEditProvider.class);
		providers.add(MkMenuAddStuffProvider.class);
		
		CurTile.setText("��������");
		makeMenu.setText("��һ��");
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
//				Intent intent = new Intent(MakeMenuActivity.this, MyMenuActivity.class);
//				MakeMenuActivity.this.startActivity(intent);
			}
		});
		makeMenu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				
				IItemBean itemBean = mList.get(0);
				if(itemBean instanceof MkMenuMenuTitle)
				{
					MkMenuMenuTitle bean = (MkMenuMenuTitle)mList.get(0);
				Toast.makeText(MakeMenuActivity.this,"����Ϊ" + bean.getMenuTitle(),Toast.LENGTH_SHORT).show();
				}
				}
				
		});
		if(mList == null)//��ֹ��д
			mList = new ArrayList<IItemBean>();

		
		
//        Intent intent=getIntent();//getIntent������Ŀ�а�����ԭʼintent����������������������intent��ֵ��һ��Intent���͵ı���intent  
//        Bundle bundle=intent.getExtras();//.getExtras()�õ�intent�������Ķ�������   
//        MaterialBean bean  = (MaterialBean)bundle.get("material");
//        if (bean!= null)
//        {
//        	MkMenuMainStuff data = (MkMenuMainStuff)mList.get(editPosition);
//        	data.setMaterial(bean);
//        }
		if(mList.size() == 0)//��ֹ��д
			loadMenuData();
		
		instance = this;
	};
	
	@Override
	public void onResume() {
		super.onResume();
		adpater = new MiltilViewListAdapter(this.getApplication(), mList, providers);
		adpater.setmCallback(new buttonClick());
		makeMenuList.setAdapter(adpater);
		MyScrollListener scrollListener = new MyScrollListener(adpater);
		makeMenuList.setOnScrollListener(scrollListener);
		adpater.notifyDataSetChanged();		
		

	}
	private void refreshData() {
		// TODO Auto-generated method stub
        Intent intent=getIntent();//getIntent������Ŀ�а�����ԭʼintent����������������������intent��ֵ��һ��Intent���͵ı���intent  
        Bundle bundle=intent.getExtras();//.getExtras()�õ�intent�������Ķ�������   
        MaterialBean bean  = (MaterialBean)bundle.get("material");
        if (bean!= null)
        {
        	MkMenuMainStuff data = (MkMenuMainStuff)mList.get(editPosition);
        	data.setMaterial(bean);
        	adpater.notifyDataSetChanged();
        }
        
        
	}

	private void loadMenuData()
	{
		MkMenuMenuTitle titleBean = new MkMenuMenuTitle();
		mList.add(titleBean);
		MkMenuMainStuff mainStuf = new MkMenuMainStuff();
		mList.add(mainStuf);
		subStuffTitle subStuffTitleBean = new subStuffTitle();
		mList.add(subStuffTitleBean);//��Ӹ��ϱ���
		MkMenuSubStuff subStuff = new MkMenuSubStuff();
		mList.add(subStuff);
		StuffAdd stuffAdd = new StuffAdd();
		mList.add(stuffAdd);
	};
	
	class buttonClick implements Callback
	{
		@Override
		public void click(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(MakeMenuActivity.this, StuffSelectActivity.class);
			editPosition = (Integer)v.getTag();
			intent.putExtra("pos",editPosition);
			MakeMenuActivity.this.startActivity(intent);
			hasNextPage = true;


			//Toast.makeText(CookActivity.this.getActivity(),"listview���ڲ��İ�ť������ˣ���λ����-->" + (Integer) v.getTag(1,),Toast.LENGTH_SHORT).show();
		}
			
	};

}
