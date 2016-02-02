package com.dangdang.enumeration;

public enum DigestId {
	
	StrategyBefore30("TYPE"), //阅读数前30条的攻略id
	StrategyAfter30("频道内容"), //阅读数后30条的攻略id
	UserDigestId("用户文章Id"), //3：频道  5：攻略
	DigestId("文章id");


	String content="";
	
	DigestId(String id){
		content=id;
	}

}

