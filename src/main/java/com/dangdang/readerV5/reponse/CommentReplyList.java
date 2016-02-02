package com.dangdang.readerV5.reponse;

import java.util.List;

public class CommentReplyList {
	List<Comment> commentReplyList;
	String currentDate;
	String systemDate;
	
	public List<Comment> getCommentReplyList(){
		return commentReplyList;
	}
	public String getCurrentDate(){
		return currentDate;
	}
	public String getsystemDate(){
		return systemDate;
    }
	
	public void setCommentReplyList(List<Comment> commentReplyList){
		this.commentReplyList = commentReplyList;
	}
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	public void setsystemDate(String currentDate){
		this.systemDate = currentDate;
    }
}
