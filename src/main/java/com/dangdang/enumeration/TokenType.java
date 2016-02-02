package com.dangdang.enumeration;

public enum TokenType {
	//频道->getMonthlyChannelList wiki used
	UserNoMonthly("没有频道包月"), //获取没有频道包月的Token
	UserMonthly("有频道包月(未过期)"), //获取有频道包月(未过期)的Token
	UserMonthlyOverdue("有频道包月(已过期)"),  //获取有频道包月(已过期)的Token
	MonthlyChannelOffline("有频道(已下架)包月(未过期)"), //获取有频道(已下架)包月(未过期)的Token	
	
	//书城->GetBuyAlsoBuy wiki used
	UserNoBuyBook("用户没有买过书"), //获取没有买过书的Token
	UserAlreadyBuyBook("用户已买过书"); //获取买过书的Token

	String content="";
	
	TokenType(String id){
		content=id;
	}

}
