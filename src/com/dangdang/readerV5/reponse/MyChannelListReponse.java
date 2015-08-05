package com.dangdang.readerV5.reponse;


import java.util.List;
public class MyChannelListReponse {
	List<MyChannelList> channelList;
	String count;
	String total;
	public List<MyChannelList> getChannelList() {
		return channelList;
	}
	public void setChannelList(List<MyChannelList> channelList) {
		this.channelList = channelList;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}

}
