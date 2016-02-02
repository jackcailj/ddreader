package com.dangdang.reader.functional.reponse;

public class LotteryResponse {

	String bellValue;
	String canDoNum;
	String currentDate;
	String lotteryStatus;
	String key;
	String prizeType;
	String recordId;
	String systemDate;
	String timestamp;
	MobileEbookInfo ebook;
	
	public String getBellValue(){
		return bellValue;
	}
	public String getCanDoNum(){
		return canDoNum;
	}
	public String getCurrentDate(){
		return currentDate;
	}
	public String getLotteryStatus(){
		return lotteryStatus;
	}
	public String getKey(){
		return key;
	}
	public String getPrizeType(){
		return prizeType;
	}
	public String getRecordId(){
		return recordId;
	}
	public String getSystemDate(){
		return systemDate;
	}
	public String getTimestamp(){
		return timestamp;
	}
	public MobileEbookInfo getEbook(){
		return ebook;
	}
	
	public void setBellValue(String bellValue){
		this.bellValue = bellValue;
	}
	public void setCanDoNum(String canDoNum){
		this.canDoNum = canDoNum;
	}
	public void setCurrentDate(String currentDate){
		this.currentDate = currentDate;
	}
	public void setLotteryStatus(String lotteryStatus){
		this.lotteryStatus = lotteryStatus;
	}
	public void setKey(String key){
		this.key = key;
	}
	public void setPrizeType(String prizeType){
		this.prizeType = prizeType;
	}
	public void setRecordId(String recordId){
		this.recordId = recordId;
	}
	public void setSystemDate(String systemDate){
		this.systemDate = systemDate;
	}
	public void setTimestamp(String timestamp){
		this.timestamp = timestamp;
	}
	public void setEbook(MobileEbookInfo ebook){
		this.ebook = ebook;
	}
}
