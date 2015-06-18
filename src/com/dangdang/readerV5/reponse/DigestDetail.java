package com.dangdang.readerV5.reponse;

public class DigestDetail extends DigestList{
	String alreayMark;
	String cardUrl;
	String digestId;
	EbookInfo ebookInfo;
	String mediaId;
	String mood;
	String pic1Path;
	String reviewCnt;
	
	public String getAlreayMark(){
		return alreayMark;
	}
	public String getCardUrl(){
		return cardUrl;
	}
	public String getDigestId(){
		return digestId;
	}
	public EbookInfo getEbookInfo(){
		return ebookInfo;
	}
	public String getMediaId(){
		return mediaId;
	}
	public String getMood(){
		return mood;
	}
	public String getPic1Path(){
		return pic1Path;
	}
	public String getReviewCnt(){
		return reviewCnt;
	}
	
	public void setAlreayMark(String alreayMark){
		this.alreayMark = alreayMark;
	}
	public void setCardUrl(String cardUrl){
		this.cardUrl = cardUrl;
	}
	public void setDigestId(String digestId){
		this.digestId = digestId;
	}
	public void setEbookInfo(EbookInfo ebookInfo){
		this.ebookInfo = ebookInfo;
	}
	public void setMediaId(String mediaId){
		this.mediaId = mediaId;
	}
	public void setMood(String mood){
		this.mood = mood;
	}
	public void setPic1Path(String pic1Path){
		this.pic1Path = pic1Path;
	}
	public void setReviewCnt(String reviewCnt){
		this.reviewCnt = reviewCnt;
	}

}
