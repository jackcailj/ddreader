package com.dangdang.readerV5.reponse;

import java.util.List;

public class DigestList {	
     List<AuthorList> authorList;
     List<SignList> signList;
     String cardRemark;
     String cardTitle;
     String cardType;
     String createTime;
     String dayOrNight;
     String id;
     int isPraise;
     String pic1Path;
     int replyCnt;
     String showStartDateYmdLong;
     String smallPic1Path;
     String smallPic2Path;
     String smallPic3Path;
     String sortPage;
     int topCnt;
     
     public List<AuthorList> getAuthorList(){
    	 return authorList;
     }
     public List<SignList> getSignList(){
    	 return signList;
     }
     public String getCardRemark(){
    	 return cardRemark;
     }
     public String getCardTitle(){
    	 return cardTitle;
     }
     public String getCardType(){
    	 return cardType;
     }
     public String getCreateTime(){
    	 return createTime;
     }     
     public String getDayOrNight(){
    	 return dayOrNight;
     }
     public String getId(){
    	 return id;
     }
     public int getIsPraise(){
    	 return isPraise;
     }
     public String getPic1Path(){
    	 return pic1Path;
     }
     public int getReplyCnt(){
    	 return replyCnt;
     }
     public String getShowStartDateYmdLong(){
    	 return showStartDateYmdLong;
     }
     public String getSmallPic1Path(){
    	 return smallPic1Path;
     }
     public String getSmallPic2Path(){
    	 return smallPic2Path;
     }
     public String getSmallPic3Path(){
    	 return smallPic3Path;
     }
     public String getSortPage(){
    	 return sortPage;
     }
     public int getTopCnt(){
    	 return topCnt;
     }
     
     public void setAuthorList(List<AuthorList> authorList){
    	 this.authorList = authorList;
     }
     public void setSignList(List<SignList> signList){
    	 this.signList = signList;
     }
     public void setCardRemark(String cardRemark){
    	 this.cardRemark = cardRemark;
     }
     public void setCardTitle(String cardTitle){
    	 this.cardTitle = cardTitle;
     }
     public void setCardType(String cardType){
    	 this.cardType = cardType;
     }
     public void setCreateTime(String createTime){
    	 this.createTime = createTime;
     }
     public void setDayOrNight(String dayOrNight){
    	 this.dayOrNight = dayOrNight;
     }
     public void setId(String id){
    	 this.id = id;
     }
     public void setIsPraise(int isPraise){
    	 this.isPraise = isPraise;
     }
     public void setPic1Path(String pic1Path){
    	 this.pic1Path = pic1Path;
     }
     public void setReplyCnt(int replyCnt){
    	 this.replyCnt = replyCnt;
     }
     public void setShowStartDateYmdLong(String showStartDateYmdLong){
    	 this.showStartDateYmdLong = showStartDateYmdLong;
     }
     public void setSmallPic1Path(String smallPic1Path){
    	 this.smallPic1Path = smallPic1Path;
     }
     public void setSmallPic2Path(String smallPic2Path){
    	 this.smallPic2Path = smallPic2Path;
     }
     public void setSmallPic3Path(String smallPic3Path){
    	 this.smallPic3Path = smallPic3Path;
     }
     public void setSortPage(String sortPage){
    	 this.sortPage = sortPage;
     }
     public void setTopCnt(int topCnt){
    	 this.topCnt = topCnt;
     }
}
