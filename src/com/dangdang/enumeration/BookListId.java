package com.dangdang.enumeration;

/**
 * 
 * @author guohaiying
 *
 */
public enum BookListId {
	UserBookListId("用户书单"), 
	BookListId("有效的书单"),
	MyMonthlyBookListId("我包月的频道书单（在有效期内）");


	String content="";
	
	BookListId(String id){
		content=id;
	}

}
