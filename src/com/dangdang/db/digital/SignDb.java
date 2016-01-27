package com.dangdang.db.digital;

import ch.qos.logback.core.db.dialect.DBUtil;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.SigninDetail;
import com.dangdang.digital.meta.SigninMain;
import org.omg.CORBA.OBJ_ADAPTER;


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
        String selectString="SELECT value from signin_prize where continue_days ="+(continueDay>8?8:continueDay) +" limit 1";
        Map<String,Object> result=DbUtil.selectOne(Config.YCDBConfig,selectString);
        return Integer.parseInt(result.get("value").toString());
    }

    /*
    获得某天签到总人数
     */
    public static int getSigninCountPerDay() throws Exception {
        String selectString="select count(*) as count from (select * from signin_detail WHERE DATE_FORMAT(signin_time,'%y-%m-%d')=DATE_FORMAT(NOW(),'%y-%m-%d') GROUP BY signin_main_id) as a";
        Map<String,Object> result=DbUtil.selectOne(Config.YCDBConfig,selectString);
        return Integer.parseInt(result.get("count").toString());
    }

    /*
    获取某个人签到2个月内记录历史
     */
    public static List<SigninDetail> getSigninDetail(String custId) throws Exception {
        String selectString="SELECT sd.* from signin_detail sd\n" +
                "LEFT JOIN signin_main sm on sd.signin_main_id=sm.id\n" +
                "where sm.cust_id="+custId+" and sd.signin_time>=DATE_SUB(CURDATE(),INTERVAL 70 DAY)\n" +
                "GROUP BY signin_main_id,DATE_FORMAT(signin_time,'%y-%m-%d') ORDER BY id";
        List<SigninDetail> results=DbUtil.selectList(Config.YCDBConfig,selectString,SigninDetail.class);
        return results;
    }

    /*
   获取某个人签到2个月内记录历史
    */
    public static  int getContinuDaysLessCount(String continudays) throws Exception {
        String selectString="SELECT count(*) as count from signin_main where continue_days<="+continudays;
        Map<String,Object> result=DbUtil.selectOne(Config.YCDBConfig,selectString);
        return Integer.parseInt(result.get("count").toString());
    }
}
