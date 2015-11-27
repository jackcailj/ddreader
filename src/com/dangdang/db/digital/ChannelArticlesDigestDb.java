package com.dangdang.db.digital;

import java.util.ArrayList;
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
	
	//获取用户的文章
	public static List<String> getUserChannelDigest(String custId) throws Exception{
		int _custId = Integer.valueOf(custId);
		String selectSQL ="SELECT digest_id " +
				"FROM `channel_articles_digest` " +
				"WHERE channel_id=(SELECT channel_id " +
							"FROM `channel` WHERE cust_id="+_custId+") " +
				"AND `status` IN (0,1,2) AND is_publish=1";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		List<String> digestIdList = new ArrayList<String>();
		for(int i=0; i<infos.size(); i++){
			digestIdList.add(infos.get(i).get("digest_id").toString());
		}
		return digestIdList;
	}
	
	//获取频道下的文章
	public static String getDigestIdByChannelId(String channelId) throws Exception{
		int _channelId = Integer.valueOf(channelId);
		String selectSQL = "SELECT digest_id FROM `channel_articles_digest` " +
				"WHERE channel_id="+_channelId+" AND is_publish=1 AND `status` IN (0,1)";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		int n=(int)Math.random()*(infos.size()-1);
		return infos.get(n).get("digest_id").toString();
	}
	
}
