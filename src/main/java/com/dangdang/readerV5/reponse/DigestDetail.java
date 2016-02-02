package com.dangdang.readerV5.reponse;

public class DigestDetail extends DigestList{
	String alreayMark;
	String cardUrl;
	String digestId;
	EbookInfo ebookInfo;
	String mediaId;
	String mood;
	String pic1Path;
	int reviewCnt;
	int topCnt;
	int clickCnt;
	int isPraise;
	int isSupportReward;
	
	public int getIsSupportReward() {
		return isSupportReward;
	}
	public void setIsSupportReward(int isSupportReward) {
		this.isSupportReward = isSupportReward;
	}
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
	public int getReviewCnt(){
		return reviewCnt;
	}
	public int getTopCnt(){
		return topCnt;
	}
	public int getClickCnt(){
		return clickCnt;
	}	
	public int getIsPraise(){
		return isPraise;
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
	public void setReviewCnt(int reviewCnt){
		this.reviewCnt = reviewCnt;
	}
	public void setTopCnt(int topCnt){
		this.topCnt = topCnt;
	}
	public void setClickCnt(int clickCnt){
		this.clickCnt = clickCnt;
	}	
	public void setIsPraise(int isPraise){
		this.isPraise = isPraise;
	}	

}
