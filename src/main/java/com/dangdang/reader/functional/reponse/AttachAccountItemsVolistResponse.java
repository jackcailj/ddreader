package com.dangdang.reader.functional.reponse;

import java.util.List;

public class AttachAccountItemsVolistResponse {
    List<AttachAccountItemsVolist> attachAccountItemsVolist;
    String currentDate;
    String systemDate;
    
    public List<AttachAccountItemsVolist> getAttachAccountItemsVolist(){
    	return attachAccountItemsVolist;
    }
    
    public void setAttachAccountItemsVolist(List<AttachAccountItemsVolist> attachAccountItemsVolist){
    	this.attachAccountItemsVolist = attachAccountItemsVolist;
    }
    
	public String getCurrentDate(){
		return currentDate;
	}
		
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	
	public String getSystemDate(){
		return systemDate;
	}
		
	public void setSystemDate(String systemDate){
		this.systemDate = systemDate;
	}
}
