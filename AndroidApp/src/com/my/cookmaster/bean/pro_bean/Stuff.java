

package com.my.cookmaster.bean.pro_bean;

import java.util.*;

public class Stuff {
   private String name;
   private String weight;
   private Long id;
   private Long cateid;

   private String cate;

   private int type;

   private int food_flag;

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getWeight() {
	return weight;
}

public void setWeight(String weight) {
	this.weight = weight;
}

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getCate() {
	return cate;
}

public void setCate(String cate) {
	this.cate = cate;
}

public int getType() {
	return type;
}

public void setType(int type) {
	this.type = type;
}

public int getFood_flag() {
	return food_flag;
}

public void setFood_flag(int food_flag) {
	this.food_flag = food_flag;
}

public Long getCateid() {
	return cateid;
}

public void setCateid(Long cateid) {
	this.cateid = cateid;
}

}