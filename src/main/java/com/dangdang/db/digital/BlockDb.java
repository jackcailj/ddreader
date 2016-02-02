package com.dangdang.db.digital;

import java.util.List;
import java.util.Map;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

public class BlockDb {	
	public static String getBlock(String code) throws Exception{
		String selectSQL = "SELECT content FROM `media_block` WHERE `code` LIKE '"+code+"'";
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		return infos.get(0).get("content").toString();
	}
}
