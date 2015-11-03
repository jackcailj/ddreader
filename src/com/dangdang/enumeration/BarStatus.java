package com.dangdang.enumeration;

public enum BarStatus {
	DAISHENHE("待审核",1),
	APPROVED("通过",2),
	GANYUSHENHE("干预审核" ,3),
	XIAJIA("下架",4);
	
	private String name;
	private int index;
	
	private BarStatus(String name, int index){
		this.name = name;
		this.index = index;
	}
	
	public static int getIndex(String name){
		for(BarStatus p : BarStatus.values()){
			if(p.getName().equals(name)){
				return p.index;
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
