package com.dangdang.readerV5.reponse;

import java.util.List;

public class MediaSale {

	String isStore;
	List<EbookMediaList> mediaList;
	String price;
	String saleId;
	String type;
	public String getIsStore() {
		return isStore;
	}
	public void setIsStore(String isStore) {
		this.isStore = isStore;
	}
	public List<EbookMediaList> getMediaList() {
		return mediaList;
	}
	public void setMediaList(List<EbookMediaList> mediaList) {
		this.mediaList = mediaList;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getSaleId() {
		return saleId;
	}
	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
