package com.dangdang.digital;

import ch.qos.logback.core.db.dialect.DBUtil;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.model.SigninMain;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by cailianjie on 2015-8-21.
 */
public class SignDb {

    public  static  void upadteSignDate(String custId,int firstdaydiff,int lastdatediff) throws Exception {
        //SigninMain signinMain = getSignRecord(custId);
        String updateString="UPDATE signin_main SET first_signin_time=DATE_SUB(NOW(),INTERVAL "+firstdaydiff+" day),last_signin_time=DATE_SUB(NOW(),INTERVAL "+lastdatediff+" day) where cust_id="+custId;
        DbUtil.executeUpdate(Config.YCDBConfig,updateString);
    }

    /*
    获取某个用户某天后的签到记录
     */
    public static SigninMain getSignRecord(String custId) throws Exception {
        String selectSql="select * FROM signin_main where cust_id="+custId;
        SigninMain signinMain = DbUtil.selectOne(Config.YCDBConfig,selectSql,SigninMain.class);

        return signinMain;
    }

    /*
    根据连续签到天数获取奖励积分值
     */
    public static int getSignPrize(int continueDay) throws Exception {
        String selectString="SELECT value from signin_prize where continue_days ="+(continueDay>8?8:continueDay);
        Map<String,Object> result=DbUtil.selectOne(Config.YCDBConfig,selectString);
        return Integer.parseInt(result.get("value").toString());
    }
}
