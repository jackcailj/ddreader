package com.dangdang.reader.functional.reponse;

public class BookNoteInfo {
	String bookAuthor;
	String bookCoverPic;
	String bookTitle;
	Integer count;
	Long modifyTime;
	Long productId;
	
	
	public String getBookAuthor() {
		return bookAuthor;
	}
	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
	public String getBookCoverPic() {
		return bookCoverPic;
	}
	public void setBookCoverPic(String bookCoverPic) {
		this.bookCoverPic = bookCoverPic;
	}
	public String getBookTitle() {
		return bookTitle;
	}
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Long getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Long modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	
}
