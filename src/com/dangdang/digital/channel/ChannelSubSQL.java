package com.dangdang.digital.channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dangdang.config.Config;
//import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

/**
 * 频道订阅接口相关sql
 * @author guohaiying
 *
 */
public class ChannelSubSQL {
	
	//随机获取一个可用的频道
	public static String getChannel() throws Exception{
	 	String selectSQL = "SELECT channel.channel_id " +
		" from media_column_content mcc left join channel on mcc.sale_id= channel.channel_id"+
		" where channel.shelf_status=1"+
		" and channel.is_completed=1"+
		" and  mcc.status in(1,2) ORDER BY RAND() LIMIT 1";
	 	List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
	 	return infos.get(0).get("channel_id").toString();
	}
	
	//删除某用户订阅的某频道记录（为模拟首次订阅）
//	public static void delSubWithCustidAndChannelid(String custid, String channelid) throws Exception{
//		int custID = Integer.valueOf(custid);
//		int channelID = Integer.valueOf(channelid);
//		String selectSQL="SELECT COUNT(1) FROM channel_sub_user WHERE cust_id="+custID+" AND channel_id=" + channelID;
//		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
//	 	int num = Integer.valueOf(infos.get(0).get("COUNT(1)").toString());
//	 	if(num==1){
//	 		String delSQL="DELETE FROM channel_sub_user WHERE cust_id="+custID+" AND channel_id=" + channelID;
//			DbUtil.executeUpdate(Config.YCDBConfig, delSQL);
//	 	
//			//查询频道订阅总数	
//			int sub_number = Integer.valueOf(getSubTotal(channelid));
//	
//			//频道订阅总数-1
//			if(sub_number>0){
//				String updateSQL="UPDATE `channel` SET sub_number="+(sub_number-1)+" WHERE channel_id=" + channelID;
//				DbUtil.executeUpdate(Config.YCDBConfig, updateSQL);
//			}
//	 	}
//
//	}
//	
	//查询用户是否订阅或取消订阅某频道 type=1订阅  type=0取消订阅
	public static String isSub(String custId, String channelId, String type) throws Exception{
		int _custId = Integer.valueOf(custId);
		int _channelId = Integer.valueOf(channelId);
		int _type = Integer.valueOf(type);
	  	String selectSQL="SELECT count(1) " +
	  			"FROM channel_sub_user " +
	  			"WHERE type="+_type+" AND cust_id="+_custId+" AND channel_id=" + _channelId;
		List<Map<String, Object>>  infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		return infos.get(0).get("count(1)").toString();			 
	}
	
	
    //获取频道订阅总数
    public static String getSubTotal(String channelid) throws Exception{
    	int channelID = Integer.valueOf(channelid);
    	String selectSQL="SELECT sub_number FROM channel WHERE channel_id=" + channelID;
		List<Map<String, Object>> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL);	
		if(infos.get(0)!=null)
			return infos.get(0).get("sub_number").toString();	
		else
			return "";
    }
    
    //订阅或取消订阅
    public static void setSubWithCustidAndChannelidAndType(String custid, String channelid, String type) throws Exception{
		int custID = Integer.valueOf(custid);
		int channelID = Integer.valueOf(channelid);
		int _type = Integer.valueOf(type);
    	String updateSQL = "UPDATE channel_sub_user " +
    			"SET type="+type+" WHERE cust_id="+custID+" AND channel_id="+channelID;
    	DbUtil.executeUpdate(Config.YCDBConfig, updateSQL);
    	
		//查询频道订阅总数	
		int sub_number = Integer.valueOf(getSubTotal(channelid));

		if(_type==1)
			updateSQL="UPDATE `channel` SET sub_number="+(sub_number+1)+" WHERE channel_id=" + channelID;
		else if(_type==0&&sub_number>0)
			updateSQL="UPDATE `channel` SET sub_number="+(sub_number-1)+" WHERE channel_id=" + channelID;
		DbUtil.executeUpdate(Config.YCDBConfig, updateSQL);
    }  
   
    //根据频道名称查询频道Id
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
			List<String> list=ChannelSubSQL.getChannelId("aa");
			System.out.println(list.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
}
