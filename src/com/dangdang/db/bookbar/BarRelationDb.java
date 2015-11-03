package com.dangdang.db.bookbar;

import java.util.List;
import java.util.Map;
import java.util.Random;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.enumeration.BarStatus;

public class BarRelationDb {
	public static Map<String,Object> getObjectId(BarStatus status) throws Exception{
		String sql = "select br.bar_id, br.object_id from bar_relation as br left join bar as b on br.bar_id=b.bar_id "
				+ "where b.bar_status="+status.getIndex()+" limit 100";		
		List<Map<String,Object>> list = DbUtil.selectList(Config.BOOKBARDBConfig, sql);
		return list.get((new Random()).nextInt(100));
	}
	
	public static Map<String,Object> getBarId(String value) throws Exception{
		String sql = "select * from bar_relation where object_id="+value;		
		Map<String,Object> map = DbUtil.selectOne(Config.BOOKBARDBConfig, sql);
		return map;
	}

}
