package com.dangdang.readerV5.reponse;

public class EbookInfo {
	String bookAuthor;
	String bookImgUrl;
	String bookName;
	String editorRecommend;
	String productId;
	
	public String getBookAuthor(){
		return bookAuthor;
	}
	public String getBookImgUrl(){
		return bookImgUrl;
	}
	public String getBookName(){
		return bookName;
	}
	public String getEditorRecommend(){
		return editorRecommend;
	}
	public String getProductId(){
		return productId;
	}	
	public void setBookAuthor(String bookAuthor){
		this.bookAuthor = bookAuthor;
	}
	public void setBookImgUrl(String bookImgUrl){
		this.bookImgUrl = bookImgUrl;
	}
	public void setBookName(String bookName){
		this.bookName = bookName;
	}
	public void setEditorRecommend(String editorRecommend){
		this.editorRecommend = editorRecommend;
	}
	public void setProductId(String productId){
		this.productId = productId;
	}

}
