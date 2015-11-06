package com.dangdang.db.digital;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

/**
 * 
 * @author guohaiying
 *
 */
public class MediaSaleDb {

	
	//模拟后台，下架某本书
//	public static int setShelfStatusWithColumncode(String columncode) throws Exception{
//		int sale_id = getRandChannel(columncode);
//		String updateSQL = "UPDATE `media_sale` SET shelf_status=0 WHERE sale_id= "+ sale_id;
//		DbUtil.executeUpdate(Config.YCDBConfig, updateSQL);
//		RefreshCache.refresh();
//		return sale_id;
//	}
	
	//模拟后台，上架某本书
	public static int setShelfStatusWithSaleid(int saleid) throws Exception{
		String updateSQL = "UPDATE `media_sale` SET shelf_status=1 WHERE sale_id= "+ saleid;
		DbUtil.executeUpdate(Config.YCDBConfig, updateSQL);
		RefreshCache.refresh();
		return saleid;
	}
}
