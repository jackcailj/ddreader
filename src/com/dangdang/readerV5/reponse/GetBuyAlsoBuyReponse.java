package com.dangdang.readerV5.reponse;

import java.util.List;

public class GetBuyAlsoBuyReponse {

	List<BuyMediaList> mediaList;
	String total;
	public List<BuyMediaList> getMediaList() {
		return mediaList;
	}
	public void setMediaList(List<BuyMediaList> mediaList) {
		this.mediaList = mediaList;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	
}
