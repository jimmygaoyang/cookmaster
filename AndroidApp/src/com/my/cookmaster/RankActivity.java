package com.my.cookmaster;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.my.cookmaster.bean.bus_bean.rankFragmentBean;
import com.my.cookmaster.view.rankFragment;
import com.my.cookmaster.view.viewpagerindicator.BaseFragment;

public class RankActivity extends BaseFragment{

	private List<rankFragmentBean> beanList = new ArrayList<rankFragmentBean>();

	

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.rank_activity, null, false);
        if(view!=null)
        {
        	ViewGroup parent=(ViewGroup)view.getParent();
        	if( parent!=null)  
        		parent.removeView(view);
        }
        view = inflater.inflate(R.layout.rank_activity, null, false);
        TextView textView = (TextView) view.findViewById(R.id.text);
        textView.setText(getTitle());
        creatData();
        initViews();
        
        
        return view;
    }
    
    
    private void initViews() {
		// TODO Auto-generated method stub
		
	}


	private void creatData()
    {
    	rankFragmentBean bean1= new rankFragmentBean();
    	bean1.setTagStr("Ӫ��");
    	bean1.setTop1Str("�ƶ�����");
    	bean1.setTop2Str("�Ǵ��Ź�");
    	bean1.setTop3Str("����������");
    	bean1.setTop4Str("��������");
    	bean1.setTop5Str("ľ������");
    	beanList.add(bean1);
    	rankFragmentBean bean2= new rankFragmentBean();
    	bean2.setTagStr("ʱ��");
    	bean2.setTop1Str("�㴻����");
    	bean2.setTop2Str("��̨����");
    	bean2.setTop3Str("�ƶ�����");
    	bean2.setTop4Str("�ƶ�����");
    	bean2.setTop5Str("�ƶ�����");
    	beanList.add(bean2);
    	rankFragmentBean bean3= new rankFragmentBean();
    	bean3.setTagStr("����");
    	bean3.setTop1Str("ˮ����");
    	bean3.setTop2Str("������˿");
    	bean3.setTop3Str("���Ӽ�");
    	bean3.setTop4Str("�ƶ�����");
    	bean3.setTop5Str("�ƶ�����");
    	beanList.add(bean3);
    }
	
	@Override
    public void refreshData()
    {
    
    }
}
