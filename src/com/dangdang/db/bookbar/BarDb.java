package com.dangdang.db.bookbar;

import java.util.List;
import java.util.Random;

import com.dangdang.bookbar.meta.Bar;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.enumeration.BarStatus;

public class BarDb {
	
	public static Bar getBarInfo(BarStatus status) throws Exception{
		String sql = "SELECT * FROM bar where bar_status="+status.getIndex()+" and member_num>0 limit 100";
		List<Bar> bar = DbUtil.selectList(Config.BOOKBARDBConfig, sql, Bar.class);	
		return bar.get((new Random()).nextInt(100));			
	}
	
	public static Bar getBarInfo(BarStatus status, String value) throws Exception{
		String sql = "SELECT * FROM bar where bar_status="+status.getIndex()+" and bar_id="+value;
		Bar bar = DbUtil.selectOne(Config.BOOKBARDBConfig, sql, Bar.class);	
		return bar;
	}

}
