package com.dangdang.db.account;

import com.dangdang.account.meta.AccountGradeExperienceRelation;
import com.dangdang.account.meta.AttachAccount;
import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

/**
 * Created by cailianjie on 2015-11-16.
 */
public class AccountGradeExperienceRelationDb {


    /*
    获取等级经验信息。
    */
    public  static AccountGradeExperienceRelation getAccountGradeExperience(int grade) throws Exception {
        String selectSql = "select * from account_grade_experience_relation where grade="+grade;
        AccountGradeExperienceRelation account = DbUtil.selectOne(Config.ACCOUNTDBConfig,selectSql,AccountGradeExperienceRelation.class);

        return account;
    }
}
