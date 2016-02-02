package com.dangdang.readerV5.reponse;

import java.util.List;

public class MonthlyChannel {
	List<MonthlyChannelList> list;

	public List<MonthlyChannelList> getChannelList() {
		return list;
	}
	public void setChannelList(List<MonthlyChannelList> channelList) {
		this.list = channelList;
	}
	
}
