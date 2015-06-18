package com.dangdang.readerV5.discovery;

import java.util.Date;
import java.util.Map;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

/**
 * 作者列表页    action：getDigestListByAuthor
 * @author wuhaiyan 
 */
public class GetDigestListByAuthor extends GetDigestListBySign{
	String authorId;
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		parseParameters(params);
		Date date = new Date();
		String sql = "SELECT author_id FROM `media_digest_author` ORDER BY rand() limit 1";
		authorId = DbUtil.selectOne(Config.YCDBConfig, sql).get("author_id").toString();
		if(paramMap.get("authorId")!=null&&paramMap.get("authorId").equals("FromDB")){
			paramMap.put("authorId", authorId);
		}
		if(paramMap.get("createTime")!=null&&paramMap.get("createTime").equals("currentTime")){
			paramMap.put("createTime", ((Long)date.getTime()).toString());
		}
	
		digestSql = "select id from media_digest where author like '%"+authorId+"%' and is_show=1";
	}
	
	//media/api2.go?pageSize=15&action=getDigestListByAuthor&authorId=72851&returnType=json&deviceType=FP_Android&channelId=60000&clientVersionNo=1.0.1&serverVersionNo=1.0.0&permanentId=20150522070611523664833360843852483&deviceSerialNo=864338010887072&macAddr=30%3Af3%3A1d%3A5c%3A96%3Abb&resolution=720*1280&clientOs=4.2.1&platformSource=FP-P&channelType=all&token=2419d8b072cc93c1516050807acdd39a

}
