package com.dangdang.account;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

/**
 * Created by cailianjie on 2015-6-18.
 */
public class AccountUtils {

    public static AccountInfo getAccountInfo(String custId) throws Exception {
        String selectString="select m.cust_id as custId,m.user_name as userName,m.master_account_money as masterAccountMoney,m.master_account_money_ios as masterAccountMoneyIos,\n" +
                "a.account_grade as accountGrade,a.account_integral as accountIntegral, a.attach_account_money as attachAccountMoney,a.attach_account_money_ios as attachAccountMoneyIos from master_account m\n" +
                "LEFT JOIN attach_account  a on m.cust_id=a.cust_id where m.cust_id="+custId;

        AccountInfo info = DbUtil.selectOne(Config.ACCOUNTDBConfig,selectString,AccountInfo.class);
        return info;
    }
}
