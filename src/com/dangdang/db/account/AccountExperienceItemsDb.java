package com.dangdang.db.account;

import ch.qos.logback.core.db.dialect.DBUtil;
import com.dangdang.account.meta.AccountExperienceItems;
import com.dangdang.autotest.config.Config;
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
}
