package com.dangdang.readerV5.reponse;

public class Module {
	String barModuleId;
	String moduleName;
	String showNum;
	String templateNo;
	String type;
	
	public String getBarModuleId(){
		return barModuleId;
	}
	public String getModuleName(){
		return moduleName;
	}
	public String getShowNum(){
		return showNum;
	}
	public String getTemplateNo(){
		return templateNo;
	}
	public String getType(){
		return type;
	}	
	public void setBarModuleId(String barModuleId){
		this.barModuleId = barModuleId;
	}
	public void setModuleName(String moduleName){
		this.moduleName = moduleName;
	}
	public void setShowNum(String showNum){
		this.showNum = showNum;
	}
	public void setTemplateNo(String templateNo){
		this.templateNo = templateNo;
	}
	public void setType(String type){
		this.type = type;
	}

}
