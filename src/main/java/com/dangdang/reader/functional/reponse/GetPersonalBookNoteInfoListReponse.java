package com.dangdang.reader.functional.reponse;

import java.util.List;



public class GetPersonalBookNoteInfoListReponse {
	
	Integer bookNo;
	List<BookNoteInfo> bookNoteInfoList;
	public Integer getBookNo() {
		return bookNo;
	}
	public void setBookNo(Integer bookNo) {
		this.bookNo = bookNo;
	}
	public List<BookNoteInfo> getBookNoteInfoList() {
		return bookNoteInfoList;
	}
	public void setBookNoteInfoList(List<BookNoteInfo> bookNoteInfoList) {
		this.bookNoteInfoList = bookNoteInfoList;
	}
	
	
}
