package com.dangdang.readerV5.discovery;

import java.util.Date;
import java.util.Map;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

/**
 * 心情列表页    action：getDigestListByMood
 * @author wuhaiyan 
 */
public class GetDigestListByMood extends GetDigestListBySign{
	String mood;
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		parseParameters(params);
		Date date = new Date();
		String sql = "SELECT mood FROM `media_digest` where mood is not null ORDER BY rand() limit 1";
		mood = DbUtil.selectOne(Config.YCDBConfig, sql).get("mood").toString();
		if(paramMap.get("mood")!=null&&paramMap.get("mood").equals("FromDB")){
			paramMap.put("mood", mood);
		}
		if(paramMap.get("createTime")!=null&&paramMap.get("createTime").equals("currentTime")){
			paramMap.put("createTime", ((Long)date.getTime()).toString());
		}
	
		digestSql = "select id from media_digest where mood="+mood+" and is_show=1";
	}
}
