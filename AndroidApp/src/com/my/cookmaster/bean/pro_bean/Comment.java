
package com.my.cookmaster.bean.pro_bean;

import java.util.*;

public class Comment {
   private int UserId;
   private int UserName;
   private int Avatar;
   private int AtUserId;
   private int AtUserName;
   private int Content;
   private int CreateTime;
public int getUserId() {
	return UserId;
}
public void setUserId(int userId) {
	UserId = userId;
}
public int getUserName() {
	return UserName;
}
public void setUserName(int userName) {
	UserName = userName;
}
public int getAvatar() {
	return Avatar;
}
public void setAvatar(int avatar) {
	Avatar = avatar;
}
public int getAtUserId() {
	return AtUserId;
}
public void setAtUserId(int atUserId) {
	AtUserId = atUserId;
}
public int getAtUserName() {
	return AtUserName;
}
public void setAtUserName(int atUserName) {
	AtUserName = atUserName;
}
public int getContent() {
	return Content;
}
public void setContent(int content) {
	Content = content;
}
public int getCreateTime() {
	return CreateTime;
}
public void setCreateTime(int createTime) {
	CreateTime = createTime;
}

}