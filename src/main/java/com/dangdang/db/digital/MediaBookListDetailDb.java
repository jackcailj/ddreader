package com.dangdang.db.digital;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.MediaBooklistDetail;

import java.util.List;

/**
 * @author guohaiying
 */
public class MediaBookListDetailDb {
	
	//根据channelId获取书单下的书
	public static List<MediaBooklistDetail> getMediaIdList(String channelId) throws Exception{
		String selectSQL="SELECT * FROM `media_booklist_detail` " +
				"WHERE booklist_id IN (" +
				"SELECT booklist_id " +
				"FROM media_booklist " +
				"WHERE channel_id IN ("+channelId+")) " +
				"AND on_shelf_status=1";
		List<MediaBooklistDetail> infos = DbUtil.selectList(Config.YCDBConfig,selectSQL,MediaBooklistDetail.class);
		return infos;
	}
	
	//根据bookList_id获取书单下的书
	public static List<MediaBooklistDetail> getMediaIdList(int bookListId) throws Exception{
		String selectSQL="SELECT * " +
				"FROM `media_booklist_detail` " +
				"WHERE booklist_id="+bookListId+" " +
						"AND on_shelf_status=1 " +
						"ORDER BY ABS(indexnum) DESC";
		List<MediaBooklistDetail> infos = DbUtil.selectList(Config.YCDBConfig,selectSQL, MediaBooklistDetail.class);		
		return infos;
	}
	
	public static List<MediaBooklistDetail> getMediaIdList(String bookListId, int type, int shelfStatus) throws Exception{
		String selectSQL = "SELECT * " +
				" FROM `media_booklist_detail` " +
				" where booklist_id= "+bookListId +
				" AND type= "+type +
				" AND on_shelf_status=" +shelfStatus;
		List<MediaBooklistDetail> infos = DbUtil.selectList(Config.YCDBConfig,selectSQL, MediaBooklistDetail.class);		
		return infos;
	}
	
	public static void main(String[] args){
		String s = null;
		try {
			List<MediaBooklistDetail> list = MediaBookListDetailDb.getMediaIdList("152",3,1);
			System.out.println(list.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
