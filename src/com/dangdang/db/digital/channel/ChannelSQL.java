package com.dangdang.db.digital.channel;

import java.util.List;
import java.util.Map;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.readerV5.reponse.BookList;
import com.dangdang.readerV5.reponse.Channel;
import com.dangdang.readerV5.reponse.ChannelBookList;
import com.dangdang.readerV5.reponse.ChannelResponse;

/**
 * 频道详情页接口相关sql
 * @author guohaiying
 *
 */
public class ChannelSQL {
	
	//获取企业/个人的频道
	public static String getChannelWithOwnerType(String type) throws Exception{
		int _type = Integer.valueOf(type);
		String selectSQL = "SELECT c.channel_id " +
				"FROM channel c,media_column_content mcc, channel_owner co " +
				"WHERE c.channel_id = mcc.sale_id " +
				"AND c.owner_id= co.owner_id " +
				"AND c.shelf_status=1 " +
				"AND c.is_completed=1 " +
				"AND mcc.`status` IN (1,2) " +
				"AND NOW() BETWEEN start_date AND end_date " +
				"AND co.type=" +_type+
				" LIMIT 1";
		 List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig,selectSQL);
		 return infos.get(0).get("channel_id").toString();		
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
				"AND NOW() BETWEEN start_date AND end_date " +
				"LIMIT 1";
		 List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig,selectSQL);
		 return infos.get(0).get("channel_id").toString();		
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
				"WHERE channel_id IS NOT NULL) " +
				"ORDER BY RAND() " +
				"LIMIT 1";
		 List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig,selectSQL);
		 return infos.get(0).get("channel_id").toString();	
	}
	
	//获取可进行/不可进行包月的频道
	public static String getChannelWithIsAllowMonthly(String isAllowMonthly) throws Exception{
		int _isAllowMonthly = Integer.valueOf(isAllowMonthly);
		String selectSQL = "SELECT c.channel_id " +
				"FROM channel c,media_column_content mcc " +
				"WHERE c.channel_id = mcc.sale_id " +
				"AND c.shelf_status=1 " +
				"AND c.is_completed=1 " +
				"AND mcc.`status` IN (1,2) " +
				"AND NOW() BETWEEN start_date AND end_date " +
				"AND c.is_allow_monthly=" +_isAllowMonthly +
				" LIMIT 1";
		 List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig,selectSQL);
		 return infos.get(0).get("channel_id").toString();		
	}
	
	//获取用户没有包月权限的频道
//	public static String getChannelHasAuthority(String custId) throws Exception{
//		String selectSQL = "";
//	}
	
	//获取用户已订阅/未订阅的频道
	public static String getChannelIsSub(String custid) throws Exception{
		int _custId = Integer.valueOf(custid);
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
				" AND cust_id="+custid+")"+
				" ORDER BY RAND()"+
				" LIMIT 1";
		 List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig,selectSQL);
		 return infos.get(0).get("channel_id").toString();		
	}
	
    //获取书的基本信息
    public static BookList getBookMessage(int booklist_id ) throws Exception {
        String selectSQL = "SELECT book_num,booklist_id,change_num,channel_id,creator,description,image_url," +
		"is_show,modifier,name, owner,status,store_num " +
		"FROM `media_booklist`" +
		" WHERE booklist_id="+booklist_id;      
        List<BookList> bookList =  DbUtil.selectList(Config.YCDBConfig,selectSQL,BookList.class);
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
    
  
    //获取频道信息
    public static ChannelResponse getChannel(String custId, String channelID) throws Exception { 
    	Long _channelId = Long.valueOf(channelID);
    	String selectSQL = "SELECT channel_id, description, icon, owner_id, sub_number, title " +
    			"FROM `channel` " +
    			"WHERE channel_id="+_channelId+" AND shelf_status=1";
    	List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
    	ChannelResponse response = new ChannelResponse();
        //设置channel
        Channel channel = new Channel();
        String channelId = infos.get(0).get("channel_id").toString();
        channel.setBookList(getBookIDList(channelId));
        channel.setChannelId(channelId);
        //设置包月策略
        //channel.setChannelMonthlyStrategy(ChannelMonthlySQL.getChannel(channelId));
        channel.setDescription(infos.get(0).get("description").toString());
        //设置hasArtical
        int hasArtical = getArticlesCount(channelId);
        if(hasArtical>=1)
        	channel.setHasArtical("1");
        else channel.setHasArtical("0");
        //设置hasBoughtMonthly
        int hasBoughtMonthly = ChannelColumnSQL.hasBoughtMonthly(custId, channelId);
        channel.setHasBoughtMonthly(String.valueOf(hasBoughtMonthly));
        channel.setIcon(infos.get(0).get("icon").toString());
        //设置isAllowMonthly
        channel.setIsAllowMonthly(isAllowMonthly(channelId));
        //设置isSub 是否订阅
        String isSub = ChannelSubSQL.isSub(custId, channelId, "1");
        if(isSub.equals("1"))
        	channel.setIsSub("1");
        else 
        	channel.setIsSub("0");
        //设置monthlyType
        //channel.setMonthlyType("0");
        //设置owner
        int owner_id = Integer.valueOf(infos.get(0).get("owner_id").toString());
//        Map<String, Object> m = getOwner(owner_id);
//        int type = Integer.valueOf(m.get("type").toString());
//        if(type==1)
//        	channel.setOwnder(m.get("company").toString());
//        else
//        	channel.setOwnder(m.get("name").toString());
        //设置ownerType
        channel.setOwnerType(ChannelColumnSQL.getOwnerType(infos.get(0).get("owner_id").toString()));  
        channel.setSubNumber(infos.get(0).get("sub_number").toString());
        channel.setTitle(infos.get(0).get("title").toString());
        response.setChannel(channel);
        return response;
    }
    
    public static String isAllowMonthly(String channelID) throws Exception{
    	Long channelId = Long.valueOf(channelID);
    	String selectSQL = "SELECT is_allow_monthly FROM `channel` WHERE channel_id=" + channelId;
     	List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
    	return infos.get(0).get("is_allow_monthly").toString();  
    }
    
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
    
    //获取频道下书单id
    public static ChannelBookList getBookIDList(String channelID) throws Exception {   
    	Long channelId = Long.valueOf(channelID);
    	String selectSQL = "SELECT booklist_id FROM media_booklist WHERE channel_id="+channelId;
    	List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
    	ChannelBookList bookList = new ChannelBookList();
    	if(infos.size()==0)
    		bookList=null;
    	else 
    		bookList.setBooklistId(infos.get(0).get("booklist_id").toString());  	
    	
    	return bookList;
    }
    
    public static void main(String[] args) throws Exception{
//    	List<BookList> list = new ArrayList<BookList>();
    	//ChannelSQL.getChannelColumn("all_interface_test");
    	String s = ChannelSQL.getChannelIsSub("50098052");
   	   System.out.println(s);
//    	System.out.println(list.size());
//    	BookList b = list.get(0);
//    	System.out.println(b.getBooklist_id());
    }

}
