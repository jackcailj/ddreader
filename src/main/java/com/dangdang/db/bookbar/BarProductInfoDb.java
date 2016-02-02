package com.dangdang.db.bookbar;

import java.util.List;

import com.dangdang.bookbar.meta.BarProductInfo;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

/**
 * @author guohaiying
 */
public class BarProductInfoDb {
	
	//获取纸书信息
	public static BarProductInfo getPaperBookMsg(long productId) throws Exception{
		String selectSQL = "SELECT * FROM `bar_product_info` WHERE product_id=" +productId;
		List<BarProductInfo> infos = DbUtil.selectList(Config.BOOKBARDBConfig, selectSQL, BarProductInfo.class);
		return infos.get(0);
	}
	
	//获取有效/下架的纸书 0:正常  6：下架
	public static List<BarProductInfo> getPaperBookList(int shelfStatus) throws Exception{
		String selectSQL = "SELECT product_id FROM `bar_product_info` WHERE paper_book_status=" +shelfStatus + " LIMIT 100";
		List<BarProductInfo> infos = DbUtil.selectList(Config.BOOKBARDBConfig, selectSQL, BarProductInfo.class);
		return infos;	
	}
	

}
