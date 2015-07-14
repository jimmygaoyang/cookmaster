package com.my.cookmaster.bus.protocol;

public class OutMaterialBean extends BaseBean{
	private String amount;//精确到个位
	private int cmdId=1;

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public int getCmdId() {
		return cmdId;
	}

}
