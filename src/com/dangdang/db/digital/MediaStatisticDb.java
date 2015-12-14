package com.dangdang.db.digital;

import java.util.List;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.Media;

public class MediaStatisticDb {
	
	//获取某本书对应的分类的畅销榜的书
	public static List<Media> get(String mediaId, String num) throws Exception{
		String selectSQL = "";
		List<Media> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL, Media.class);
		return infos;		
	}
	
	

}
