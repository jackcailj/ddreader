package com.dangdang.db.comment;

import java.util.Map;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

/**
 * @author guohaiying
 */
public class PraiseInfoDb {	
	public static Boolean get(String custId, String targetId) throws Exception{
		String selectSQL = "SELECT COUNT(1) FROM `praise_info`" +
				" WHERE comment_type=1 AND user_id ="+custId+
						" AND target_id="+targetId+
						" AND target_source IN (4000,7000)";
		Map<String, Object> infos =  DbUtil.selectOne(Config.BSAECOMMENT,selectSQL);
		if(infos.get("COUNT(1)").toString().equals("1")) return true;
		else return false;
	}
}
