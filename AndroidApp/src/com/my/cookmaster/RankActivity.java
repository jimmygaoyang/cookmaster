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
    	bean1.setTagStr("”™—¯");
    	bean1.setTop1Str("ª∆∂π÷Ì ÷");
    	bean1.setTop2Str("Ã«¥◊≈≈π«");
    	bean1.setTop3Str("Œ˜∫Ï ¡º¶µ∞");
    	bean1.setTop4Str("«Â’Ùˆ‘”„");
    	bean1.setTop5Str("ƒæ∂˙º¶µ∞");
    	beanList.add(bean1);
    	rankFragmentBean bean2= new rankFragmentBean();
    	bean2.setTagStr(" ±¡Ó");
    	bean2.setTop1Str("œ„¥ªº¶µ∞");
    	bean2.setTop2Str("À‚Ã®≥¥»‚");
    	bean2.setTop3Str("ª∆∂π÷Ì ÷");
    	bean2.setTop4Str("ª∆∂π÷Ì ÷");
    	bean2.setTop5Str("ª∆∂π÷Ì ÷");
    	beanList.add(bean2);
    	rankFragmentBean bean3= new rankFragmentBean();
    	bean3.setTagStr("œ„¿±");
    	bean3.setTop1Str("ÀÆ÷Û”„");
    	bean3.setTop2Str("œ„¿±»‚Àø");
    	bean3.setTop3Str("¿±◊”º¶");
    	bean3.setTop4Str("ª∆∂π÷Ì ÷");
    	bean3.setTop5Str("ª∆∂π÷Ì ÷");
    	beanList.add(bean3);
    }
	
	@Override
    public void refreshData()
    {
    
    }
}
