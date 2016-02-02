package com.dangdang.enumeration;

/**
 * 用户的频道   书单  修改内容
 * @author guohaiying
 */
public enum ModifyContent {
	ChannelName("频道名称"), 
	ChannelDescription("频道内容"), 
	ChannelIcon("频道背景图"),
	BookListName("书单名称"),
	BookListDescription("书单内容"),
	BookListIcon("书单背景图");

	String content="";
	
	ModifyContent(String id){
		content=id;
	}

}
