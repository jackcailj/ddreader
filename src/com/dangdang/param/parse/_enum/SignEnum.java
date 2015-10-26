package com.dangdang.param.parse._enum;

/**
 * 签名
 * @author guohaiying
 *
 */
public enum SignEnum {

	purchase("purchase", 1); //purchaseEbookVirtualPayment 接口参数sign
	
	private String name;
	private int index;
	
	private SignEnum(String name, int index){
		this.name = name;
		this.index = index;
	}
	
	public static int getIndex(String name){
		for(SignEnum s : SignEnum.values()){
			if(s.getName().equals(name)){
				return s.index;
			}
		}
		return -1;
	}
	
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int getIndex(){
		return index;
	}
	
	public void setIndex(int index){
		this.index = index;
	}
}
