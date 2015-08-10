package com.dangdang.digital.channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.readerV5.reponse.ChannelMediaList;

/**
 * 
 * @author guohaiying
 *
 */
public class BookListDetailSQL {
	
	public static String getBookListIdWithType(String type) throws Exception{
		String selectSQL = "SELECT DISTINCT booklist_id " +
				"FROM `media_booklist_detail` " +
				"WHERE on_shelf_status=1 " +
				"AND  type IN ("+Integer.valueOf(type)+") " +
				"ORDER BY RAND() " +
				"LIMIT 1";
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		return infos.get(0).get("booklist_id").toString();
	}

    
    //获取书单书列表
    public static List<ChannelMediaList> getMediaList(int booklist_id, int size) throws Exception {
        String selectSQL = "SELECT author_penname, cover_pic, descs, media_id, recommand_words, sale_id, title " +
        		"FROM media " +
        		"WHERE sale_id IN (" +
        		"SELECT sale_id FROM media_booklist_detail " +
        		"WHERE on_shelf_status=1 " +
        		"AND booklist_id="+ booklist_id +
        		" ORDER BY indexnum) LIMIT "+size;
        List<Map<String, Object>> list = DbUtil.selectList(Config.YCDBConfig,selectSQL);
        ChannelMediaList tmp = new ChannelMediaList();
        List<ChannelMediaList> channelList = new ArrayList<ChannelMediaList>();
        for(int i=0; i<list.size(); i++){
        	tmp.setAuthorPenname(list.get(i).get("author_penname").toString());
        	tmp.setChannelId(getChannelID(booklist_id));
        	if(list.get(i).get("descs")==null)
        		tmp.setDescs("");
        	else
        		tmp.setDescs(list.get(i).get("descs").toString());
        	//设置isStore
        	tmp.setIsStore("0");
        	tmp.setMediaId(list.get(i).get("media_id").toString());
        	//设置mediaType
        	tmp.setMediaType(getType(list.get(i).get("sale_id").toString()));
        	if(list.get(i).get("recommand_words")==null)
        		tmp.setRecommandWords("");
        	else
        		tmp.setRecommandWords(list.get(i).get("recommand_words").toString());
        	tmp.setSaleId(list.get(i).get("sale_id").toString());
        	tmp.setTitle(list.get(i).get("title").toString());
        	channelList.add(tmp);
        }
        return channelList;
    }
    
    //获取书的类型
    public static String getType(String mediaId) throws Exception{
    	int _mediaId = Integer.valueOf(mediaId);
    	String selectSQL = "SELECT type FROM `media_booklist_detail` WHERE sale_id="+_mediaId;
    	List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		return infos.get(0).get("type").toString();
    }
    
    //根据booklist_id获取channelId
    public static String getChannelID(int booklist_id) throws Exception{
    	String selectSQL="SELECT channel_id FROM `media_booklist` WHERE booklist_id=" + booklist_id;
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		return infos.get(0).get("channel_id").toString();	
    }
    
    //获取书单数列表总量
    public static String getMediaListCount(int booklistId) throws Exception{
        String selectSQL = "SELECT count(1) " +
        		"FROM `media_booklist_detail` " +
        		"WHERE on_shelf_status=1 " +
        		"AND  booklist_id="+booklistId;
        List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		return infos.get(0).get("count(1)").toString();
    }
    
}
