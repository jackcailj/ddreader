package com.dangdang.readerV5.bookstore;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ExpressionVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.GetBuyAlsoBuyReponse;


/**
 * 买了又买接口
 * @author guohaiying
 *
 */
public class GetBuyAlsoBuy extends FixtureBase{
	ReponseV2<GetBuyAlsoBuyReponse> jsonResult;
	
    @Override
    public void doWork() throws Exception {
        super.doWork();
        jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetBuyAlsoBuyReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
        	int size = jsonResult.getData().getMediaList().size();
        	dataVerifyManager.add(new ExpressionVerify(size>0).setVerifyContent("验证数量 "+size));           
        }
        super.dataVerify();
    }

}
