package com.my.cookmaster.view.util;

import android.util.Log;

public class TTLog {
	public static void dvbtLog(String logStr) {
        String lineFormat = "%s--%s--%d";
        StackTraceElement traceElement = Thread.currentThread().getStackTrace()[3];
        String logText = String.format(lineFormat, traceElement.getFileName(),
                traceElement.getMethodName(), traceElement.getLineNumber());
        if(logStr.equals("")|| logStr == null)
        	Log.d("aisino", logText);
        else
        	Log.d("aisino", logText+"--"+logStr);
    }

}
