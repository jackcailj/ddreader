package com.dangdang.readerV5.discovery;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.LotteryConfigDb;
import com.dangdang.db.digital.LotteryPrizeRecordDb;
import com.dangdang.db.ucenter.UserDeviceDb;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.LotteryConfig;
import com.dangdang.digital.meta.LotteryPrizeRecord;
import com.dangdang.readerV5.reponse.Data;
import com.dangdang.reader.functional.reponse.LotteryResponse;

public class Shake extends FixtureBase{
	String dayNum_db; //配置参数-db中每天可摇奖测次数
	String dayNum_json; //配置参数-json返回的每天可摇奖测次数
	String canDoNum; //可使用的摇奖次数
	
	ReponseV2<Data> getClienInfoResult;
	ReponseV2<LotteryResponse> lotteryResult;
	//ReponseV2<Data> getClienInfoResult;
		
    @Override
    public void doWork() throws Exception {
    	//获取摇一摇初始化信息接口
    	addAction("getClienInfo");
        super.doWork();
        getClienInfoResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<Data>>(){});
        verifyGetClienInfo();
               
        //获取摇奖结果接口
    	addAction("getLotteryResultWithBell");
        super.doWork();
        lotteryResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<LotteryResponse>>(){});
        verifyLottery();
        
        //领取/放弃奖品服务接口
//    	addAction("recivedOrCancelPrize");
//        super.doWork();
//       // jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<ColumnReponse>>(){});
//        verifyGetClienInfo();            
    }
    
	//验证 获取摇一摇初始化信息结果
    protected void verifyGetClienInfo() throws Exception {
    	   if(reponseV2Base.getStatus().getCode()==0){	
    		   LotteryConfig config = LotteryConfigDb.getLotteryConfig();
    		   dataVerifyManager.add(new ValueVerify<Integer>(getClienInfoResult.getData().getDayNum(),config.getDayNum()).setVerifyContent("验证DayNum"),VerifyResult.SUCCESS);
    	   }
    	super.dataVerify();   	
    }
    
	//验证 获取摇奖结果
    protected void verifyLottery() throws Exception {
    	   if(reponseV2Base.getStatus().getCode()==0){	
    		   String custId = UserDeviceDb.getCustIdByToken(paramMap.get("token"));
    		   LotteryPrizeRecord lottery = LotteryPrizeRecordDb.getPrizeRecordByCustId(custId);
    		   lotteryResult.getData().getPrizeType();
//    		   dataVerifyManager.add(new ValueVerify<Integer>(getClienInfoResult.getData().getDayNum(),lottery.get).setVerifyContent("验证DayNum"));
    	   }
    	//super.dataVerify();    	
    }
    
    
	//验证 领取/放弃奖品结果
    protected void verifyRecivedOrCancelPriz() throws Exception {
    	   if(reponseV2Base.getStatus().getCode()==0){	
    		   LotteryConfig config = LotteryConfigDb.getLotteryConfig();
    		   dataVerifyManager.add(new ValueVerify<Integer>(getClienInfoResult.getData().getDayNum(),config.getDayNum()).setVerifyContent("验证DayNum"),VerifyResult.SUCCESS);
    	   }
    	super.dataVerify();   	
    }

	
	


}
