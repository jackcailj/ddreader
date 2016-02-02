package com.dangdang.readerV5.reponse;

import java.util.List;

import com.dangdang.bookbar.meta.Bar;

public class ArticleListData {
	List<ArticleList> articleList;	
	Bar barInfo; //传objectId的时候把吧信息也返回了
	String currentDate;
	String systemDate;
	int updateArticleNum;  //单品页帖子列表没有该字段
	
	public List<ArticleList> getArticleList(){
		return articleList;
	}
	public Bar getBarInfo(){
		return barInfo;
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
	public void setBarInfo(Bar barInfo){
		this.barInfo = barInfo;
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
