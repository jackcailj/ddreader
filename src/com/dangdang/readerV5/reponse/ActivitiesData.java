package com.dangdang.readerV5.reponse;

import java.util.List;

public class ActivitiesData {
	List<Activity> activitys;
	String currentDate;
	String systemDate;
	
	public List<Activity> getActivitys(){
		return activitys;
	}
	public String getCurrentDate(){
		return currentDate;
	}
	public String getsystemDate(){
		return systemDate;
    }
	
	public void setActivitys(List<Activity> activitys){
		this.activitys = activitys;
	}
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	public void setsystemDate(String currentDate){
		this.systemDate = currentDate;
    }

}
