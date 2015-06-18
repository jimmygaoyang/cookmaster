package com.my.cookmaster.view;

import com.my.cookmaster.R;
import com.my.cookmaster.bean.bus_bean.rankFragmentBean;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class rankFragment extends Fragment {
	private TextView tag;
	private TextView top1;
	private TextView top2;
	private TextView top3;
	private TextView top4;
	private TextView top5;
	rankFragmentBean rankbean = new rankFragmentBean();
	

	public void setBean(rankFragmentBean bean)
	{
		rankbean = bean;
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rank_view, null, false);
        
        tag = (TextView) view.findViewById(R.id.Tag);
        tag.setText(rankbean.getTagStr());
        top1 = (TextView) view.findViewById(R.id.top1);
        top1.setText("1"+rankbean.getTop1Str());
        top2 = (TextView) view.findViewById(R.id.top2);
        top2.setText("2"+rankbean.getTop2Str());
        top3 = (TextView) view.findViewById(R.id.top3);
        top3.setText("3"+rankbean.getTop3Str());
        top4 = (TextView) view.findViewById(R.id.top4);
        top4.setText("4"+rankbean.getTop4Str());
        top5 = (TextView) view.findViewById(R.id.top5);
        top5.setText("5"+rankbean.getTop5Str());
 
        return view;
    }
	
	
}
