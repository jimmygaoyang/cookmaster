package com.my.cookmaster.view.util;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class TTProgressMsgHandler extends Handler{
	private ProgressDialog mProgressDialog = null;
	public TTProgressMsgHandler(ProgressDialog progressDialog)
	{
		this.mProgressDialog = progressDialog;
	}
	@Override
	public void handleMessage(Message msg) {
		if (msg.what == 0) {
			if (mProgressDialog == null) {
				return;
			}
			Bundle bundle = msg.getData();
			String msgsString = bundle.getString("MSG");
			mProgressDialog.setMessage(msgsString);
		}
		super.handleMessage(msg);
	}
}
