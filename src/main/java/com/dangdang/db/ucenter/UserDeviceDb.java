package com.dangdang.db.ucenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dangdang.authority.meta.MediaMonthlyAuthority;
import com.dangdang.config.Config;
import com.dangdang.db.authority.AuthorityDb;
import com.dangdang.db.authority.MediaMonthlyAuthorityDb;
import com.dangdang.db.SqlUtil;
import com.dangdang.ddframework.dbutil.DbUtil;

/**
 * 
 * @author guohaiying
 *
 */
public class UserDeviceDb {
	
	//根据userName和deviceType获取最新的token
	public static String getTokenByUserName(String userName, String deviceType) throws Exception{
		String selectSQL = "SELECT LOGIN_TOKEN " +
				"FROM `user_device` " +
				"WHERE USERNAME='"+userName+"' AND DEVICE_TYPE='"+deviceType+"' " +
				"ORDER BY LAST_LOGIN_TIME DESC " +
				"LIMIT 1";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.UCENTERDBConfig, selectSQL);
		if(infos.size()==0)
			return null;
		else
			return infos.get(0).get("LOGIN_TOKEN").toString();
	}
	
	//根据userName和deviceType获取最新的token
	public static String getTokenByUserName(String userName) throws Exception{
		String selectSQL = "SELECT LOGIN_TOKEN " +
				"FROM `user_device` " +
				"WHERE USERNAME='"+userName+"' "+
				" ORDER BY LAST_LOGIN_TIME DESC " +
				" LIMIT 1";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.UCENTERDBConfig, selectSQL);
		if(infos.size()==0)
			return null;
		else
			return infos.get(0).get("LOGIN_TOKEN").toString();
	}
	
	//根据custId和deviceType获取最新的token
	//IMediaApiDubbo.java used
	public static String getToken(String custId, String deviceType) throws Exception{
		int _custId = Integer.valueOf(custId);
		String selectSQL = "SELECT LOGIN_TOKEN " +
				"FROM `user_device` " +
				"WHERE CUST_ID="+_custId+" AND DEVICE_TYPE='"+deviceType+"' " +
				"ORDER BY LAST_LOGIN_TIME DESC " +
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
		if(infos.size()==0) return null;
		else
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
		int n= (int)Math.random()*(infos.size()-1);
		return infos.get(n).get("LOGIN_TOKEN").toString();
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
				" ORDER BY LAST_LOGIN_TIME DESC " +
				" LIMIT 1 ";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.UCENTERDBConfig, selectSQL);
		int n= (int)Math.random()*(infos.size()-1);
		return infos.get(n).get("LOGIN_TOKEN").toString();
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
				" ORDER BY LAST_LOGIN_TIME DESC " +
				" LIMIT 1 ";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.UCENTERDBConfig, selectSQL);
		int n= (int)Math.random()*(infos.size()-1);
		return infos.get(n).get("LOGIN_TOKEN").toString();
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
				" ORDER BY LAST_LOGIN_TIME DESC " +
				" LIMIT 1 ";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.UCENTERDBConfig, selectSQL);
		int n= (int)Math.random()*(infos.size()-1);
		return infos.get(n).get("LOGIN_TOKEN").toString();
	}	
	
	//获取没有买过书的Token
	//GetTokenParse.java used
	public static String getNoBuyBookToken(String deviceType) throws Exception{
		List<String> custIDs = AuthorityDb.getAllCustIdList();
		String selectSQL = "SELECT LOGIN_TOKEN " +
				" FROM `user_device` " +
				" WHERE LOGIN_TOKEN IS NOT NULL  " +
				" AND DEVICE_TYPE='"+deviceType+
				"' AND CUST_ID NOT IN "+SqlUtil.getListToString(custIDs)+
				" LIMIT 1";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.UCENTERDBConfig, selectSQL);
		int n= (int)Math.random()*(infos.size()-1);
		return infos.get(n).get("LOGIN_TOKEN").toString();
	}
	
	//获取买过书的Token
	//GetTokenParse.java used
	public static String getAlreadyBuyBookToken(String deviceType) throws Exception{
		List<String> custIDs = AuthorityDb.getCustIdList("media_authority_0");
		String selectSQL = "SELECT LOGIN_TOKEN " +
				" FROM `user_device` " +
				" WHERE LOGIN_TOKEN IS NOT NULL  " +
				" AND DEVICE_TYPE='"+deviceType+
				"' AND CUST_ID IN"+SqlUtil.getListToString(custIDs)+
				" LIMIT 1";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.UCENTERDBConfig, selectSQL);
		int n= (int)Math.random()*(infos.size()-1);
		return infos.get(n).get("LOGIN_TOKEN").toString();
	}
	
	public static List<String> getDeviceNo() throws Exception{
		String selectSQL = "select distinct(DEVICE_NO) from user_device  where DEVICE_TYPE='Android' limit 1000";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.UCENTERDBConfig, selectSQL);
		List list = new ArrayList<String>();
		for(int i=0;i<infos.size();i++){
			list.add(infos.get(i).get("DEVICE_NO").toString());
		}
		return list;
	}
	
	public static void main(String[] args){
		try {
//			List list = new ArrayList<String>();
//			list=UserDeviceDb.getDeviceNo();
//			String s="";
//			for(int i=0;i<list.size();i++){
//				s+=list.get(i)+",";
//			}		
			String s = UserDeviceDb.getCustIdByName("cailj_ddtest@126.com");
			System.out.println(s);
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
