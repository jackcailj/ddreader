package com.dangdang.readerV5.reponse;

public class ArticlePackage {

	String articleId;
	String author;
	String commentNum; //评论
	String description;
	String icon;
	String title;
	String topCnt; //赞
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(String commentNum) {
		this.commentNum = commentNum;
	}
	public String getTopCnt() {
		return topCnt;
	}
	public void setTopCnt(String topCnt) {
		this.topCnt = topCnt;
	}
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
