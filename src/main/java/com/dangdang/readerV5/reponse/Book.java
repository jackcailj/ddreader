package com.dangdang.readerV5.reponse;

public class Book {
	String authorName;
	String bookType;
	String coverPic;
	String descs;
	String productId;
	String publisher;
	String title;
	
	public String getAuthorName(){
		return authorName;
	}
	public String getBookType(){
		return bookType;
	}
	public String getCoverPic(){
		return coverPic;
	}
	public String getDescs(){
		return descs;
	}
	public String getProductId(){
		return productId;
	}
	public String getPublisher(){
		return publisher;
	}
	public String getTitle(){
		return title;
	}
	
	public void setAuthorName(String authorName){
		this.authorName = authorName;
	}
	public void setBookType(String bookType){
		this.bookType = bookType;
	}
	public void setCoverPic(String coverPic){
		this.coverPic = coverPic;
	}
	public void setDescs(String descs){
		this.descs = descs;
	}
	public void setProductId(String productId){
		this.productId = productId;
	}
	public void setPublisher(String publisher){
		this.publisher = publisher;
	}
	public void setTitle(String title){
		this.title = title;
	}

}
