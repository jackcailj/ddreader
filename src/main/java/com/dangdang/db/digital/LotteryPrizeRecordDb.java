package com.dangdang.db.digital;

import java.util.Map;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.LotteryPrizeRecord;

public class LotteryPrizeRecordDb {
	
	//根据custId获摇奖记录
	public static LotteryPrizeRecord getPrizeRecordByCustId(String custId) throws Exception{
    	String selectSQL = "SELECT * FROM lottery_prize_record " +
    			"WHERE cust_id="+custId+" AND DATE(creation_date)=CURDATE() ORDER BY creation_date DESC LIMIT 1";
    	LotteryPrizeRecord infos = DbUtil.selectOne(Config.YCDBConfig, selectSQL, LotteryPrizeRecord.class);	
    	return infos;
	}
	
	//根据custId获取用户的摇奖记录数
	public static String getPrizeRecordNum(String custId) throws Exception{
		String selectSQL = "SELECT COUNT(1) FROM `lottery_prize_record` WHERE cust_id="+custId+" AND DATE(creation_date)=CURDATE();";
		Map<String, Object> infos = DbUtil.selectOne(Config.YCDBConfig, selectSQL);
		return infos.get("COUNT(1)").toString();
	}
	
	public static void main(String[] args) throws Exception{
		String num = LotteryPrizeRecordDb.getPrizeRecordNum("50098052");
		System.out.println(num);
		
		LotteryPrizeRecord infos= LotteryPrizeRecordDb.getPrizeRecordByCustId("50098052");
		System.out.println(infos==null);
	}

}
