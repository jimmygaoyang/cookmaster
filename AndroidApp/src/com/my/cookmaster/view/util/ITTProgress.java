package com.my.cookmaster.view.util;

public abstract interface ITTProgress {
	public void progressRunnable(StringBuffer err);
	public void progressDismiss(String err);
}
