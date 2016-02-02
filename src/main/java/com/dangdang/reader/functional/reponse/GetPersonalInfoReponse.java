package com.dangdang.reader.functional.reponse;

import java.util.List;

import javax.swing.text.StyledEditorKit.BoldAction;

public class GetPersonalInfoReponse {
	String beatOthers;  //阅历
	Integer bookNoteCount;
	List<Integer> favProductIds;
	Integer favTotalNum;
	Boolean isStar;
	public String getBeatOthers() {
		return beatOthers;
	}
	public void setBeatOthers(String beatOthers) {
		this.beatOthers = beatOthers;
	}
	public Integer getBookNoteCount() {
		return bookNoteCount;
	}
	public void setBookNoteCount(Integer bookNoteCount) {
		this.bookNoteCount = bookNoteCount;
	}
	public List<Integer> getFavProductIds() {
		return favProductIds;
	}
	public void setFavProductIds(List<Integer> favProductIds) {
		this.favProductIds = favProductIds;
	}
	public Integer getFavTotalNum() {
		return favTotalNum;
	}
	public void setFavTotalNum(Integer favTotalNum) {
		this.favTotalNum = favTotalNum;
	}
	public Boolean getIsStar() {
		return isStar;
	}
	public void setIsStar(Boolean isStar) {
		this.isStar = isStar;
	}
	
	
}
