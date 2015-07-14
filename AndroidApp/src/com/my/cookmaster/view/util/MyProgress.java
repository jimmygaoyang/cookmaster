package com.my.cookmaster.view.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.TextView;
import com.my.cookmaster.R;




public abstract class MyProgress
{
	private static MyProDialog mProgressDialog = null;
	private static TTProgressMsgHandler mMsgHandler = null;
	private static StringBuffer mstringBuffer = new StringBuffer();
	private static Runnable mRunnable = null;
	private static	OnDismissListener mOnDismissListener = null;
	private static ITTProgress mTTProgress = null;
	public static void showDlg(Context context,
									String message,
									final ITTProgress progress)
	{
		try
		{
			indeterminateInternal(false, context, message, progress, true);
		}
		catch (Exception e)
		{

		}
	}
	public static void showDlgEx(Context context,String message,final ITTProgress progress, boolean cancelable)
	{
		try
		{
			indeterminateInternal(false, context, message, progress, cancelable);
		}
		catch (Exception e)
		{

		}
	}

	public static void showDlg(Context context,
									String message,
									final ITTProgress progress,
									boolean cancelable)
	{

		try
		{

			indeterminateInternal(false, context, message, progress, cancelable);
		}
		catch (Exception e)
		{
			TTLog.dvbtLog("e.toString()");
		}
	}

	//bSys：是否显示全局的Dialog
	//这需要还需要在AndroidManifest.xml中增加权限：
	//<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
	public static void showDlg(boolean bSys, Context context,
			String message,
			final ITTProgress progress,
			boolean cancelable)
	{
		try
		{
			indeterminateInternal(bSys, context, message, progress, cancelable);
		}
		catch (Exception e)
		{
			TTLog.dvbtLog("e.toString()");
		}
	}



	/**
	 * 修改提示信息文字
	 * @param
	 * @return
	 * @date 2012-3-12
	 * @author 唐日升
	 */
	public static boolean ChangeProgressMessage(String message)
	{
		try {
			if (mMsgHandler == null) {
				return false;
			}
			Message msg = new Message();
			msg.what = 0;
			Bundle bundle = new Bundle();
			bundle.putString("MSG", message);
			msg.setData(bundle);
			mMsgHandler.sendMessage(msg);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	public static void post(Runnable run)
	{
		if (mMsgHandler != null) {
			mMsgHandler.post(run);
		}
	}
	private static MyProDialog createProgressDialog(Context context, String message)
	{
		mProgressDialog = new MyProDialog(context,R.style.Dialog);
		mProgressDialog.setIndeterminate(false);
		mProgressDialog.setMessage(message);
		mMsgHandler = new TTProgressMsgHandler(mProgressDialog);
		return mProgressDialog;
	}
	private static void createRunnableAndDiss()
	{
		mRunnable = new Runnable() {
			@Override
			public void run() {
				Looper.prepare();
				mstringBuffer.delete(0, mstringBuffer.length());
				mTTProgress.progressRunnable(mstringBuffer);
			}
		};
		//登录验证结束响应
		mOnDismissListener = new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				mTTProgress.progressDismiss(mstringBuffer.toString());
			}
		};
	}

	private static void indeterminateInternal(boolean bSys, Context context, String message, final ITTProgress progress, boolean cancelable)
	{
		mTTProgress = progress;
		Context con = context;
		if(bSys) {
			con = context.getApplicationContext();
		}
		final MyProDialog dialog = createProgressDialog(con, message);

		dialog.setCancelable(cancelable);
		createRunnableAndDiss();
		dialog.setOnDismissListener(mOnDismissListener);
		if(bSys) {
//			dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		}
		dialog.show();
		new Thread() {

			@Override
			public void run()
			{
				long time = System.currentTimeMillis();

				mRunnable.run();

				//设置显示等待提示最少1s左右，防止提示一闪而过的情况
				time = System.currentTimeMillis() - time;
				if (time < 800) {
					SystemClock.sleep(800);
				}

				mMsgHandler.post(new Runnable() {

					public void run()
					{
						try
						{
							dialog.dismiss();
						}
						catch (Exception e)
						{
							TTLog.dvbtLog(e.toString());
						}

					}
				});
			};
		}.start();
	}



	public static class MyProDialog  extends ProgressDialog
	{
		private TextView textView;
		private String message="";
	    public MyProDialog(Context context, int theme) {
	        super(context, theme);
	    }

	    public MyProDialog(Context context) {
	        super(context);
	    }

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setCancelable(false);
	        setContentView(R.layout.dlg_progress);
	        textView = (TextView)findViewById(R.id.dlg_progress_txt);
			textView.setText(message);

			/*WindowManager m = (WindowManager)getContext().getSystemService("window");
			WindowManager.LayoutParams lp = getWindow().getAttributes();
			lp.width = (int) (m.getDefaultDisplay().getWidth()*0.6);
			getWindow().setAttributes(lp);*/
		}

	    public void setMessage(String str) {
			this.message=str;
			if(textView!=null)
				textView.setText(message);
	    }

		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_HOME) {
			return true;
			}else{
				return super.onKeyDown(keyCode, event);
			}

		}

//		@Override
//		public void onAttachedToWindow() {
//		this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
//		super.onAttachedToWindow();
//		}
	}

}



