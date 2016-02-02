package com.dangdang.readerV5.reponse;

import java.util.List;

public class HotChannelResponse {
	List<ChannelList> channelList;
	int count;
	int total;
	public List<ChannelList> getChannelList() {
		return channelList;
	}
	public void setChannelList(List<ChannelList> channelList) {
		this.channelList = channelList;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}

}
