package com.dangdang.readerV5.reponse;

import java.util.List;

public class CommentList {
	List<List<Comment>> commentList;
	String currentDate;
	String systemDate;
	
	public List<List<Comment>> getCommentList(){
		return commentList;
	}
	public String getCurrentDate(){
		return currentDate;
	}
	public String getsystemDate(){
		return systemDate;
    }
	
	public void setCommentList(List<List<Comment>> commentList){
		this.commentList = commentList;
	}
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	public void setsystemDate(String currentDate){
		this.systemDate = currentDate;
    }
}
