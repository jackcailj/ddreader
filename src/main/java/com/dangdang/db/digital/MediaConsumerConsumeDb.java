package com.dangdang.db.digital;

import java.util.List;

import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.MediaConsumerConsume;

public class MediaConsumerConsumeDb {
	
	//根据custId获取最新一条消费记录
	public static MediaConsumerConsume getConsumeRecord(String custId) throws Exception{
		String selectSQL = "";
		List<MediaConsumerConsume> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL,MediaConsumerConsume.class);
		if(infos.size()==0) 
			return null;
		else
			return infos.get(0);
	}

}
