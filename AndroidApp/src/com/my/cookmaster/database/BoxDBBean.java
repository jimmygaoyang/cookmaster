package com.my.cookmaster.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable
public class BoxDBBean {
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private Long box_id;
	@DatabaseField
	private String box_mac;
	@DatabaseField
	private Long material_kind_id;
	@DatabaseField
	private String material_kind_name;
	@DatabaseField
	private Long material_id;
	@DatabaseField
	private String material_name;
	@DatabaseField
	private Long brand_id;
	@DatabaseField
	private String brand_name;
	@DatabaseField
	private String sw_ver;
	@DatabaseField
	private String corpor_name;
	@DatabaseField
	private String corpor_tel;
	@DatabaseField
	private String corpor_addr;
	@DatabaseField
	private String corpor_url;
	public Long getBox_id() {
		return box_id;
	}
	public void setBox_id(Long box_id) {
		this.box_id = box_id;
	}
	public String getBox_mac() {
		return box_mac;
	}
	public void setBox_mac(String box_mac) {
		this.box_mac = box_mac;
	}
	public Long getMaterial_kind_id() {
		return material_kind_id;
	}
	public void setMaterial_kind_id(Long material_kind_id) {
		this.material_kind_id = material_kind_id;
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
	public Long getBrand_id() {
		return brand_id;
	}
	public void setBrand_id(Long brand_id) {
		this.brand_id = brand_id;
	}
	public String getSw_ver() {
		return sw_ver;
	}
	public void setSw_ver(String sw_ver) {
		this.sw_ver = sw_ver;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
