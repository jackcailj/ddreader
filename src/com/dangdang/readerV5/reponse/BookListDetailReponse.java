package com.dangdang.readerV5.reponse;

import java.util.List;

public class BookListDetailReponse {
	List<MediaList> mediaList;
	Integer total;

	public List<MediaList> getMediaList() {
		return mediaList;
	}
	public void setMediaList(List<MediaList> mediaList) {
		this.mediaList = mediaList;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
}
