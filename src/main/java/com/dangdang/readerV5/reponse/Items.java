package com.dangdang.readerV5.reponse;

public class Items {
    String imageUrl;
    String itemDesc;
    int voteCount;
    int hasVote;
    
    public String getImageUrl(){
    	return imageUrl;
    }    
    public String getItemDesc(){
    	return itemDesc;
    }
    public int getVoteCount(){
    	return voteCount;
    }
    public int getHasVote(){
    	return hasVote;
    }
    
    public void setImageUrl(String imageUrl){
    	this.imageUrl = imageUrl;
    }    
    public void getItemDesc(String itemDesc){
    	this.itemDesc = itemDesc;
    }
    public void setVoteCount(int voteCount){
    	this.voteCount = voteCount;
    }
    public void setHasVote(int hasVote){
    	this.hasVote = hasVote;
    }
    

}
