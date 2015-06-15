package com.dangdang.reader.functional.reponse;

import java.util.Date;

public class MobileEbookInfo {
	String cover;
	String bookName;
	String author;
	//Date publishDate;
	Integer score;
	String desc;
	String publisher;
	String productId;
	String paperProductId;
	Integer bell;
	String imgUrl;
	Integer price;
	String firstPromoModel;
	
	
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getFirstPromoModel() {
		return firstPromoModel;
	}
	public void setFirstPromoModel(String firstPromoModel) {
		this.firstPromoModel = firstPromoModel;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
/*	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}*/
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getPaperProductId() {
		return paperProductId;
	}
	public void setPaperProductId(String paperProductId) {
		this.paperProductId = paperProductId;
	}
	public Integer getBell() {
		return bell;
	}
	public void setBell(Integer bell) {
		this.bell = bell;
	}
	
	
}
