package com.dangdang.db.account;

import java.util.List;
import java.util.Map;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

import com.dangdang.account.meta.AccountExperienceItems;
import com.dangdang.ddframework.dbutil.DbUtil;

import java.util.List;

/**
 * Created by cailianjie on 2015-11-16.
 */
public class AccountExperienceItemsDb {

    /*
    获取经验列表
     */
    public static List<AccountExperienceItems> getAccountExpreienceItems(String cust_id,long lastExperienceItemsId,int pageSize) throws Exception {
        String selectSql = "SELECT experience_items_id,cust_id,experience,action_type,platform_no,device_type,creation_date from account_experience_items where cust_id="+cust_id+ (lastExperienceItemsId==0?"":" and  experience_items_id<"+lastExperienceItemsId)+" ORDER BY creation_date desc LIMIT "+pageSize;
        List<AccountExperienceItems> itemses = DbUtil.selectList(Config.ACCOUNTDBConfig,selectSql,AccountExperienceItems.class);

        return itemses;
    }
  //字段action_type在account库的account_action_type_info表里
	//个人发表文章/攻略 30  后台删除文章/攻略31  道长删除文章/攻略32
	//获取用户的经验值
	public static String getUserExperience(String custId) throws Exception{
		int _custId = Integer.valueOf(custId);
		String selectSQL = "SELECT SUM(experience) " +
				"FROM `account_experience_items` " +
				"WHERE platform_no=1002 " +
				"AND cust_id="+ _custId;
		List<Map<String, Object>> infos = DbUtil.selectList(Config.ACCOUNTDBConfig, selectSQL);
		if(infos.get(0).get("SUM(experience)")==null) return null;
		else return infos.get(0).get("SUM(experience)").toString();
	}
	
	public static String getUserExperienceByType(String custId, int actionType, String deviceType) throws Exception{
		int _custId = Integer.valueOf(custId);
		String selectSQL = "SELECT SUM(experience)" +
				" FROM `account_experience_items` " +
				" WHERE action_type= " +actionType +
				" AND platform_no=1002 " +
				" AND device_type='"+deviceType+"' " +
				" AND cust_id="+_custId;
		List<Map<String, Object>> infos = DbUtil.selectList(Config.ACCOUNTDBConfig, selectSQL);
		if(infos.get(0).get("SUM(experience)")==null) return null;
		else return infos.get(0).get("SUM(experience)").toString();
	}
	
	public static void main(String[] args){
		try {
			String s= AccountExperienceItemsDb.getUserExperience("50098052");
			System.out.println(s);
			
			s= AccountExperienceItemsDb.getUserExperienceByType("50098052",30,"Android");
			System.out.println(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
