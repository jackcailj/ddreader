package com.dangdang.readerV5.reponse;

public class ArticleInfo {
	Article article;
	String currentDate;
	String systemDate;
	
	public Article getArticle(){
		return article;
	}
	public String getCurrentDate(){
		return currentDate;
	}
	public String getsystemDate(){
		return systemDate;
    }
	
	public void setArticle(Article article){
		this.article = article;
	}
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	public void setsystemDate(String currentDate){
		this.systemDate = currentDate;
    }

}
