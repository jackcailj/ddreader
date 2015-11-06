package com.dangdang.db.ucenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dangdang.authority.meta.MediaMonthlyAuthority;
import com.dangdang.config.Config;
import com.dangdang.db.authority.MediaMonthlyAuthorityDb;
import com.dangdang.db.SqlUtil;
import com.dangdang.ddframework.dbutil.DbUtil;

/**
 * 
 * @author guohaiying
 *
 */
public class UserDeviceDb {
	
	//根据custId和deviceType获取最新的token
	//IMediaApiDubbo.java used
	public static String getToken(String custId, String deviceType) throws Exception{
		int _custId = Integer.valueOf(custId);
		String selectSQL = "SELECT LOGIN_TOKEN " +
				"FROM `user_device` " +
				"WHERE CUST_ID="+_custId+" AND DEVICE_TYPE='"+deviceType+"' " +
				"ORDER BY LOGIN_TOKEN DESC " +
				"LIMIT 1";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.UCENTERDBConfig, selectSQL);
		return infos.get(0).get("LOGIN_TOKEN").toString();
	}
	
	//根据userName获取custId
	public static String getCustIdByName(String name) throws Exception{
		String selectSQL = "SELECT CUST_ID FROM `user_device` WHERE USERNAME='"+name+"' LIMIT 1;";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.UCENTERDBConfig, selectSQL);
		return infos.get(0).get("CUST_ID").toString();
	}
	
	//根据token获取custId
	//GetChannelIdParse.java used
	public static String getCustIdByToken(String token) throws Exception{
		String selectSQL = "SELECT CUST_ID FROM `user_device` WHERE LOGIN_TOKEN='"+token+"'";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.UCENTERDBConfig, selectSQL);
		return infos.get(0).get("CUST_ID").toString();
	}
	
	//获取有频道包月(未过期)的Token
	//GetTokenParse.java used
	public static String getTokenByCustId() throws Exception{	
		List<String> custIdList = MediaMonthlyAuthorityDb.getCustId();
		String selectSQL = "SELECT LOGIN_TOKEN FROM `user_device` " +
				"WHERE LOGIN_TOKEN IS NOT NULL " +
				"AND CUST_ID IN "+SqlUtil.getListToString(custIdList);
		List<Map<String, Object>> infos = DbUtil.selectList(Config.UCENTERDBConfig, selectSQL);
		return infos.get(0).get("LOGIN_TOKEN").toString();
	}
	
	//获取没有包月的token
	//GetTokenParse.java used
	public static String getNoMonthlyToken(String deviceType) throws Exception{
		List<String> custIds = MediaMonthlyAuthorityDb.getCustId();
		String selectSQL = "SELECT LOGIN_TOKEN  " +
				" FROM `user_device` " +
				" WHERE LOGIN_TOKEN IS NOT NULL " +
				" AND DEVICE_TYPE='"+deviceType+
				"' AND CUST_ID NOT IN "+SqlUtil.getListToString(custIds)+
				" ORDER BY LOGIN_TOKEN DESC " +
				" LIMIT 1 ";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.UCENTERDBConfig, selectSQL);
		return infos.get(0).get("LOGIN_TOKEN").toString();
	}
	
	//获取有包月过期的Token
	//GetTokenParse.java used
	public static String getMonthlyOverdueToken(String deviceType) throws Exception{
		List<String> custIds = MediaMonthlyAuthorityDb.getMonthlyOverdueCustId();
		String selectSQL = "SELECT LOGIN_TOKEN  " +
				" FROM `user_device` " +
				" WHERE LOGIN_TOKEN IS NOT NULL " +
				" AND DEVICE_TYPE='"+deviceType+
				"' AND CUST_ID IN "+SqlUtil.getListToString(custIds)+
				" ORDER BY LOGIN_TOKEN DESC " +
				" LIMIT 1 ";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.UCENTERDBConfig, selectSQL);
		return infos.get(0).get("LOGIN_TOKEN").toString();
	}	
	
	//获取有频道(已下架)包月(未过期)的Token
	//GetTokenParse.java used
	public static String getMonthlyChannelOfflineToken(String deviceType) throws Exception{
		List<MediaMonthlyAuthority> list = MediaMonthlyAuthorityDb.getMonthlyChannelOffline();
		List<String> custIds = new ArrayList<String>();
		for(int i=0; i<list.size(); i++){
			custIds.add(String.valueOf(list.get(i).getCustId()));
		}
		String selectSQL = "SELECT LOGIN_TOKEN  " +
				" FROM `user_device` " +
				" WHERE LOGIN_TOKEN IS NOT NULL " +
				" AND DEVICE_TYPE='"+deviceType+
				"' AND CUST_ID IN "+SqlUtil.getListToString(custIds)+
				" ORDER BY LOGIN_TOKEN DESC " +
				" LIMIT 1 ";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.UCENTERDBConfig, selectSQL);
		return infos.get(0).get("LOGIN_TOKEN").toString();
	}	
	
	public static void main(String[] args){
		try {
			String s=UserDeviceDb.getMonthlyChannelOfflineToken("Android");
			System.out.println(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
