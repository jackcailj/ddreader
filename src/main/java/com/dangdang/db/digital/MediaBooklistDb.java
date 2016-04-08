package com.dangdang.db.digital;

import java.util.List;
import java.util.Map;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.MediaBooklist;

/**
 * @author guohaiying
 */
public class MediaBooklistDb {
	
    //随机获取一个有效的书单
    //BookList.java used
    public static String getRandChannel() throws Exception { 
    	String selectSQL = "SELECT booklist_id " +
    			"FROM `media_booklist_detail` " +
    			"WHERE booklist_id IN (SELECT booklist_id " +
    								"FROM `media_booklist` " +
    								"WHERE channel_id IN (SELECT channel_id " +
    											"FROM channel " +
    											"WHERE is_completed=1 " +
    											"AND shelf_status=1)) " +
    											"GROUP BY booklist_id " +
    											"HAVING booklist_id>0";
    	List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
    	int n=(int)Math.random()*(infos.size()-1);
        return infos.get(n).get("booklist_id").toString();
    }
		
    //获取某频道的书单信息
    //BookList.java used
    public static MediaBooklist getBookListMsg(String channelId) throws Exception { 
    	String selectSQL = "SELECT * FROM `media_booklist` WHERE channel_id="+channelId;
    	List<MediaBooklist> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL, MediaBooklist.class);	
        return infos.get(0);
    }
    
    //获取某书单的信息
    public static MediaBooklist getBookListMsg2(String bookListId) throws Exception { 
    	String selectSQL = "SELECT * FROM `media_booklist` WHERE booklist_id="+bookListId;
    	List<MediaBooklist> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL, MediaBooklist.class);	
        return infos.get(0);
    }

    //根据custId获取BookListId
    public static String getBookListId(String custId) throws Exception{
    	int _custId = Integer.valueOf(custId);
    	String selectSQL = "SELECT booklist_id FROM `media_booklist` WHERE `owner`=" +_custId;
    	List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
    	return infos.get(0).get("booklist_id").toString();
    }




}
