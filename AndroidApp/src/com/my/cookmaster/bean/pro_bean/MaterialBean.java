package com.my.cookmaster.bean.pro_bean;

public class MaterialBean {
	private Long material_id;
	private Long material_kind_id;
	private String material_kind_name;
	private String name;
	private Long brand_id;
	private String brand_name;
	private Long ref;
	public Long getMaterial_id() {
		return material_id;
	}
	public void setMaterial_id(Long material_id) {
		this.material_id = material_id;
	}
	public Long getMaterial_kind_id() {
		return material_kind_id;
	}
	public void setMaterial_kind_id(Long material_kind_id) {
		this.material_kind_id = material_kind_id;
	}
	public Long getBrand_id() {
		return brand_id;
	}
	public void setBrand_id(Long brand_id) {
		this.brand_id = brand_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	public Long getRef() {
		return ref;
	}
	public void setRef(Long ref) {
		this.ref = ref;
	}
	public String getMaterial_kind_name() {
		return material_kind_name;
	}
	public void setMaterial_kind_name(String material_kind_name) {
		this.material_kind_name = material_kind_name;
	}
	
} 
