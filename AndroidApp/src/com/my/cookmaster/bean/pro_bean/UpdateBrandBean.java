package com.my.cookmaster.bean.pro_bean;

public class UpdateBrandBean {
	private String box_mac;
	private Long box_id;
	private Long brand_id= (long) -1;;
	private String name;
	private String corpor_name;
	private String corpor_tel;
	private String corpor_addr;
	private String corpor_url;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCorpor_name() {
		return corpor_name;
	}
	public void setCorpor_name(String corpor_name) {
		this.corpor_name = corpor_name;
	}
	public String getCorpor_tel() {
		return corpor_tel;
	}
	public void setCorpor_tel(String corpor_tel) {
		this.corpor_tel = corpor_tel;
	}
	public String getCorpor_addr() {
		return corpor_addr;
	}
	public void setCorpor_addr(String corpor_addr) {
		this.corpor_addr = corpor_addr;
	}
	public String getCorpor_url() {
		return corpor_url;
	}
	public void setCorpor_url(String corpor_url) {
		this.corpor_url = corpor_url;
	}
	public String getBox_mac() {
		return box_mac;
	}
	public void setBox_mac(String box_mac) {
		this.box_mac = box_mac;
	}
	public Long getBrand_id() {
		return brand_id;
	}
	public void setBrand_id(Long brand_id) {
		this.brand_id = brand_id;
	}
	public Long getBox_id() {
		return box_id;
	}
	public void setBox_id(Long box_id) {
		this.box_id = box_id;
	}
}
