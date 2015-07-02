package com.dangdang.readerV5.reponse;

import java.util.List;

public class BookListDetailReponse {
	String currentDate;
	List<MediaList> mediaList;
	String systemDate;
	Integer total;
	public String getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	public List<MediaList> getMediaList() {
		return mediaList;
	}
	public void setMediaList(List<MediaList> mediaList) {
		this.mediaList = mediaList;
	}
	public String getSystemDate() {
		return systemDate;
	}
	public void setSystemDate(String systemDate) {
		this.systemDate = systemDate;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
}
