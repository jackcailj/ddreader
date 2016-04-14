package com.dangdang.db.digital;

import java.util.List;
import java.util.Map;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

public class MediaCatetoryDb {
	public static String getBlock(String code) throws Exception{
		String selectSQL = "SELECT * FROM media_catetory WHERE `parent_id` IN (649)AND `status`=1 ORDER BY index_order DESC;";
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		return infos.get(0).get("content").toString();
	}
	
	

}
