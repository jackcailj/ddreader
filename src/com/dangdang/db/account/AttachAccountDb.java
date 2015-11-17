package com.dangdang.db.account;

import com.dangdang.account.meta.AccountExperienceItems;
import com.dangdang.account.meta.AttachAccount;
import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

import java.util.List;

/**
 * Created by cailianjie on 2015-11-16.
 */
public class AttachAccountDb {
    public  static AttachAccount getAttachAccount(String custId) throws Exception {
        String selectSql = "select * from attach_account where cust_id ="+custId;
        AttachAccount account = DbUtil.selectOne(Config.ACCOUNTDBConfig,selectSql,AttachAccount.class);

        return account;
    }

}
