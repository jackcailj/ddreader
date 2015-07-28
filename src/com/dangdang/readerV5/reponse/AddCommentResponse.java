package com.dangdang.readerV5.reponse;

import java.util.List;

public class AddCommentResponse {
	int commentId;
	int commentNum;
	String currentDate;
	String systemDate;
	
	public int getCommentId(){
		return commentId;
	}
	public int getCommentNum(){
		return commentNum;
	}
	public String getCurrentDate(){
		return currentDate;
	}
	public String getsystemDate(){
		return systemDate;
    }
	
	public void setCommentId(int commentId){
		this.commentId = commentId;
	}
	public void setCommentNum(int commentNum){
		this.commentNum = commentNum;
	}
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	public void setsystemDate(String currentDate){
		this.systemDate = currentDate;
    }
}
