package com.my.cookmaster.view.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
public abstract class TTProgress
{
	private static ProgressDialog mProgressDialog = null;
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
			indeterminateInternal(context, message, progress, true);
		}
		catch (Exception e)
		{

		}
	}
//	public static void showDlgEx(Context context,String message,final ITTProgress progress, boolean cancelable)
//	{
//		try
//		{
//			indeterminateInternal(context, message, progress, cancelable);
//		}
//		catch (Exception e)
//		{
//
//		}
//	}

	public static void showDlg(Context context,
									String message,
									final ITTProgress progress,
									boolean cancelable)
	{

		try
		{

			indeterminateInternal(context, message, progress, cancelable);
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
	private static ProgressDialog createProgressDialog(Context context, String message)
	{
		mProgressDialog = new ProgressDialog(context);
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

	private static void indeterminateInternal(Context context, String message, final ITTProgress progress, boolean cancelable)
	{
		mTTProgress = progress;
		final ProgressDialog dialog = createProgressDialog(context, message);
		dialog.setCancelable(cancelable);
		createRunnableAndDiss();
		dialog.setOnDismissListener(mOnDismissListener);
		dialog.show();
		new Thread() {

			@Override
			public void run()
			{
				mRunnable.run();

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
}
