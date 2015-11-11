package com.dangdang.db.digital;

import java.util.List;
import java.util.Map;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

/**
 * 
 * @author guohaiying
 *
 */
public class ChannelArticlesDigestDb {

	//获取频道文章id  3:频道; 5:攻略;
	public static String getChannelDigest(String type) throws Exception{
		int _type = Integer.valueOf(type);
		String selectSQL = "SELECT  digest_id " +
				"FROM `channel_articles_digest` " +
				"WHERE is_publish=1 " +
				"AND channel_id IS NOT NULL " +
				"AND `status` IN (0, 1, 2) " +
				"AND digest_id IN(SELECT id " +
					"FROM media_digest " +
					"WHERE type="+type+")";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		return null;
	}
	
}
