package com.my.cookmaster.bean.pro_bean;

import java.util.*;


public class menuBean {

   private Long RecipeId;
   private String Cover;
   private String Title;
   private String Intro;
   private String CookTime;
   private String ReadyTime;
   private String Tips;
   private String UserName;
   private Long UserId;
   private Long FavoriteCount;
   private Long PhotoCount;
   private String ReviewTime;
   private String Avatar;
   private Long ViewCount;
   private Long Type;
   private String UserCount;
   private List<Tag> Tags = new ArrayList<Tag>();
   private Long LikeCount;
   private Long IsLike;
   private String[] PhotoList;
   private List<step> Steps = new ArrayList<step>();
   private List<Stuff> Stuff = new ArrayList<Stuff>();
   private List<Stuff> MainStuff = new ArrayList<Stuff>();
   private List<Stuff> OtherStuff = new ArrayList<Stuff>();;
   private int ad_flag;
   private ad_data ad_data;
   private String CommentCount;
   private String[] CommentList;
   private int ProductConunt;
   private int[] Product;
   private int VIP;
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
public String getTitle() {
	return Title;
}
public void setTitle(String title) {
	Title = title;
}
public String getIntro() {
	return Intro;
}
public void setIntro(String intro) {
	Intro = intro;
}
public String getCookTime() {
	return CookTime;
}
public void setCookTime(String cookTime) {
	CookTime = cookTime;
}
public String getReadyTime() {
	return ReadyTime;
}
public void setReadyTime(String readyTime) {
	ReadyTime = readyTime;
}
public String getTips() {
	return Tips;
}
public void setTips(String tips) {
	Tips = tips;
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
public Long getPhotoCount() {
	return PhotoCount;
}
public void setPhotoCount(Long photoCount) {
	PhotoCount = photoCount;
}
public Long getFavoriteCount() {
	return FavoriteCount;
}
public void setFavoriteCount(Long favoriteCount) {
	FavoriteCount = favoriteCount;
}
public String getReviewTime() {
	return ReviewTime;
}
public void setReviewTime(String reviewTime) {
	ReviewTime = reviewTime;
}
public String getAvatar() {
	return Avatar;
}
public void setAvatar(String avatar) {
	Avatar = avatar;
}
public Long getViewCount() {
	return ViewCount;
}
public void setViewCount(Long viewCount) {
	ViewCount = viewCount;
}
public Long getType() {
	return Type;
}
public void setType(Long type) {
	Type = type;
}
public String getUserCount() {
	return UserCount;
}
public void setUserCount(String userCount) {
	UserCount = userCount;
}
public List<Tag> getTags() {
	return Tags;
}
public void setTags(List<Tag> tags) {
	Tags = tags;
}
public Long getLikeCount() {
	return LikeCount;
}
public void setLikeCount(Long likeCount) {
	LikeCount = likeCount;
}
public Long getIsLike() {
	return IsLike;
}
public void setIsLike(Long isLike) {
	IsLike = isLike;
}
public String[] getPhotoList() {
	return PhotoList;
}
public void setPhotoList(String[] photoList) {
	PhotoList = photoList;
}
public List<step> getSteps() {
	return Steps;
}
public void setSteps(List<step> steps) {
	Steps = steps;
}
public List<Stuff> getStuff() {
	return Stuff;
}
public void setStuff(List<Stuff> stuff) {
	Stuff = stuff;
}
public List<Stuff> getMainStuff() {
	return MainStuff;
}
public void setMainStuff(List<Stuff> mainStuff) {
	MainStuff = mainStuff;
}
public List<Stuff> getOtherStuff() {
	return OtherStuff;
}
public void setOtherStuff(List<Stuff> otherStuff) {
	OtherStuff = otherStuff;
}
public int getAd_flag() {
	return ad_flag;
}
public void setAd_flag(int ad_flag) {
	this.ad_flag = ad_flag;
}
public ad_data getAd_data() {
	return ad_data;
}
public void setAd_data(ad_data ad_data) {
	this.ad_data = ad_data;
}
public String getCommentCount() {
	return CommentCount;
}
public void setCommentCount(String commentCount) {
	CommentCount = commentCount;
}
public String[] getCommentList() {
	return CommentList;
}
public void setCommentList(String[] commentList) {
	CommentList = commentList;
}
public int getProductConunt() {
	return ProductConunt;
}
public void setProductConunt(int productConunt) {
	ProductConunt = productConunt;
}
public int[] getProduct() {
	return Product;
}
public void setProduct(int[] product) {
	Product = product;
}
public int getVIP() {
	return VIP;
}
public void setVIP(int vIP) {
	VIP = vIP;
}

}