package com.dangdang.readerV5.reponse;

import java.util.List;

public class VoteInfo {
	int hasVote;
	int voteCount;
	List<Items> items;
	
	public int getHasVote(){
		return hasVote;
	}
	public int getVoteCount(){
		return voteCount;
	}	
	public List<Items> getItems(){
		return items;
	}
	
	public void setHasVote(int hasVote){
		this.hasVote = hasVote;
	}
	public void setVoteCount(int voteCount){
		this.voteCount = voteCount;
	}	
	public void setItems(List<Items> items){
		this.items = items;
	}

}
