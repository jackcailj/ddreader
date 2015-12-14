package com.dangdang.db.ucenter;

import com.dangdang.config.Config;
import com.dangdang.db.digital.ChannelDb;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ucenter.meta.LoginRecord;

import java.util.List;
import java.util.Map;

/**
 * @author guohaiying
 */
public class LoginRecordDb {
	//获取用户的登录记录信息
	public static Map<String, Object> getUserLoginRecord(String channelId) throws Exception{
		int _custId = Integer.valueOf(ChannelDb.getCustIdByChannelId(channelId));
		String selectSQL = "SELECT cust_nickname,channel_owner FROM `login_record` " +
				"WHERE cust_id="+_custId;
		List<Map<String, Object>> infos = DbUtil.selectList(Config.UCENTERDBConfig,selectSQL);
		if(infos.size()==0)
			return null;
		else
			return infos.get(0);			
	}
	
	//根据custId获取登录记录信息
	public static LoginRecord getLoginRecord(String custId) throws Exception{
		String selectSQL = "SELECT * FROM `login_record` WHERE cust_id="+custId;
		List<LoginRecord> infos = DbUtil.selectList(Config.UCENTERDBConfig, selectSQL, LoginRecord.class);
		LoginRecord loginRecord = null;
		try{
			loginRecord = infos.get(0);
		}catch(Exception e){
			System.out.println("在表login_record中未搜索到记录custId="+custId);
		}
		return loginRecord;
	}
	
	public static void main(String[] args){
		try {
			Map<String, Object> m = LoginRecordDb.getUserLoginRecord("1169");
			System.out.println("aaaa"+m);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
