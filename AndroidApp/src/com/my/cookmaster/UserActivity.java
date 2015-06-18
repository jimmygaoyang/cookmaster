package com.my.cookmaster;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.my.cookmaster.view.viewpagerindicator.BaseFragment;

public class UserActivity extends BaseFragment{

	private RelativeLayout setBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_activity, null, false);
        TextView textView = (TextView) view.findViewById(R.id.text);
        setBar = (RelativeLayout)view.findViewById(R.id.mysetBox);
        setBar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("cook","µ„ª˜¡À…Ë÷√");
			}
		});
        textView.setText(getTitle());
        return view;
    }
}