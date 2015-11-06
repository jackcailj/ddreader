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
		int _custId = Integer.valueOf(custId);
		String selectSQL = "SELECT * FROM `master_account` WHERE cust_id="+ _custId;
		List<MasterAccount> infos = DbUtil.selectList(Config.ACCOUNTDBConfig, selectSQL, MasterAccount.class);
		return infos.get(0);
	}
}
