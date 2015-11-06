package com.dangdang.db.digital;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.readerV5.reponse.ChannelColumnReponse;

/**
 * 
 * @author guohaiying
 *
 */
public class MediaColumnDb {
	
	//随机返回一个频道
	//ChannelColumn接口 wiki used
	public static String getColumnCode(String type) throws Exception{
		int _type = Integer.valueOf(type);
		String selectSQL = "SELECT column_code " +
				"FROM media_column " +
				"WHERE type="+_type+" AND path LIKE 'ddds%' AND column_id NOT IN (" +
				"SELECT parent_id " +
				"FROM media_column " +
				"WHERE type="+_type+") " +
				"LIMIT 1";
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		return infos.get(0).get("column_code").toString();
	}

	//获取频道栏目基本信息
    public static ChannelColumnReponse getChannelColumn(String columnCode, int num) throws Exception {          
    	String selectSQL = "SELECT column_code, is_show_horn, name, tips FROM media_column WHERE column_code='"+columnCode+"'";
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		Map<String, Object> map = infos.get(0);		
		ChannelColumnReponse column = new ChannelColumnReponse();
    	column.setChannelList(MediaColumnContentDb.getChannelList(columnCode, num));
    	column.setColumnCode(map.get("column_code").toString());
    	column.setCount(MediaColumnContentDb.getChannelList(columnCode, num).size());
    	//column.setIsShowHorn(map.get("is_show_horn").toString());   	
    	column.setName(map.get("name").toString());
    	column.setTips(map.get("tips").toString());
    	column.setTotal(ChannelDb.getChannelList(columnCode).size());      
        return column;
    }
    
 
    
    /*+++++++++++++++++++++++++++++++++++++++++++++后台-》推荐列表+++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    
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
		RefreshCache.refresh();
	}
	
	//往频道书单中添加出版物的书
	
	//往频道书单中添加原创的书
	
	//往频道书单中添加纸质书
	
	//判断xx栏目是否为空
//	public static boolean isColumnNull(String columnCode) throws Exception{
//		String selectSQL = "SELECT sale_id,sale_name FROM `media_column_content` WHERE column_code='" +columnCode+"' AND `status` in (1,2) AND end_date> NOW()";
//		List<Map<String, Object>> result = DbUtil.selectList(Config.YCDBConfig, selectSQL);
//		int n = result.size();
//		if(n==0) return false;
//		else return true;
//	}



	
	//模拟后台，删除xx栏目
	public static void delColumnWithColumncode(String columncode) throws Exception{
		String delSQL = "DELETE FROM `media_column` WHERE column_code ='" +columncode+"'";
		DbUtil.executeUpdate(Config.YCDBConfig, delSQL);
		RefreshCache.refresh();
	}		

	public static void main(String[] args) throws Exception{
		String s = MediaColumnDb.getColumnCode("1");
		System.out.println(s);
	}
}
