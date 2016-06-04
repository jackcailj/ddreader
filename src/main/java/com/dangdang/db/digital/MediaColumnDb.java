package com.dangdang.db.digital;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.MediaColumn;

/**
 * @author guohaiying
 */
public class MediaColumnDb {
	
	//随机返回一个频道
	//GetColumnCodeParse.java used
	public static String getChannelColumnCode() throws Exception{
		List<String> columnCodes = getColumnCode(1);
		String columnCode = "";
		for(int i=0; i<columnCodes.size(); i++){
			columnCode = columnCodes.get(i);
			String selectSQL = "SELECT count(1) FROM channel " +
					"WHERE is_completed=1 " +
					"AND shelf_status=1 " +
					"AND channel_id IN (SELECT channel_id " +
										"FROM `media_column_content` " +
										"WHERE column_code='"+columnCode+"') ";
			List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
			if(Integer.valueOf(infos.get(0).get("count(1)").toString())>0)
				break;
		}
		return columnCode;
	}
	
	//GetColumnCodeParse.java used
	public static List<String> getColumnCode(int type) throws Exception{
		List<String> list = new ArrayList<String>();
		String selectSQL = "SELECT column_code " +
				"FROM media_column " +
				"WHERE type="+type+" AND path LIKE 'ddds%' AND column_id NOT IN (" +
				"SELECT parent_id " +
				"FROM media_column " +
				"WHERE type="+type+") " +
				"LIMIT 1";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		for(int i=0;i<infos.size();i++){
			list.add(infos.get(i).get("column_code").toString());
		}
		return list;
	}

	//获取频道栏目基本信息
	//ChannelColumn.java used
    public static MediaColumn getMediaColumn(String columnCode) throws Exception {          
    	String selectSQL = "SELECT * FROM media_column WHERE column_code='"+columnCode+"'";
		List<MediaColumn> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL, MediaColumn.class);
		if(infos.size()==0) return null;
		else return infos.get(0);
    }
    
    public static String getRecordNum(String columnCode) throws Exception{
    	String selectSQL = "SELECT count(1) FROM `media_column` WHERE column_code='"+columnCode+"'";
    	Map<String, Object> infos = DbUtil.selectOne(Config.YCDBConfig, selectSQL);
    	return infos.get("count(1)").toString();
    	
    }
    

    

	public static void main(String[] args) throws Exception{
		//String s = MediaColumnDb.getColumnName("all_rec_pdzbtj");
		//System.out.println(s);
	}
}
