package com.dangdang.readerV5.reponse;

import java.util.List;

public class ArticleList {
	String articleId;
	String barId;
	String commentNum;
	String content;
	String custId;
	String headPhoto;
	List<String> imgList;
	String isDel;
	String isPraise;
	String isShow;
	String isTop;
	String isWonderful;
	String lastModifiedDateMsec;
	String mediaDigestId;
	String nickName;
	String praiseNum;
	String title;
	String type;
	UserBaseInfo userBaseInfo;
	
	public String getArticleId(){
		return articleId;
	}
	public String getBarId(){
		return barId;
	}
	public String getCommentNum(){
		return commentNum;
	}
	public String getContent(){
		return content;
	}
	public String getCustId(){
		return custId;
	}
	public String getHeadPhoto(){
		return headPhoto;
	}
	public List<String> getImgList(){
		return imgList;
	}
	public String getIsDel(){
		return isDel;
	}
	public String getIsPraise(){
		return isPraise;
	}
	public String getIsShow(){
		return isShow;
	}
	public String getIsTop(){
		return isTop;
	}
	public String getIsWonderful(){
		return isWonderful;
	}
	public String getLastModifiedDateMsec(){
		return lastModifiedDateMsec;
	}
	public String getMediaDigestId(){
		return mediaDigestId;
	}
	public String getNickName(){
		return nickName;
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
	
	public void setArticleId(String articleId){
		this.articleId = articleId;
	}
	public void setBarId(String barId){
		this.barId = barId;
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
	public void setHeadPhoto(String headPhoto){
		this.headPhoto = headPhoto;
	}
	public void setImgList(List<String> imgList){
		this.imgList = imgList;
	}
	public void setIsDel(String isDel){
		this.isDel = isDel;
	}
	public void setIsPraise(String isPraise){
		this.isPraise = isPraise;
	}
	public void setIsShow(String isShow){
		this.isShow = isShow;
	}
	public void setIsTop(String isTop){
		this.isTop = isTop;
	}
	public void setIsWonderful(String isWonderful){
		this.isWonderful = isWonderful;
	}
	public void setLastModifiedDateMsec(String lastModifiedDateMsec){
		this.lastModifiedDateMsec = lastModifiedDateMsec;
	}
	public void setMediaDigestId(String mediaDigestId){
		this.mediaDigestId = mediaDigestId;
	}
	public void setNickName(String nickName){
		this.nickName = nickName;
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
