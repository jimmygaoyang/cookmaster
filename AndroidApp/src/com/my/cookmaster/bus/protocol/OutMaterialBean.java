package com.my.cookmaster.bus.protocol;

public class OutMaterialBean extends BaseBean{
	private String amount;//��ȷ����λ
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
