package com.dangdang.readerV5.reponse;

public class CommentCount {
	int upcommentCount;
	int commentCount;
	String currentDate;
	String systemDate;
	
	public int getUpcommentCount(){
		return upcommentCount;
	}
	public int getCommentCount(){
		return commentCount;
	}
	public String getCurrentDate(){
		return currentDate;
	}
	public String getsystemDate(){
		return systemDate;
    }
	
	public void setUpcommentCount(int upcommentCount){
		this.upcommentCount = upcommentCount;
	}
	public void setCommentCount(int commentCount){
		this.commentCount = commentCount;
	}
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	public void setsystemDate(String currentDate){
		this.systemDate = currentDate;
    }

}
