package com.dangdang.db.digital;

import java.util.List;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.MediaListCategory;

public class MediaListCategoryDb {
	public static MediaListCategory get(String categoryCode) throws Exception{
		String selectSQL = "SELECT * FROM media_list_category where category_code='"+categoryCode+"'";
		List<MediaListCategory> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL, MediaListCategory.class);
		if(infos.size()==0)
			return null;
		else 
			return infos.get(0);
	}
	
	public static void main(String[] args) throws Exception{
		MediaListCategory mc = MediaListCategoryDb.get("all_ddds_sale");
		System.out.println(mc.getCategoryName());
	}

}
