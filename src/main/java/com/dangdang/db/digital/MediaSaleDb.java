package com.dangdang.db.digital;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dangdang.config.Config;
import com.dangdang.db.SqlUtil;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.MediaSale;

/**
 * @author guohaiying
 */
public class MediaSaleDb {
	
	//根据saleId获取MediaSale
	public static MediaSale getMediaSale(String saleId) throws Exception{
		String selectSQL = "SELECT * FROM `media_sale` WHERE shelf_status=1 AND sale_id="+ saleId;
		List<MediaSale> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL, MediaSale.class);
		if(infos.size()==0) return null;
		else return infos.get(0);
	}
	
	//获取xx栏目下书
	public static List<String> getMediaIds(String columnCode) throws Exception{
		String selectSQL = "SELECT sale_id " +
				"FROM media_sale " +
				"WHERE shelf_status=1 " +
				"AND sale_id IN"+SqlUtil.getListToString(MediaColumnContentDb.getColumnContent(columnCode));
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		if(infos.size()==0) return null;
		else{
			List<String> MediaIdList = new ArrayList<String>();
			for(int i=0; i<infos.size(); i++){
				MediaIdList.add(infos.get(i).get("sale_id").toString());
			}
			return MediaIdList;
		}
	}
	
	public static void main(String[] args){
		try {
			List<String> list = MediaSaleDb.getMediaIds("all_rec_xssf");
			System.out.println(list.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
