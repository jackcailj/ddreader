package com.dangdang.db.account;

import java.util.List;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.account.meta.AccountActionTypeInfo;

public class AccountActionTypeInfoDb {

	public static AccountActionTypeInfo getAccountActionType(int actionTypeId) throws Exception{
		String selectSQL = "SELECT * " +
				"FROM `account_action_type_info` " +
				"WHERE action_type_id=" + actionTypeId;
		List<AccountActionTypeInfo> infos = DbUtil.selectList(Config.ACCOUNTDBConfig, selectSQL,AccountActionTypeInfo.class);
		return infos.get(0);
	}
	
	public static void main(String[] args){
		try {
			AccountActionTypeInfo a = AccountActionTypeInfoDb.getAccountActionType(30);
		System.out.println(a.getExperienceAward());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

