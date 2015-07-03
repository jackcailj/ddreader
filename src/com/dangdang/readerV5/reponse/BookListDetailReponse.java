package com.dangdang.readerV5.reponse;

import java.util.List;

public class BookListDetailReponse {
	List<ChannelMediaList> mediaList;
	String total;

	public List<ChannelMediaList> getMediaList() {
		return mediaList;
	}
	public void setMediaList(List<ChannelMediaList> mediaList) {
		this.mediaList = mediaList;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
}
