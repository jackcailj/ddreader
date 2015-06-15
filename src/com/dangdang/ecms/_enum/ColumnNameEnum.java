package com.dangdang.ecms._enum;

/**
 * 书城的栏目名称
 * @author guohaiying
 *
 */
public enum ColumnNameEnum {
	Android("android客户端4.0"),
	IPHONE("iPhone客户端4.0"),
	IPAD("iPad客户端4.0"),
	recommend("主打推荐"),
	newBooks("新书首发"),
	borrowRead("免费借阅"),
	teLimitedFree("今日限免"),
	teFreeRob("免费抢"),
	teLimitedCheap("限时特价"),
	teVeryCheap("巨便宜");

	
	private String context;
		
	private ColumnNameEnum(String value) {
		// TODO Auto-generated constructor stub
		context=value;
	}
	   
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return context;
	}
	
}
