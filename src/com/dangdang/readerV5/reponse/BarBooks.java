package com.dangdang.readerV5.reponse;

import java.util.List;

public class BarBooks {
	List<Book> books;
	String currentDate;
	String systemDate;
	
	public List<Book> getBooks(){
		return books;
	}
	public String getCurrentDate(){
		return currentDate;
	}
	public String getsystemDate(){
		return systemDate;
    }
	
	public void setBooks(List<Book> books){
		this.books = books;
	}
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	public void setsystemDate(String currentDate){
		this.systemDate = currentDate;
    }
}
