package com.dangdang.readerV5.reponse;

import java.util.List;

public class ArticleContent {
	String commentNum;
	String content;
	String custId;
	List<String> imgList;
	String isPraise;
	String isTop;
	String lastModifiedDateMsec;
	String mediaDigestId;
	String praiseNum;
	String title;
	String type;
	UserBaseInfo userBaseInfo;
	
	public String getCommentNum(){
		return commentNum;
	}
	public String getContent(){
		return content;
	}
	public String getCustId(){
		return custId;
	}
	public List<String> getImgList(){
		return imgList;
	}
	public String getIsPraise(){
		return isPraise;
	}
	public String getIsTop(){
		return isTop;
	}
	public String getLastModifiedDateMsec(){
		return lastModifiedDateMsec;
	}
	public String getMediaDigestId(){
		return mediaDigestId;
	}
	public String getPraiseNum(){
		return praiseNum;
	}
	public String getTitle(){
		return title;
	}
	public String getType(){
		return type;
	}
	public UserBaseInfo getUserBaseInfo(){
		return userBaseInfo;
	}
	public void setCommentNum(String commentNum){
		this.commentNum = commentNum;
	}
	public void setContent(String content){
		this.content = content;
	}
	public void setCustId(String custId){
		this.custId = custId;
	}
	public void setImgList(List<String> imgList){
		this.imgList = imgList;
	}
	public void setIsPraise(String isPraise){
		this.isPraise = isPraise;
	}
	public void setIsTop(String isTop){
		this.isTop = isTop;
	}
	public void setLastModifiedDateMsec(String lastModifiedDateMsec){
		this.lastModifiedDateMsec = lastModifiedDateMsec;
	}
	public void setMediaDigestId(String mediaDigestId){
		this.mediaDigestId = mediaDigestId;
	}
	public void setPraiseNum(String praiseNum){
		this.praiseNum = praiseNum;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public void setType(String type){
		this.type = type;
	}
	public void setUserBaseInfo(UserBaseInfo userBaseInfo){
		this.userBaseInfo = userBaseInfo;
	}

}
