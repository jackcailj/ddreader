package com.dangdang.digital;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.MediaList;
import com.dangdang.readerV5.reponse.BookList;
import com.dangdang.readerV5.reponse.ChannelList;
import com.dangdang.readerV5.reponse.ChannelResponse;
import com.dangdang.readerV5.reponse.ColumnReponse;

public class ChannelSQL {
	
	//获取频道栏目基本信息
    public static ColumnReponse getChannelColumn(String columnCode) throws Exception {          
    	String selectSQL = "SELECT name,is_show_horn, tips FROM media_column WHERE column_code='"+columnCode+"'";
    	List<ColumnReponse> list = new ArrayList<ColumnReponse>();
    	list = DbUtil.selectList(Config.YCDBConfig,selectSQL,ColumnReponse.class);
    	ColumnReponse column = new ColumnReponse();
    	column = list.get(0);
    	column.setIsShowHorn(column.getIs_show_horn());
    	column.setCount(getChannelList(columnCode).size());
    	column.setTotal(getTotal(columnCode));
        
        return column;
    }
    
	//获取频道栏目下频道列表
    public static List<ChannelList> getChannelList(String columnCode) throws Exception {
       
    	String selectSQL = "SELECT a.sale_id, a.sale_name, b.description,b.icon,b.sub_number " +
    			"FROM media_column_content a , channel b WHERE a.sale_id=b.channel_id " +
    			"AND b.shelf_status=1 " +
    			"AND column_code='"+columnCode+"'";
        List<ChannelList> channelList = new ArrayList<ChannelList>();
        channelList = DbUtil.selectList(Config.YCDBConfig,selectSQL,ChannelList.class);
        for(int i=0; i<channelList.size(); i++){
        	ChannelList tmp = channelList.get(i);
        	tmp.setChannelId(tmp.getSale_id());
        	tmp.setTitle(tmp.getSale_name());
        	tmp.setSubNumber(tmp.getSub_number());
        }
        return channelList;
    }
    
    
    //获取书的基本信息
    public static BookList getBookMessage(int booklist_id ) throws Exception {
        String selectSQL = "SELECT * FROM media_booklist WHERE booklist_id="+ booklist_id;
       // return DbUtil.selectList(Config.YCDBConfig,selectSQL,BookList.class);
        return null;
    }
    
    //获取书单书列表
    public static List<MediaList> getMediaList(int channel, int booklist_id ) throws Exception {
        String selectSQL = "";

        return DbUtil.selectList(Config.YCDBConfig,selectSQL,BookList.class);
    }
    
    //获取订阅数
    public static int getChannelSub(int channelID) throws Exception{
    	String selectSQL="SELECT count(*) FROM channel_sub_user WHERE channel_id=" + channelID;
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
    
    //获取频道基本信息
    public static ChannelResponse getChannelMessage(int channelID) throws Exception {       
    	String selectSQL = "SELECT channel_id, description, icon, owner_id, sub_number, title " +
    			"FROM `channel` " +
    			"WHERE channel_id="+channelID+" AND shelf_status=1";
    	List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
        ChannelResponse response = new ChannelResponse();
        response.setChannelId(Integer.valueOf(infos.get(0).get("channel_id").toString()));
        response.setDescription(infos.get(0).get("description").toString());
        response.setIcon(infos.get(0).get("icon").toString());
        //设置owner
        int owner_id = Integer.valueOf(infos.get(0).get("icon").toString());
        Map<String, Object> m = getOwner(owner_id);
        int type = Integer.valueOf(m.get("type").toString());
        if(type==1)
        	response.setOwnder(m.get("company").toString());
        else
        	response.setOwnder(m.get("name").toString());
        response.setSubNumber(Integer.valueOf(infos.get(0).get("sub_number").toString()));
        response.setTitle(infos.get(0).get("title").toString());
        return response;
        //设置是否订阅
        //response.setIsSub();
    }
    //获取owner
    public static Map<String, Object> getOwner(int ownerID) throws Exception{
    	String selectSQL = "SELECT type, name, company FROM `channel_owner` WHERE owner_id="+ownerID;
    	List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
    	return infos.get(0);
    }
    
    //获取频道下书单id
    public static List<Integer> getBookIDList(int channel) throws Exception {       
    	String selectSQL = "SELECT booklist_id FROM media_booklist WHERE channel_id="+channel;
    	List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
        List<Integer> list = new ArrayList<Integer>();
        for(int i=0; i<infos.size(); i++){
        	list.add(Integer.valueOf(infos.get(i).get("booklist_id").toString()));
        }
        return list;
    }
    
    public static void main(String[] args) throws Exception{
//    	List<BookList> list = new ArrayList<BookList>();
//    	list = ChannelSQL.getBookList(63);
//    	System.out.println(list.size());
//    	BookList b = list.get(0);
//    	System.out.println(b.getBooklist_id());
    }

}
