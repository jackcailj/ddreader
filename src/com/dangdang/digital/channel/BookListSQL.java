package com.dangdang.digital.channel;

import java.util.List;
import java.util.Map;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.readerV5.reponse.ChannelBookList;

/**
 * 频道书单相关sql
 * @author guohaiying
 *
 */
public class BookListSQL {
	
	//随机获取书列表id
	public static String getOneBookList() throws Exception{
		String selectSQL = "SELECT booklist_id FROM `media_booklist` " +
				"WHERE is_show=1 " +
				"AND `status`=0 " +
				"AND book_num>0 " +
				"ORDER BY RAND() " +
				"LIMIT 1";
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		return infos.get(0).get("booklist_id").toString();
	}

	//获取书列表基本信息
	public static ChannelBookList getBookList(String bookListId) throws Exception{
		int _bookListId = Integer.valueOf(bookListId);
	   	String selectSQL="SELECT book_num,booklist_id,change_num,channel_id,creator,description,image_url," +
	   			"is_show,modifier,`name`,`owner`,`status`,store_num " +
	   			"FROM `media_booklist` " +
	   			"WHERE booklist_id=" + _bookListId;
	   	List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
	   	ChannelBookList bookList = new ChannelBookList();
	   	bookList.setBookNum(infos.get(0).get("book_num").toString());
	   	bookList.setBooklistId(infos.get(0).get("booklist_id").toString());
	   	bookList.setChangeNum(infos.get(0).get("change_num").toString());
	   	bookList.setChannelId(infos.get(0).get("channel_id").toString());
	   	bookList.setCreator(infos.get(0).get("creator").toString());
	   	bookList.setDescription(infos.get(0).get("description").toString());
	   	if(infos.get(0).get("image_url").equals(null)||infos.get(0).get("image_url")==null)
	   		bookList.setImageUrl(null);
	   	else
	   		bookList.setImageUrl(infos.get(0).get("image_url").toString());
	   	bookList.setIsShow(infos.get(0).get("is_show").toString());
	   	if(infos.get(0).get("modifier").equals(null)||infos.get(0).get("modifier")==null)
	   		bookList.setModifier(null);
	   	else
	   		bookList.setModifier(infos.get(0).get("modifier").toString());
	   	bookList.setName(infos.get(0).get("name").toString());
	   	bookList.setOwner(infos.get(0).get("owner").toString());
	   	bookList.setStatus(infos.get(0).get("status").toString());
	   	bookList.setStoreNum(infos.get(0).get("store_num").toString());
	    return bookList;
	}
	
	//获取书列表hasBoughtMonthly字段
	
}
