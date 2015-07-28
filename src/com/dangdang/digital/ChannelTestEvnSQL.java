package com.dangdang.digital;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

public class ChannelTestEvnSQL extends ChannelSQL{
	
	/**
	 * 创建频道
	 * @param name 在哪个栏目下创建频道栏目
	 * @param code 频道栏目code
	 * @return 返回column_code值
	 */
	public static String createWithNameAndCode(String name, String code) throws Exception{		
		return BookStoreTestEvnSQL.createWithNameAndCodeAndType(name, code, 1);
	}	
	
	//往频道栏目中添加频道	
	public static void insertChannelWithColumncodeAndSize(String columncode, int size) throws Exception{
		String selectSQL;
		selectSQL = "SELECT column_id FROM `media_column` WHERE column_code='"+columncode+"'";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		int columnID = Integer.valueOf(infos.get(0).get("column_id").toString());	
		
		selectSQL="SELECT channel_id, title " +
		"FROM channel " +
		"WHERE shelf_status=1 " +
		"LIMIT "+ size;
		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);		
		String insertSQL = "INSERT INTO media_column_content(column_code, column_id, sale_id, sale_name, creation_date, start_date, end_date, status, order_value) values";
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i=0; i<result.size(); i++){
			Map<String, Object> temp = result.get(i);
			int sale_id = Integer.valueOf(temp.get("channel_id").toString());
			String name = temp.get("title").toString();
			if(name.contains("'"))
				name = name.replace("'","''");
			list.add(sale_id);
			insertSQL += "('"+ columncode +"',"+columnID+", "+ sale_id +", '"+ name +"',now(),now(),DATE_ADD(now(),INTERVAL 1 YEAR)"+", 2, "+i+"+1),";
		}
		DbUtil.executeUpdate(Config.YCDBConfig, insertSQL.substring(0, insertSQL.lastIndexOf(",")));
		BookStoreTestEvnSQL.refreshCache();
	}
	
	//往频道书单中添加出版物的书
	
	//往频道书单中添加原创的书
	
	//往频道书单中添加纸质书
	
}
