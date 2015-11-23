package com.dangdang.db.digital;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

/**
 * 
 * @author guohaiying
 *
 */
public class ChannelSubUserDb {
	
	//获取用户订阅的所有频道
	public static List<String> getUserAllSubChannel(String custId) throws Exception{
		String selectSQL = "SELECT channel.channel_id "+
			" FROM channel "+
			" WHERE channel.is_completed=1"+
			" AND channel.is_completed=1"+
			" AND channel.channel_id IN(" +
			" SELECT channel_id " +
			" FROM `channel_sub_user` " +
			" WHERE type=1 AND cust_id="+ custId +")";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		List<String> list = new ArrayList<String>();
		for(int i=0; i<infos.size(); i++){
			list.add(infos.get(i).get("channel_id").toString());
		}
		return list;
	}

	//获取用户订阅过的一个频道
	public static String getUserSubChannel(String custId) throws Exception{
		List<String> infos = getUserAllSubChannel(custId);
	 	int n = (int)Math.random()*(infos.size()-1);
	 	return infos.get(n);
	}
	
	//返回用户未订阅过的一个频道
	//ChannelSub接口  wiki used
	public static String getNotUserChannel(String custId) throws Exception{
	 	String selectSQL = "SELECT channel.channel_id "+
	 		" FROM media_column_content mcc LEFT JOIN channel ON mcc.sale_id= channel.channel_id"+
	 		" WHERE channel.shelf_status=1"+
	 		" AND channel.is_completed=1"+
	 		" AND  mcc.status in(1,2)"+
	 		" AND channel.channel_id NOT IN(" +
	 		" SELECT channel_id " +
	 		" FROM `channel_sub_user` " +
	 		" WHERE type=1 AND cust_id="+ custId +") LIMIT 1";
	 	List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
	 	return infos.get(0).get("channel_id").toString();
	}
	
	//查询用户是否订阅或取消订阅某频道 type=1订阅  type=0取消订阅
	//ChannelSub.java used
	public static String isSub(String custId, String channelId, int type) throws Exception{
		int _custId = Integer.valueOf(custId);
		int _channelId = Integer.valueOf(channelId);
	  	String selectSQL="SELECT count(1) " +
	  			"FROM channel_sub_user " +
	  			"WHERE type="+ type+" AND cust_id="+_custId+" AND channel_id=" + _channelId;
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		return infos.get(0).get("count(1)").toString();
	}
	
	
    //获取channel表中频道订阅总数
    public static int getChannelSub(String channelid) throws Exception{
    	int channelID = Integer.valueOf(channelid);
    	String selectSQL="SELECT sub_number FROM channel WHERE channel_id=" + channelID;
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		if(infos.get(0)!=null)
			return Integer.valueOf(infos.get(0).get("sub_number").toString());	
		else
			return -1;
    }   
    
    //获取channel_sub_user表中的频道订阅总数
    public static int getChannelSubUser(String channelId) throws Exception{
    	Long _channelId = Long.valueOf(channelId);
    	String selectSQL = "SELECT count(1) " +
    			"FROM `channel_sub_user` " +
    			"WHERE type=1 AND channel_id=" + _channelId;
     	List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
     	if(infos.get(0)!=null)
     		return Integer.valueOf(infos.get(0).get("count(1)").toString());	
     	else
     		return -1;
    }
   
    //根据频道名称查询频道Id
    //ChannelSub.java used
    public static List<String> getChannelId(String names) throws Exception{
    	String selectSQL = "SELECT channel_id FROM `channel` WHERE title IN ("+names+")";
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		List<String> list = new ArrayList<String>();
		for(int i=0; i<infos.size(); i++){
			list.add(infos.get(i).get("channel_id").toString());
		}
		return list;   	
    }
       
    public static void main(String[] args){
    	try {
			String list=ChannelSubUserDb.getUserSubChannel("aa");
			System.out.println(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    

}
