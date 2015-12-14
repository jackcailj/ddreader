package com.dangdang.readerV5.reponse;

import java.util.List;

public class GetBuyAlsoBuyReponse {
	List<MediaList> mediaList;
	String total;
	public List<MediaList> getMediaList() {
		return mediaList;
	}
	public void setMediaList(List<MediaList> mediaList) {
		this.mediaList = mediaList;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	
}
