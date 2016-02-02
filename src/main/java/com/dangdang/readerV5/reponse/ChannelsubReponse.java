package com.dangdang.readerV5.reponse;

import com.dangdang.ddframework.dataverify.verify_annotation.NotNull;

public class ChannelsubReponse {
	@NotNull
	Integer subNumber;

	public Integer getSubNumber() {
		return subNumber;
	}

	public void setSubNumber(Integer subNumber) {
		this.subNumber = subNumber;
	}
}
