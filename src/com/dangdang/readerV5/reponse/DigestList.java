package com.dangdang.readerV5.reponse;

import java.util.List;

public class DigestList {	
     List<AuthorList> authorList;
     List<SignList> signList;
     String cardRemark;
     String cardTitle;
     String cardType;
     String createTime;
     String id;
     
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
     public String getId(){
    	 return id;
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
     public void setId(String id){
    	 this.id = id;
     }
}
