package com.dangdang.readerV5.reponse;

import java.util.List;

public class BookFriendMoments {
	List<BookFriendMoment> bookFriendMoments;
	String currentDate;
	String systemDate;
	boolean hasNext;
	
	public List<BookFriendMoment> getBookFriendMoments(){
		return bookFriendMoments;
	}
	public String getCurrentDate(){
		return currentDate;
	}
	public String getsystemDate(){
		return systemDate;
    }	
	public boolean getHasNext(){
		return hasNext;
    }
	
	public void setBookFriendMoments(List<BookFriendMoment> bookFriendMoments){
		this.bookFriendMoments = bookFriendMoments;
	}
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	public void setsystemDate(String currentDate){
		this.systemDate = currentDate;
    }
	public void setHasNext(boolean hasNext){
		this.hasNext = hasNext;
    }

}
