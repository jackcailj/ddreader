package com.dangdang.readerV5.reponse;

import java.util.List;

public class SpecialTopicHistoryReponse {
	String count;
	List<SpecialTopicList> specialTopicList;
	String total;
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public List<SpecialTopicList> getSpecialTopicList() {
		return specialTopicList;
	}
	public void setSpecialTopicList(List<SpecialTopicList> specialTopicList) {
		this.specialTopicList = specialTopicList;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}

}
