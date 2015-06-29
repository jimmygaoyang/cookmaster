package com.my.cookmaster.bean.pro_bean;

public class addBoxBean {
	private String box_mac;
	private Long user_id = (long) -1;
	private Long material_kind_id =(long) -1;
	private Long material_id= (long) -1;
	private String software_version;
	private Long brand_id= (long) -1;
	private String material_kind_name = "δ֪";
	private String material_name= "δ֪";
	private String brand_name= "δ֪";
	public String getBox_mac() {
		return box_mac;
	}
	public void setBox_mac(String box_mac) {
		this.box_mac = box_mac;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Long getMaterial_kind_id() {
		return material_kind_id;
	}
	public void setMaterial_kind_id(Long material_kind_id) {
		this.material_kind_id = material_kind_id;
	}
	public Long getMaterial_id() {
		return material_id;
	}
	public void setMaterial_id(Long material_id) {
		this.material_id = material_id;
	}
	public String getSoftware_version() {
		return software_version;
	}
	public void setSoftware_version(String software_version) {
		this.software_version = software_version;
	}
	public Long getBrand_id() {
		return brand_id;
	}
	public void setBrand_id(Long brand_id) {
		this.brand_id = brand_id;
	}
	public String getMaterial_kind_name() {
		return material_kind_name;
	}
	public void setMaterial_kind_name(String material_kind_name) {
		this.material_kind_name = material_kind_name;
	}
	public String getMaterial_name() {
		return material_name;
	}
	public void setMaterial_name(String material_name) {
		this.material_name = material_name;
	}
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

}
