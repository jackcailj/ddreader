package com.dangdang.autotest.common;

import java.util.List;

	public class User {
	public Integer age;
	public String name;
	public String address;
	public String email;
	List<Course> course;
	//public Course course;

//	public Course getCourse() {
//	return course;
//	}
//	public void setCourse(Course course) {
//	this.course = course;
//	}
	 public List<Course> getCourse() {
	 return course;
	 }
	 public void setCourse(List<Course> course) {
	 this.course = course;
	 }
	public String getAddress() {
	return address;
	}
	public void setAddress(String address) {
	this.address = address;
	}
	public String getEmail() {
	return email;
	}
	public void setEmail(String email) {
	this.email = email;
	}
	public Integer getAge() {
	return age;
	}
	public void setAge(Integer age) {
	this.age = age;
	}
	public String getName() {
	return name;
	}
	public void setName(String name) {
	this.name = name;
	}

}
