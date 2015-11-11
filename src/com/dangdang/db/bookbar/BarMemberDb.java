package com.dangdang.db.bookbar;

import java.util.List;

import com.dangdang.bookbar.meta.BarMember;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

public class BarMemberDb {
	/**
	 * 查找某个吧里所有成员	
	 * @param
	 *     barId: 吧Id 
	 */
	public static List<BarMember> getBarRemember(String barId) throws Exception{
		//member_status（1 成员，2 申请吧主， 3 吧主）
		String sql = "select * from bar_member where member_status in (1,2,3) and bar_id="+barId
				     +"  order by member_status desc, bar_member_id ASC";
		List<BarMember> members = DbUtil.selectList(Config.BOOKBARDBConfig, sql, BarMember.class);
		return members;
	}
	
	/**
	 * 查找某个吧里特定的用户成员
	 * @param
	 *     barId: 吧Id
	 *     custId:用户custId	 
	 */
	public static BarMember getOneBarRemember(String barId, String custId) throws Exception{
		//member_status（1 成员，2 申请吧主， 3 吧主）
		String sql = "select * from bar_member where member_status in (1,2,3) and bar_id="+barId+" and cust_id="+custId
				    +"  order by member_status desc, bar_member_id ASC";;
		BarMember member = DbUtil.selectOne(Config.BOOKBARDBConfig, sql, BarMember.class);
		return member;
	}


	/*
	获取某个人的吧列表
	参数：
		cust_id：
	 */
	public static List<com.dangdang.readerV5.bookbar.BarMember> getOwnerBars(String cust_id) throws Exception {
		String sql = "select * from bar_member where cust_id="+cust_id+" and member_status=3";
		List<com.dangdang.readerV5.bookbar.BarMember> bars = DbUtil.selectList(Config.BOOKBARDBConfig, sql, com.dangdang.readerV5.bookbar.BarMember.class);
		return bars;
	}
}
