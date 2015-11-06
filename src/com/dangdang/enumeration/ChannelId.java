package com.dangdang.enumeration;

/**
 * 
 * @author guohaiying
 *
 */
public enum ChannelId {
	TYPE("TYPE"), //企业或个人频道 1企业频道   2个人频道
	MonthlyContent("频道内容"), // 0  无文章无书单无攻略的频道  1 无书单  2无文章  3无攻略   4有书单文章攻略
	IfAllowMonthly("是否允许包月"),  //  1允许 0不允许
	IfAlreadyMonthly("是否已经包月"), //   1已包月  0未包月
	IfOverdue("是否过期"), //   1已过期  0未过期
	IfAutoRenew("是否自动续费"), //   1续费  0不续费
	Offline("下线"), // 0频道支持包月且下线  1//未过期且已下线
	IfSub("是否已经订阅"); // 1已订阅  0未订阅


	String content="";
	
	ChannelId(String id){
		content=id;
	}

}
