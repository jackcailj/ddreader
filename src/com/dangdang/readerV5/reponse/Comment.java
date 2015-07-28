package com.dangdang.readerV5.reponse;

public class Comment {
	Long commentId;
	String commentNum;
	String commentParentId;
	String content;
	String createDate;
	String deviceType;
	String isAnonymous;
	String isDelete;
	String lastModifiedDate;
	String nickName1;
	String nickName2;
	String remark;
	String replyCommentId;
	String replyId;
	String status;
	long targetId;
	String targetSource;
	String up;
	String userId;
	
	public Long getCommentId(){
		return commentId;
	}
	public String getCommentNum(){
		return commentNum;
	}
	public String getCommentParentId(){
		return commentParentId;
	}
	public String getContent(){
		return content;
	}
	public String getCreateDate(){
		return createDate;
	}
	public String getDeviceType(){
		return deviceType;
	}
	public String getIsAnonymous(){
		return isAnonymous;
	}
	public String getIsDelete(){
		return isDelete;
	}
	public String getLastModifiedDate(){
		return lastModifiedDate;
	}
	public String getNickName1(){
		return nickName1;
	}
	public String getNickName2(){
		return nickName2;
	}
	public String getRemark(){
		return remark;
	}
	public String getReplyCommentId(){
		return replyCommentId;
	}
	public String getReplyId(){
		return replyId;
	}
	public String getStatus(){
		return status;
	}
	public long getTargetId(){
		return targetId;
	}
	public String getTargetSource(){
		return targetSource;
	}
	public String getUp(){
		return up;
	}
	public String getUserId(){
		return userId;
	}
	
	public void setCommentId(Long commentId){
		this.commentId = commentId;
	}
	public void setCommentNum(String commentNum){
		this.commentNum = commentNum;
	}
	public void setCommentParentId(String commentParentId){
		this.commentParentId = commentParentId;
	}
	public void setContent(String content){
		this.content = content;
	}
	public void setCreateDate(String createDate){
		this.createDate = createDate;
	}
	public void setDeviceType(String deviceType){
		this.deviceType = deviceType;
	}
	public void setIsAnonymous(String isAnonymous){
		this.isAnonymous = isAnonymous;
	}
	public void setIsDelete(String isDelete){
		this.isDelete = isDelete;
	}
	public void setLastModifiedDate(String lastModifiedDate){
		this.lastModifiedDate = lastModifiedDate;
	}
	public void setNickName1(String nickName1){
		this.nickName1 = nickName1;
	}
	public void setNickName2(String nickName2){
		this.nickName2 = nickName2;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}
	public void setReplyCommentId(String replyCommentId){
		this.replyCommentId = replyCommentId;
	}
	public void setReplyId(String replyId){
		this.replyId = replyId;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public void setTargetId(long targetId){
		this.targetId = targetId;
	}
	public void setTargetSource(String targetSource){
		this.targetSource = targetSource;
	}
	public void setUp(String up){
		this.up = up;
	}
	public void setUserId(String userId){
		this.userId = userId;
	}

}
