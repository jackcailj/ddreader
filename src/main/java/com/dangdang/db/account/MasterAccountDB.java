package com.dangdang.db.account;

import java.util.List;

import com.dangdang.account.meta.MasterAccount;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

/**
 * 
 * @author guohaiying
 *
 */
public class MasterAccountDB {

	//根据custId获取用户主帐户信息
	//BuyMonthlyAuthority.java used
	public static MasterAccount getUserMasterAccount(String custId) throws Exception{
		String selectSQL = "SELECT * FROM `master_account` WHERE cust_id="+ custId;
		List<MasterAccount> infos = DbUtil.selectList(Config.ACCOUNTDBConfig, selectSQL, MasterAccount.class);
		return infos.get(0);
	}
	
	//设置账户余额  android主账户：master_account_money   ios主账户：master_account_money_ios
	//BuyMonthlyAuthority.java used 
	public static void SetUserAccount(String custId, String accountType) throws Exception{
		String updateSQL = "UPDATE `master_account` SET "+accountType+"=10000 WHERE cust_id="+custId;
		DbUtil.executeUpdate(Config.ACCOUNTDBConfig, updateSQL);
	}
}
