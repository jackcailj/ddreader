package com.dangdang.readerV5.reponse;

import java.util.List;

public class SaleList {	
	Integer isStore;
	Integer isSupportFullBuy;
	List<MediaList> mediaList;
	Integer price;
	Integer saleId;
	Integer type;
	public Integer getIsStore() {
		return isStore;
	}
	public void setIsStore(Integer isStore) {
		this.isStore = isStore;
	}
	public List<MediaList> getMediaList() {
		return mediaList;
	}
	public void setMediaList(List<MediaList> mediaList) {
		this.mediaList = mediaList;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getSaleId() {
		return saleId;
	}
	public void setSaleId(Integer saleId) {
		this.saleId = saleId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getIsSupportFullBuy() {
		return isSupportFullBuy;
	}
	public void setIsSupportFullBuy(Integer isSupportFullBuy) {
		this.isSupportFullBuy = isSupportFullBuy;
	}

}
