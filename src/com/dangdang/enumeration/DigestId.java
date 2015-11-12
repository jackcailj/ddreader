package com.dangdang.enumeration;

public enum DigestId {
	
	StrategyBefore30("TYPE"), //阅读数前30条的攻略id
	StrategyAfter30("频道内容"); //阅读数后30条的攻略id


	String content="";
	
	DigestId(String id){
		content=id;
	}

}

