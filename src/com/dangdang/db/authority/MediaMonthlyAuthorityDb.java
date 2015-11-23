package com.dangdang.db.authority;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dangdang.authority.meta.MediaMonthlyAuthority;
import com.dangdang.config.Config;
import com.dangdang.db.digital.ChannelDb;
import com.dangdang.db.SqlUtil;
import com.dangdang.ddframework.dbutil.DbUtil;

/**
 * @author guohaiying
 */
public class MediaMonthlyAuthorityDb {
	
	//用户是否有包月权限
	public static String getUserMonthlyAuthority(String custId, String channelId) throws Exception{
		int _custId = Integer.valueOf(custId);
		int _channelId = Integer.valueOf(channelId);
		String selectSQL = "SELECT COUNT(1) FROM `media_monthly_authority` " +
				"WHERE cust_id="+_custId+" AND relation_id="+_channelId+" AND platform_no=1002 AND monthly_type=2 " +
				"AND NOW()>=monthly_start_time AND NOW()<=monthly_end_time";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.AUTHORITYConfig, selectSQL);
		return infos.get(0).get("COUNT(1)").toString();
	}
	
	//根据custId和channelId获取用户的已包月的频道
	//BuyMonthlyAuthority.java used
	public static MediaMonthlyAuthority get(String custID, String channelID) throws Exception{
		int _custID = Integer.valueOf(custID);
		int _channelID = Integer.valueOf(channelID);
		String selectSQL = "SELECT * " +
				"FROM `media_monthly_authority` " +
				"WHERE cust_id="+_custID+" AND relation_id="+_channelID;
		List<MediaMonthlyAuthority> infos = DbUtil.selectList(Config.AUTHORITYConfig, selectSQL, MediaMonthlyAuthority.class);
		if(infos==null)
			return null;
		else
			return infos.get(0);
	}

	//获取有包月权限的用户，返回cust_id和relation_id(即channelId)
	//IMediaApiDubbo.java used
	public static Map<String, String> getBoughtMonthlyUser(String deviceType) throws Exception{
		String selectSQL = "SELECT cust_id, relation_id" +
				" FROM `media_monthly_authority`" +
				" WHERE platform_no=1002" +
				" AND device_type='"+deviceType+"'" +
				" AND monthly_type=2" +
				" AND NOW()>=monthly_start_time AND NOW()<=monthly_end_time" +
				" LIMIT 1";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.AUTHORITYConfig, selectSQL);	
		Map<String, String> m = new HashMap<String, String>();
		m.put("custId", infos.get(0).get("cust_id").toString());
		m.put("channelId", infos.get(0).get("relation_id").toString());
		return m;
	}
	
	//获取用户已包月未过期的频道
	//IMediaApiDubbo.java used
	//GetChannelIdParse.java used
	//GetBookListParse.java used
	public static List<String> getUserMonthlyChannelID(String custId, String renewFlag) throws Exception{
		String selectSQL = "SELECT relation_id " +
				"FROM `media_monthly_authority` " +
				"WHERE platform_no=1002 " +
				"AND monthly_type=2 " +
				"AND NOW()>=monthly_start_time " +
				"AND NOW()<=monthly_end_time " +
				"AND is_automatically_renew = "+renewFlag+" AND cust_id="+custId;			
		List<Map<String, Object>> infos = DbUtil.selectList(Config.AUTHORITYConfig, selectSQL);
		List<String> list = new ArrayList<String>();
		if(infos == null){
			return null;
		}else{
			for(int i=0; i<infos.size(); i++){
				list.add(infos.get(i).get("relation_id").toString());
			}
			return list;
		}
	}
	
	public static List<String> getUserMonthlyChannelID(String custId) throws Exception{
		String	selectSQL = "SELECT relation_id " +
				"FROM `media_monthly_authority` " +
				"WHERE platform_no=1002 " +
				"AND monthly_type=2 " +
				"AND NOW()>=monthly_start_time " +
				"AND NOW()<=monthly_end_time " +
				"AND cust_id="+custId;	
		List<Map<String, Object>> infos = DbUtil.selectList(Config.AUTHORITYConfig, selectSQL);
		List<String> list = new ArrayList<String>();
		if(infos == null){
			return null;
		}else{
			for(int i=0; i<infos.size(); i++){
				list.add(infos.get(i).get("relation_id").toString());
			}
			return list;
		}
	}
	
	//获取用户包过月的所有频道
	public static List<String> getUserAllMonthlyChannelID(String custId) throws Exception{
		String	selectSQL = "SELECT relation_id " +
				"FROM `media_monthly_authority` " +
				"WHERE platform_no=1002 " +
				"AND monthly_type=2 " +
				"AND cust_id="+custId;	
		List<Map<String, Object>> infos = DbUtil.selectList(Config.AUTHORITYConfig, selectSQL);
		List<String> list = new ArrayList<String>();
		if(infos == null){
			return null;
		}else{
			for(int i=0; i<infos.size(); i++){
				System.out.println("aaaaaa: "+ infos.get(i).get("relation_id"));
				list.add(infos.get(i).get("relation_id").toString());
			}
			return list;
		}
	}
	
	//已购买的频道已过期，renewFlag: 1(自动续订状态)（需要跑后台任务） 0(不自动续费状态)，测续费
	//GetChannelIdParse.java used
	public static String getAutoRenewChannel(String custId,String renewFlag) throws Exception{
		int _custId = Integer.valueOf(custId);
		int _renewFlag = Integer.valueOf(renewFlag);
		String selectSQL = "SELECT relation_id FROM `media_monthly_authority` " +
				"WHERE platform_no='1002' " +
				"AND monthly_type=2 " +
				"AND NOW()>monthly_end_time " +
				"AND is_automatically_renew = "+_renewFlag+" AND cust_id =" +_custId;
		List<Map<String, Object>> infos = DbUtil.selectList(Config.AUTHORITYConfig, selectSQL);
		return infos.get(0).get("relation_id").toString();
	}
	
	//获取有包月权限的custId
	//UserDeviceDb.java used
	//GetTokenParse.java used
	public static List<String> getCustId() throws Exception{
		String selectSQL = "SELECT DISTINCT(cust_id) FROM `media_monthly_authority` WHERE NOW()<=monthly_end_time";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.AUTHORITYConfig, selectSQL);
		List<String> custIdList = new ArrayList<String>();
		for(int i=0; i<infos.size(); i++){
			custIdList.add(infos.get(i).get("cust_id").toString());
		}
		return custIdList;
	}
	
	//获取有包月过期的custId
	//UserDeviceDb.java used
	public static List<String> getMonthlyOverdueCustId() throws Exception{
		String selectSQL = "SELECT cust_id FROM `media_monthly_authority` WHERE NOW()>monthly_end_time";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.AUTHORITYConfig, selectSQL);
		List<String> custIdList = new ArrayList<String>();
		for(int i=0; i<infos.size(); i++){
			custIdList.add(infos.get(i).get("cust_id").toString());
		}
		return custIdList;
	}
	
	//获取有包月：未过期 已下线
	//GetChannelIdParse.java used
	//UserDeviceDb.java used
	public static List<MediaMonthlyAuthority> getMonthlyChannelOffline() throws Exception{
		List<String> channelIDs = ChannelDb.getOfflineChannelList();
		String selectSQL="SELECT * FROM `media_monthly_authority` " +
				"WHERE NOW()<monthly_end_time " +
				"AND relation_id IN "+SqlUtil.getListToString(channelIDs);
		List<MediaMonthlyAuthority> infos = DbUtil.selectList(Config.AUTHORITYConfig, selectSQL, MediaMonthlyAuthority.class);
		return infos;
	}
	
	//获取用户包月频道中频道的续费状态
	//ChangeAutoBuyMonthlyState.java used
	public static String getUserMonthlyState(String custId, String channelId) throws Exception{
		int _custId = Integer.valueOf(custId);
		int _channelId = Integer.valueOf(channelId);
		String selectSQL = "SELECT is_automatically_renew " +
				" FROM `media_monthly_authority` " +
				" WHERE cust_id= "+_custId+
				" AND relation_id="+_channelId;
		List<Map<String, Object>> infos = DbUtil.selectList(Config.AUTHORITYConfig, selectSQL);
		return infos.get(0).get("is_automatically_renew").toString();
	}

	
	public static void main(String[] args){
		 List<String> list = null;
		try {
			String s = MediaMonthlyAuthorityDb.getUserMonthlyState("50244532","17");
			System.out.println("aaa" + s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
