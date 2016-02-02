package com.dangdang.db.bookbar;

import java.util.List;

import com.dangdang.bookbar.meta.Recommend;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

public class RecommendDb {
	
	public static List<Recommend> getRecommendInfo(String date, String type) throws Exception{
		String sql = "select  * from recommend WHERE recommend_date=\""+date+"\" and type="+type;		
		List<Recommend> recommend = DbUtil.selectList(Config.BOOKBARDBConfig, sql, Recommend.class);
		return recommend;
	}
}
