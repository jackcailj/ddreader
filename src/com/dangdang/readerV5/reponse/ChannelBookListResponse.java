package com.dangdang.readerV5.reponse;

public class ChannelBookListResponse {

	ChannelBookList bookList;
	String hasBoughtMonthly;
	public ChannelBookList getBookList() {
		return bookList;
	}
	public void setBookList(ChannelBookList bookList) {
		this.bookList = bookList;
	}
	public String getHasBoughtMonthly() {
		return hasBoughtMonthly;
	}
	public void setHasBoughtMonthly(String hasBoughtMonthly) {
		this.hasBoughtMonthly = hasBoughtMonthly;
	}
}
