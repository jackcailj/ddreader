package com.dangdang.db.digital;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.LotteryConfig;


public class LotteryConfigDb {
	
	public static LotteryConfig getLotteryConfig( ) throws Exception{
    	String selectSQL = "SELECT * FROM lottery_config WHERE config_id=1";
    	LotteryConfig infos = DbUtil.selectOne(Config.YCDBConfig, selectSQL, LotteryConfig.class);	
    	return infos;
	}
	
	public static void main(String[] args) throws Exception{
		LotteryConfig infos = LotteryConfigDb.getLotteryConfig();
		System.out.println(infos.getDayNum());
	}
	
	

}
