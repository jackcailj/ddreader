package com.dangdang.reader.functional.reponse;

import java.util.*;

import com.dangdang.digital.meta.MediaActivityInfo;

public class GetEbookOrderFlowV2Reponse {
	String key;
	List<MediaActivityInfo> payingProducts;
	
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public List<MediaActivityInfo> getPayingProducts() {
		return payingProducts;
	}
	public void setPayingProducts(List<MediaActivityInfo> payingProducts) {
		this.payingProducts = payingProducts;
	}
}
