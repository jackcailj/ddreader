package com.dangdang.db.digital;

import java.util.List;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.MediaSpecialTopic;

public class MediaSpecialTopicDb {
	
	public static MediaSpecialTopic getSpecialTop(String st_id) throws Exception{
		String selectSQL = "SELECT * FROM `media_special_topic` WHERE st_id="+st_id+"";
		List<MediaSpecialTopic> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL, MediaSpecialTopic.class);	
		if(infos.size()==0) return null;
		else return infos.get(0);
	}

}
