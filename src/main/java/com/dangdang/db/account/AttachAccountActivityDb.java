package com.dangdang.db.account;

import java.util.List;

import com.dangdang.account.meta.AttachAccountActivity;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

public class AttachAccountActivityDb {
	
	public static AttachAccountActivity getActivityMsg(String activityCode) throws Exception{
		String selectSql = "SELECT * FROM `attach_account_activity` WHERE activity_code='"+activityCode+"'";
		List<AttachAccountActivity> infos = DbUtil.selectList(Config.ACCOUNTDBConfig,selectSql,AttachAccountActivity.class);
		if(infos.size()==0)
			return null;
		else
			return infos.get(0);
	}
	
	public static void main(String[] args) throws Exception{
		AttachAccountActivity  aaa = AttachAccountActivityDb.getActivityMsg("laxin_1002_android");
		System.out.println(aaa.getActivityCode());
	}

}
