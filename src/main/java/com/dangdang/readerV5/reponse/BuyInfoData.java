package com.dangdang.readerV5.reponse;

public class BuyInfoData {
	long attachAccountMoney;
	String currentDate;
	String endChapterId;
	String endChapterTitle;
	int freeChapterCount;
	long masterAccountMoney;
	int needBuyChapterCount;
	long needPay;
	String startChapterId;
	String startChapterTitle;
	String systemDate;
	
	public long getAttachAccountMoney(){
		return attachAccountMoney;
	}
	public String getCurrentDate(){
		return currentDate;
	}
	public String getEndChapterId(){
		return endChapterId;
	}
	public String getEndChapterTitle(){
		return endChapterTitle;
	}
	public int getFreeChapterCount(){
		return freeChapterCount;		
	}
	public long getMasterAccountMoney(){
		return masterAccountMoney;
	}
	public int getNeedBuyChapterCount(){
		return needBuyChapterCount;
	}
	public long getNeedPay(){
		return needPay;
	}
	public String getStartChapterId(){
		return startChapterId;
	}
	public String getCtartChapterTitle(){
		return startChapterTitle;
	}
	public String getSystemDate(){
		return systemDate;
	}
	public void setAttachAccountMoney(long  attachAccountMoney){
		this.attachAccountMoney = attachAccountMoney;
	}
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	public void setEndChapterId(String endChapterId){
		this.endChapterId = endChapterId;
	}
	public void setEndChapterTitle(String endChapterTitle){
		this.endChapterTitle = endChapterTitle;
	}
	public void setFreeChapterCount(int freeChapterCount ){
		this.freeChapterCount = freeChapterCount;		
	}
	public void setMasterAccountMoney(long masterAccountMoney){
		this.masterAccountMoney = masterAccountMoney;
	}
	public void setNeedBuyChapterCount(int needBuyChapterCount){
		this.needBuyChapterCount = needBuyChapterCount;
	}
	public void setNeedPay(int needPay){
		this.needPay = needPay;
	}
	public void setStartChapterId(String startChapterId){
		this.startChapterId = startChapterId;
	}
	public void setCtartChapterTitle(String startChapterTitle){
		this.startChapterTitle = startChapterTitle;
	}
	public void setSystemDate(String systemDate){
		this.systemDate = systemDate;
	}

}
