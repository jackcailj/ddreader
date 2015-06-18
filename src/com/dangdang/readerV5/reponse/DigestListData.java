package com.dangdang.readerV5.reponse;

import java.util.List;

public class DigestListData {

	 boolean hasNext;
     String systemDate;
     String currentDate;
     List<DigestList> digestList;
     
     public boolean getHasNext(){
    	 return hasNext;
     }
     
     public String getSystemDate(){
    	 return systemDate;
     }
     
     public String getCurrentDate(){
    	 return currentDate;
     }
     
     public List<DigestList> getDigestList(){
    	 return digestList;
     }
     
     public void setHasNext(boolean hasNext){
    	 this.hasNext = hasNext;
     }
     
     public void setSystemDate(String systemDate){
    	  this.systemDate = systemDate;
     }
     
     public void setCurrentDate(String currentDate){
    	 this.currentDate = currentDate;
     }
     
     public void setDigestList(List<DigestList> digestList){
    	 this.digestList = digestList;
     }
}
