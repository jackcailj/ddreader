package com.dangdang.db.digital;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dangdang.config.Config;
import com.dangdang.db.SqlUtil;
import com.dangdang.db.authority.MediaMonthlyAuthorityDb;
import com.dangdang.db.comment.CommentTargetCountDb;
import com.dangdang.ddframework.core.ConfigCore;
import com.dangdang.ddframework.core.TestEnvironment;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.Channel;
import com.dangdang.digital.meta.ChannelOwner;
import com.dangdang.readerV5.reponse.BookList;
import com.dangdang.readerV5.reponse.ChannelBookList;

/**
 * 
 * @author guohaiying
 *
 */
public class ChannelDb {
	//根据custId获取频道id  如果没有返回null
	//Channel.java used
	public static String getChannelIdByCustId(String custId) throws Exception{
    	String selectSQL = "SELECT channel_id FROM channel " +
    			"WHERE is_completed=1 AND shelf_status=1 AND cust_id="+custId;
    	List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
    	if(infos.size()==0)return null;
    	else
    		return infos.get(0).get("channel_id").toString();
	}
	
	//根据channelId获取custId
	public static String getCustIdByChannelId(String channelId) throws Exception{
    	String selectSQL = "SELECT cust_id FROM channel WHERE channel_id="+channelId;
    	List<Channel> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL, Channel.class);	
        return infos.get(0).getCustId().toString();
	}
	
    //获取频道信息
    //Channel.java used
    public static Channel getChannel(String channelId) throws Exception { 
    	String selectSQL = "SELECT * FROM `channel` WHERE channel_id="+channelId;
    	List<Channel> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL, Channel.class); 
    	Channel channel=null;
    	try{
    		channel= infos.get(0);
    	}catch(Exception e){
    		System.out.println("在表channel中未搜索到频道： "+channelId);
    	}
		return channel;
    }
	
	//获取某栏目下的频道列表
	//ChannelColumn接口 wiki used
	public static List<Map<String,Object>> getChannelList(String columnCode) throws Exception{
		String selectSQL = "SELECT * " +
				"FROM channel " +
				"WHERE shelf_status=1 AND is_completed=1 AND channel_id IN (" +
				"SELECT sale_id " +
				"FROM `media_column_content` " +
				"WHERE column_code='"+columnCode+"'AND NOW()>=start_date AND NOW()<=end_date AND `status` IN (1,2))";			
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		return infos;
	}	
	
	//获取企业/个人的频道  企业type=1  个人type=2
	//GetChannelIdParse.java used
	public static String getChannelWithOwnerType(String type) throws Exception{
		String selectSQL = "SELECT c.channel_id " +
				"FROM channel c, channel_owner co " +
				"WHERE c.owner_id= co.owner_id " +
				"AND c.shelf_status=1 " +
				"AND c.is_completed=1 " +
				"AND co.type="+type;
		 List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig,selectSQL);
		 return infos.get(SqlUtil.getRandNum(infos)).get("channel_id").toString();		
	}
	
	//获取支持包月，未包月的频道
	//GetChannelIdParse.java used
	public static String getMonthlyChannel(String custID) throws Exception{
		//用户已经包月的频道列表
		List userMonthlyChannelList = MediaMonthlyAuthorityDb.getUserMonthlyChannelID(custID, "1");
		String selectSQL = "";
		if(userMonthlyChannelList==null){
			selectSQL = "SELECT * FROM `channel` " +
				"WHERE shelf_status=1 AND is_completed=1 AND is_allow_monthly=1 LIMIT 1";
		}
		selectSQL = "SELECT channel_id FROM `channel` " +
				"WHERE shelf_status=1 " +
				"AND is_completed=1 " +
				"AND is_allow_monthly=1 " +
				"AND channel_id NOT IN "+ SqlUtil.getListToString(userMonthlyChannelList);
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		return infos.get(SqlUtil.getRandNum(infos)).get("channel_id").toString();
	}
	
	//获取下线的频道
	//UserDevice.java used
	//ChannelMonthlyStrategyDb.java used
	public static List<String> getOfflineChannelList() throws Exception{
		String selectSQL = "SELECT channel_id" +
				"FROM `channel` " +
				"WHERE shelf_status=0 " +
				"AND is_completed=1";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		List<String> list = new ArrayList<String>();
		for(int i=0; i<infos.size(); i++){
			list.add(infos.get(i).get("channel_id").toString());
		}
		return list;
	}
	
	//获取有文章频道
	public static String getChannelHasArticles() throws Exception{
		String selectSQL = "SELECT c.channel_id " +
				"FROM channel c,media_column_content mcc, channel_articles ca " +
				"WHERE c.channel_id = mcc.sale_id " +
				"AND c.channel_id = ca.channel_id " +
				"AND c.shelf_status=1 " +
				"AND c.is_completed=1 " +
				"AND mcc.`status` IN (1,2) " +
				"AND NOW() BETWEEN start_date AND end_date";
		 List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig,selectSQL);
		 return infos.get(SqlUtil.getRandNum(infos)).get("channel_id").toString();		
	}
	
	//获取无文章频道
	public static String getChannelNoHasArticles() throws Exception{
		String selectSQL = "SELECT c.channel_id " +
				"FROM channel c,media_column_content mcc " +
				"WHERE c.channel_id = mcc.sale_id " +
				"AND c.shelf_status=1 " +
				"AND c.is_completed=1 " +
				"AND mcc.`status` IN (1,2)" +
				"AND NOW() BETWEEN start_date AND end_date " +
				"AND c.channel_id NOT IN(SELECT DISTINCT channel_id " +
				"FROM channel_articles " +
				"WHERE channel_id IS NOT NULL)";
		 List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig,selectSQL);
		 return infos.get(SqlUtil.getRandNum(infos)).get("channel_id").toString();	
	}
		
	//获取用户无包月权限的频道
	
	//获取用户已订阅/未订阅的频道
	public static String getChannelIsSub(String custId) throws Exception{
		String selectSQL = "SELECT c.channel_id "+
				" FROM channel c,media_column_content mcc"+
				" WHERE c.channel_id = mcc.sale_id "+
				" AND c.shelf_status=1 "+
				" AND c.is_completed=1 "+
				" AND mcc.`status` IN (1,2) "+
				" AND NOW() BETWEEN start_date AND end_date "+
				" AND c.channel_id NOT IN(SELECT channel_id "+
				" FROM channel_sub_user "+
				" WHERE type=1 "+
				" AND cust_id="+custId+")";
		 List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig,selectSQL);
		 return infos.get(SqlUtil.getRandNum(infos)).get("channel_id").toString();		
	}
	
    //获取书的基本信息
    public static BookList getBookMessage(int booklist_id ) throws Exception {
        String selectSQL = "SELECT book_num,booklist_id,change_num,channel_id,creator,description,image_url," +
		"is_show,modifier,name, owner,status,store_num " +
		"FROM `media_booklist`" +
		" WHERE booklist_id="+booklist_id;      
        List<BookList> bookList = DbUtil.selectList(Config.YCDBConfig,selectSQL,BookList.class);
        if(bookList.size()==0) 
        	return null;
        else
        	return bookList.get(0);
    }


    
    //用户是否订阅
//    public static int getUserChannelSub(int custID, int channelID) throws Exception{
//    	String selectSQL="SELECT count(*) FROM channel_sub_user WHERE type=1 AND cust_id="+custID+" AND channel_id=" + channelID;
//		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
//		int count = Integer.valueOf(infos.get(0).get("count(*)").toString());	
//    	return count;
//    }
       
    //获取频道下是否有文章
    public static int getArticlesCount(String channelID) throws Exception{
    	Long channelId = Long.valueOf(channelID);
    	String selectSQL = "SELECT count(*) FROM `channel_articles` WHERE channel_id=" + channelId;
    	List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
    	return Integer.valueOf(infos.get(0).get("count(*)").toString());  	
    }
    
    //获取owner
    public static Map<String, Object> getOwner(int ownerID) throws Exception{
    	String selectSQL = "SELECT type, name, company FROM `channel_owner` WHERE owner_id="+ownerID;
    	List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
    	return infos.get(0);
    }


	/*
	获取ChannelOwner信息
	 */
	public static ChannelOwner getChannelOwner(String custId) throws Exception{
		String selectSQL = "select * from channel_owner where cust_id="+custId;
		ChannelOwner channelOwner = DbUtil.selectOne(Config.YCDBConfig, selectSQL, ChannelOwner.class);
		return channelOwner;
	}
    
    //获取频道下书单id
    public static ChannelBookList getBookIDList(String channelID) throws Exception {   
    	Long channelId = Long.valueOf(channelID);
    	String selectSQL = "SELECT booklist_id FROM media_booklist WHERE channel_id="+channelId;
    	List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
    	ChannelBookList bookList = new ChannelBookList();
    	if(infos.size()==0)
    		bookList=null;
    	else 
    		bookList.setBooklistId(Integer.valueOf(infos.get(0).get("booklist_id").toString()));  	
    	
    	return bookList;
    }
    
	//上下架频道 shelf_statue:1上架   0：下架
	public static String setStatusWithChannelAndStatus(String channelId, String status) throws Exception{
		Long _channelId = Long.valueOf(channelId);
		int _status = Integer.valueOf(status);
		String updateSQL="UPDATE channel SET shelf_status="+_status+", apply_times = apply_times+1 WHERE channel_id=" +_channelId;
		DbUtil.executeUpdate(Config.YCDBConfig, updateSQL);
		RefreshCache.refresh();
		return channelId;
	}
	
	//随机获取频道下的一本书mediaId
	public String getMediaId(String channelId) throws Exception{
		Long _channelId = Long.valueOf(channelId);
		String selectSQL = "SELECT media_id FROM `media_booklist` mb, media_booklist_detail mbd , channel c " +
				" WHERE c.channel_id=mb.channel_id " +
				" AND mb.booklist_id=mbd.booklist_id " +
				" AND on_shelf_status=1 " +
				" AND c.channel_id="+_channelId +
				" AND mbd.media_id IS NOT NULL" +
				" ORDER BY RAND() " +
				" LIMIT 1";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		return infos.get(0).get("media_id").toString();
	}
	
	//Recommendarticle.java used
	public static List<Channel> getRelatedChannel(String channelId, int num) throws Exception{
		List<String> list = CommentTargetCountDb.getRelatedChannel(channelId);
		if(list.size()==0) return null;
		String selectSQL = "SELECT * FROM `channel` " +
				" WHERE  shelf_status=1 " +
				" AND channel_id IN "+SqlUtil.getListToString(list)+
				" ORDER BY sub_number DESC LIMIT "+num;
		List<Channel> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL, Channel.class);
		return infos;
	}
	
	//获取有标签的频道
	public static String getHaveTagChannel() throws Exception{
		String selectSQL = 	"SELECT channel_id FROM channel " +
		"WHERE shelf_status=1 " +
		"AND channel_id IN" + SqlUtil.getListToString(CommentTargetCountDb.getHaveTagChannels("4000"));	
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		return infos.get(SqlUtil.getRandNum(infos)).get("channel_id").toString();
	}
	
	//获取没有表签的频道
	public static String getHaveNotTagChannel() throws Exception{
		String selectSQL = "SELECT channel_id FROM channel " +
				"WHERE shelf_status=1 " +
				"AND channel_id NOT IN" + SqlUtil.getListToString(CommentTargetCountDb.getHaveTagChannels("4000"));
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);
		return infos.get(SqlUtil.getRandNum(infos)).get("channel_id").toString();
	}
    
    public static void main(String[] args) throws Exception{
    	Channel s = ChannelDb.getChannel("1169");
    	System.out.println(s);
    }
	
}
