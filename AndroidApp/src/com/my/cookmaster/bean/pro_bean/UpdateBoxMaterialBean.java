package com.my.cookmaster.bean.pro_bean;

public class UpdateBoxMaterialBean {
	private Long box_id;
	private String material_name;
	private Long material_id =(long) -1;
	private String material_kind_name;
	private Long material_kind_id = (long) -1;
	public Long getBox_id() {
		return box_id;
	}
	public void setBox_id(Long box_id) {
		this.box_id = box_id;
	}
	public String getMaterial_name() {
		return material_name;
	}
	public void setMaterial_name(String material_name) {
		this.material_name = material_name;
	}
	public String getMaterial_kind_name() {
		return material_kind_name;
	}
	public void setMaterial_kind_name(String material_kind_name) {
		this.material_kind_name = material_kind_name;
	}
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
}
