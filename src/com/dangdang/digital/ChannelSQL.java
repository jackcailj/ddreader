package com.dangdang.digital;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.readerV5.reponse.BookList;
import com.dangdang.readerV5.reponse.Channel;
import com.dangdang.readerV5.reponse.ChannelBookList;
import com.dangdang.readerV5.reponse.ChannelList;
import com.dangdang.readerV5.reponse.ChannelMediaList;
import com.dangdang.readerV5.reponse.ChannelResponse;
import com.dangdang.readerV5.reponse.ColumnReponse;

public class ChannelSQL {
	
	//获取频道栏目基本信息
    public static ColumnReponse getChannelColumn(String columnCode) throws Exception {          
    	String selectSQL = "SELECT column_code, is_show_horn, name, tips FROM media_column WHERE column_code='"+columnCode+"'";
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		Map<String, Object> map = infos.get(0);		
    	ColumnReponse column = new ColumnReponse();
    	column.setChannelList(getChannelList(columnCode));
    	column.setColumnCode(map.get("column_code").toString());
    	column.setCount(getChannelList(columnCode).size());
    	column.setIsShowHorn(map.get("is_show_horn").toString());   	
    	column.setName(map.get("name").toString());
    	column.setTips(map.get("tips").toString());
    	column.setTotal(getTotal(columnCode));      
        return column;
    }
    
	//获取频道栏目下频道列表
    public static List<ChannelList> getChannelList(String columnCode) throws Exception {
       
    	String selectSQL = "SELECT a.sale_id, b.title, b.description,b.icon,b.sub_number " +
    			"FROM media_column_content a , channel b, media_booklist c" +
    			" WHERE a.sale_id=b.channel_id " +
    			"AND b.channel_id = c.channel_id " +
    			"AND b.shelf_status=1 " +
    			"AND column_code='"+columnCode+"'";      
        List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
        List<ChannelList> channelList = new ArrayList<ChannelList>();
        for(int i=0; i<infos.size(); i++){
        	ChannelList tmp = new ChannelList();
        	tmp.setChannelId(Integer.valueOf(infos.get(i).get("sale_id").toString()));
        	tmp.setDescription(infos.get(i).get("description").toString());
        	tmp.setIcon(infos.get(i).get("icon").toString());
        	tmp.setSubNumber(Integer.valueOf(infos.get(i).get("sub_number").toString()));
        	tmp.setTitle(infos.get(i).get("title").toString());       	
        	channelList.add(tmp);
        }
        return channelList;
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
    
    //获取书单书列表
    public static List<ChannelMediaList> getMediaList(int booklist_id, int size) throws Exception {
        String selectSQL = "SELECT author_penname, cover_pic, descs, media_id, recommand_words, sale_id, title " +
        		"FROM media " +
        		"WHERE sale_id IN (" +
        		"SELECT sale_id FROM media_booklist_detail " +
        		"WHERE booklist_id="+ booklist_id +
        		" ORDER BY indexnum) LIMIT "+size;
        List<Map<String, Object>> list = DbUtil.selectList(Config.YCDBConfig,selectSQL);
        String channelID = getChannelID(booklist_id);
        ChannelMediaList tmp = new ChannelMediaList();
        List<ChannelMediaList> channelList = new ArrayList<ChannelMediaList>();
        for(int i=0; i<list.size(); i++){
        	tmp.setAuthorPenname(list.get(i).get("author_penname").toString());
        	//设置channelId
        	tmp.setChannelId((channelID));
        	//？有问题
        	tmp.setCoverPic(Config.getUrl()+"/pic/"+list.get(i).get("cover_pic").toString());
        	tmp.setDescs(list.get(i).get("descs").toString());
        	//设置isStore
        	tmp.setIsStore("0");
        	tmp.setMediaId(list.get(i).get("media_id").toString());
        	//设置mediaType
        	tmp.setMediaType("1");
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
    
    //获取书单数列表总量
    public static String getMediaListCount(int booklist_id) throws Exception{
        String selectSQL = "SELECT count(*) FROM `media_booklist_detail` WHERE booklist_id="+booklist_id;
        List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		return infos.get(0).get("count(*)").toString();
    }
    
    //根据booklist_id获取channelId
    public static String getChannelID(int booklist_id) throws Exception{
    	String selectSQL="SELECT channel_id FROM `media_booklist` WHERE booklist_id=" + booklist_id;
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		return infos.get(0).get("channel_id").toString();	
    }
    
    //获取订阅数
    public static int getChannelSub(int channelID) throws Exception{
    	String selectSQL="SELECT count(*) FROM channel_sub_user WHERE type=1 AND channel_id=" + channelID;
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		int count = Integer.valueOf(infos.get(0).get("count(*)").toString());	
    	return count;
    }
    
    //用户是否订阅
    public static int getUserChannelSub(int custID, int channelID) throws Exception{
    	String selectSQL="SELECT count(*) FROM channel_sub_user WHERE type=1 AND cust_id="+custID+" AND channel_id=" + channelID;
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		int count = Integer.valueOf(infos.get(0).get("count(*)").toString());	
    	return count;
    }
    
    //获取某个频道栏目下频道总数量（没有书单的频道不计数）
    public static int getTotal(String columnCode) throws Exception{
    	String selectSQL="SELECT count(*) FROM media_booklist WHERE channel_id IN " +
    			"(SELECT a.sale_id FROM media_column_content a, channel b " +
    			"WHERE a.sale_id=b.channel_id " +
    			"AND a.column_code='"+columnCode+"'" +
    			"AND b.shelf_status=1)";
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		int count = Integer.valueOf(infos.get(0).get("count(*)").toString());	
    	return count;
    }
    
    //获取频道信息
    public static ChannelResponse getChannel(int channelID) throws Exception {       
    	String selectSQL = "SELECT channel_id, description, icon, owner_id, sub_number, title " +
    			"FROM `channel` " +
    			"WHERE channel_id="+channelID+" AND shelf_status=1";
    	List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
        ChannelResponse response = new ChannelResponse();
        //设置channel
        Channel channel = new Channel();
        channel.setBookList(getBookIDList(channelID));
       // channel.setChannel(channel);
        channel.setChannelId(infos.get(0).get("channel_id").toString());
        channel.setDescription(infos.get(0).get("description").toString());
        //设置hasArtical
        int hasArtical = getArticalCount(channelID);
        if(hasArtical>=1)
        	channel.setHasArtical("1");
        else channel.setHasArtical("0");
        
        channel.setIcon(infos.get(0).get("icon").toString());
        //设置isSub 是否订阅
        channel.setIsSub("0");
        //设置owner
        int owner_id = Integer.valueOf(infos.get(0).get("owner_id").toString());
        Map<String, Object> m = getOwner(owner_id);
        int type = Integer.valueOf(m.get("type").toString());
        if(type==1)
        	channel.setOwnder(m.get("company").toString());
        else
        	channel.setOwnder(m.get("name").toString());
        channel.setSubNumber(infos.get(0).get("sub_number").toString());
        channel.setTitle(infos.get(0).get("title").toString());
        response.setChannel(channel);
        return response;
    }
    //获取频道下是否有文章
    public static int getArticalCount(int channelID) throws Exception{
    	String selectSQL = "SELECT count(*) FROM `media_booklist` WHERE channel_id=" + channelID;
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
    public static ChannelBookList getBookIDList(int channelID) throws Exception {       
    	String selectSQL = "SELECT booklist_id FROM media_booklist WHERE channel_id="+channelID;
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
    	List<ChannelMediaList> list = ChannelSQL.getMediaList(14,10);
   	   System.out.println(list.get(0).getSaleId());
//    	System.out.println(list.size());
//    	BookList b = list.get(0);
//    	System.out.println(b.getBooklist_id());
    }

}
