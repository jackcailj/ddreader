package com.dangdang.reader.functional.reponse;

import java.util.List;

public class BookList {

	List<MobileEbookInfo> books;
    String categoryName;
    String custId;
    String lastChangedDate;
    
    public List<MobileEbookInfo> getBooks() {
		return books;
	}
	public void setBooks(List<MobileEbookInfo> books) {
		this.books = books;
	}	
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}	
	
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}	
	
	public String getLastChangedDate() {
		return lastChangedDate;
	}
	public void setLastChangedDate(String lastChangedDate) {
		this.lastChangedDate = lastChangedDate;
	}	
}
