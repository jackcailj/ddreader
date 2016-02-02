package com.dangdang.readerV5.reponse;

import java.util.List;

public class GetMonthlyChannelListResponse {
	List<MonthlyChannelList> channelList;
	Integer count;
	Integer total;

	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List<MonthlyChannelList> getChannelList() {
		return channelList;
	}
	public void setChannelList(List<MonthlyChannelList> channelList) {
		this.channelList = channelList;
	}
	
}
