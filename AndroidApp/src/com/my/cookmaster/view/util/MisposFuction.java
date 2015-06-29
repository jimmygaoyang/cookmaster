package com.my.cookmaster.view.util;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.EditText;

public class MisposFuction {

	public static void ShowMessage(String msg, Context activity){
		Builder builder = new Builder(activity);
        builder.setTitle("提示");
        builder.setMessage(msg);
        builder.setPositiveButton("确认"  , new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
	}

//	public static void GetInputMessage(String msg, Context activity,StringBuilder _builder){
//		Builder builder = new Builder(activity);
//		final EditText editText = new EditText(activity);//
//
//        builder.setTitle("请输入"+msg);
//        builder.setView(editText);
////        builder.setMessage(msg);
//        builder.setPositiveButton("确认"  , new OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//            	String aa = editText.getText().toString();
//            	_builder.append(aa);
//                dialog.dismiss();
//            }
//        });
//        builder.create().show();
//	}


}
