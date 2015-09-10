package com.my.cookmaster.bean.pro_bean;

public class UploadStep {
	private String Intro;
	private String stepImgRes;
	private int stepIndex;
	private boolean uploadSuccess;
	public String getIntro() {
		return Intro;
	}
	public void setIntro(String intro) {
		Intro = intro;
	}
	public String getStepImgRes() {
		return stepImgRes;
	}
	public void setStepImgRes(String stepImgRes) {
		this.stepImgRes = stepImgRes;
	}
	public int getStepIndex() {
		return stepIndex;
	}
	public void setStepIndex(int stepIndex) {
		this.stepIndex = stepIndex;
	}
	public boolean isUploadSuccess() {
		return uploadSuccess;
	}
	public void setUploadSuccess(boolean uploadSuccess) {
		this.uploadSuccess = uploadSuccess;
	}
}
