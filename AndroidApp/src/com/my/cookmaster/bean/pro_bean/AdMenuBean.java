package com.my.cookmaster.bean.pro_bean;

public class AdMenuBean {

	private Long RecipeId;
	private String Cover;
	private String CoverTitle;
	private String UserName;
	private Long UserId;
	private Long FavoriteCount;
	public Long getRecipeId() {
		return RecipeId;
	}
	public void setRecipeId(Long recipeId) {
		RecipeId = recipeId;
	}
	public String getCover() {
		return Cover;
	}
	public void setCover(String cover) {
		Cover = cover;
	}
	public String getCoverTitle() {
		return CoverTitle;
	}
	public void setCoverTitle(String coverTitle) {
		CoverTitle = coverTitle;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public Long getUserId() {
		return UserId;
	}
	public void setUserId(Long userId) {
		UserId = userId;
	}
	public Long getFavoriteCount() {
		return FavoriteCount;
	}
	public void setFavoriteCount(Long favoriteCount) {
		FavoriteCount = favoriteCount;
	}
	
}
