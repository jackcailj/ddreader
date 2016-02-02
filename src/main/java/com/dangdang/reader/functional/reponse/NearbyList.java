package com.dangdang.reader.functional.reponse;

import java.util.List;

import com.dangdang.ecms.meta.Ebook;

public class NearbyList {

	List<Ebook> books;
	String distance;
	String gender;
	String introduct;
	String nickname;
	String thumb;
	String userId;
	
	public	List<Ebook> getBooks(){
		return books;
	}
	public String getDistance(){
		return distance;
	}
	public String getGender(){
		return gender;
	}
	public String getIntroduct(){
		return introduct;
	}
	public String getNickname(){
		return nickname;
	}
	public String getThumb(){
		return thumb;
	}
	public String getUserId(){
		return userId;
	}
	
	public	void setBooks(List<Ebook> books){
		this.books = books;
	}
	public void setDistance(String distance){
		this.distance = distance;
	}
	public void setThumb(String thumb){
		this.thumb = thumb;
	}
	public void setUserId(String userId){
		this.userId = userId;
	}
	public void setGender(String gender){
		this.gender = gender;
	}
	public void setIntroduct(String introduct){
		this.introduct = introduct;
	}
	public void setNickname(String nickname){
		this.nickname = nickname;
	}
}
