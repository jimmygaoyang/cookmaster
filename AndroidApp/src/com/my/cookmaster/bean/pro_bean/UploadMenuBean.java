package com.my.cookmaster.bean.pro_bean;

import java.util.ArrayList;
import java.util.List;

public class UploadMenuBean {
	private String Cover;
	private String Title;
	private Long UserId;
	private String Intro;
	private String CoverUrl;
	private MaterialBean MainStuffs;
	private List<MaterialBean> SubStuffs = new ArrayList<MaterialBean>();
	private String UserName;
	private List<UploadStep> Steps = new ArrayList<UploadStep>();
	private boolean checked = false;
	private boolean coverUploadSuccess = false;
	private boolean published = false;
	private Long RecipeId;
	public String getCover() {
		return Cover;
	}
	public void setCover(String cover) {
		Cover = cover;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public Long getUserId() {
		return UserId;
	}
	public void setUserId(Long userId) {
		UserId = userId;
	}
	public String getIntro() {
		return Intro;
	}
	public void setIntro(String intro) {
		Intro = intro;
	}
	public String getCoverUrl() {
		return CoverUrl;
	}
	public void setCoverUrl(String coverUrl) {
		CoverUrl = coverUrl;
	}
	public MaterialBean getMainStuffs() {
		return MainStuffs;
	}
	public void setMainStuffs(MaterialBean mainStuffs) {
		MainStuffs = mainStuffs;
	}
	public List<MaterialBean> getSubStuffs() {
		return SubStuffs;
	}
	public void setSubStuffs(List<MaterialBean> subStuffs) {
		SubStuffs = subStuffs;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public List<UploadStep> getSteps() {
		return Steps;
	}
	public void setSteps(List<UploadStep> steps) {
		Steps = steps;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public boolean isCoverUploadSuccess() {
		return coverUploadSuccess;
	}
	public void setCoverUploadSuccess(boolean coverUploadSuccess) {
		this.coverUploadSuccess = coverUploadSuccess;
	}
	public boolean isPublished() {
		return published;
	}
	public void setPublished(boolean published) {
		this.published = published;
	}
	public Long getRecipeId() {
		return RecipeId;
	}
	public void setRecipeId(Long recipeId) {
		RecipeId = recipeId;
	}
	
	
	
	
	
	
}
