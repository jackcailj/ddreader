package com.dangdang.readerV5.reponse;

import java.util.List;

public class ArticleListData {
	List<ArticleList> articleList;
	String currentDate;
	String systemDate;
	int updateArticleNum;
	
	public List<ArticleList> getArticleList(){
		return articleList;
	}
	public String getCurrentDate(){
		return currentDate;
	}
	public String getsystemDate(){
		return systemDate;
    }
	public int getUpdateArticleNum(){
		return updateArticleNum;
	}	
	public void setArticleList(List<ArticleList> articleList){
		this.articleList = articleList;
	}
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	public void setsystemDate(String currentDate){
		this.systemDate = currentDate;
    }
	public void setUpdateArticleNum(int updateArticleNum){
		this.updateArticleNum = updateArticleNum;
	}
}
