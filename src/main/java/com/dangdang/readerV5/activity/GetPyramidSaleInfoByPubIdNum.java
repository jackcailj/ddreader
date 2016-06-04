package com.dangdang.readerV5.activity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ExpressionVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.GetPyramidSaleInfoResponse;

/**
 * 
 * @author guohaiying
 * @date 2016年5月23日 上午10:13:47
 */
public class GetPyramidSaleInfoByPubIdNum extends FixtureBase{
	ReponseV2<GetPyramidSaleInfoResponse> jsonResult;
	
    @Override
    public void doWork() throws Exception {
        super.doWork();
        jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetPyramidSaleInfoResponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
        	String content = jsonResult.getData().getPyramidInfo().getContents();
        	dataVerifyManager.add(new ExpressionVerify(content.contains(paramMap.get("pubIdNum"))).setVerifyContent("验证content中是否包含pubIdNum！"));           
        }
        super.dataVerify();
    }
    

}
