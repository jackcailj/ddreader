package com.dangdang.enumeration;

/**
 * @author guohaiying
 */
public enum ColumnCode {
	Channel("Channel"),  //频道栏目
	BookStore("BookStore");  //书城栏目

	String content="";
	
	ColumnCode(String id){
		content=id;
	}

}
