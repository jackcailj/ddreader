package com.dangdang.db.bookbar;

import java.util.List;
import java.util.Random;

import com.dangdang.bookbar.meta.Bar;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.util.Util;
import com.dangdang.enumeration.BarStatus;

public class BarDb {
	
	public static Bar getBarInfo(BarStatus status) throws Exception{
		String sql = "select * from bar b where b.bar_status="+status.getIndex()+" and b.bar_id in "
				+ "(SELECT bm.bar_id from bar_member bm where bm.member_status=3) limit 100";
		List<Bar> bar = DbUtil.selectList(Config.BOOKBARDBConfig, sql, Bar.class);	
		return bar.get((new Random()).nextInt(100));			
	}
	
	public static Bar getBarInfo(BarStatus status, String value) throws Exception{
		String sql = "SELECT * FROM bar where bar_status="+status.getIndex()+" and bar_id="+value;
		Bar bar = DbUtil.selectOne(Config.BOOKBARDBConfig, sql, Bar.class);	
		return bar;
	}
	
	public static List<Bar> getBarListOfBarOwner(Long custId){
		String sql = "select * from bar b where b.bar_status="+BarStatus.APPROVED.getIndex()+" and b.bar_id in "
				+ "(SELECT bm.bar_id from bar_member bm where bm.cust_id="+custId+" and bm.member_status=3)";		
		try{
			List<Bar> bars = DbUtil.selectList(Config.BOOKBARDBConfig, sql, Bar.class);
			return bars;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}		
	}

}
