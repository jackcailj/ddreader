package com.dangdang.readerV5.activity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ExpressionVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.GetPubIdNumByTokenResponse;

/**
 * 根据token获取用户的pubId
 * @author guohaiying
 * @date 2016年5月23日 上午9:45:50
 */
public class GetPubIdNumByToken extends FixtureBase{

	ReponseV2<GetPubIdNumByTokenResponse> jsonResult;
	
	//设置用户名到 paramMap参数中
	public void setParam(String userName) throws Exception {
		paramMap.put("userName", userName);
		super.beforeParseParam();
	}
	
    @Override
    public void doWork() throws Exception {
        super.doWork();
        jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetPubIdNumByTokenResponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
        	String pubId = jsonResult.getData().getPubIdNum();
            dataVerifyManager.add(new ExpressionVerify(!pubId.equals(null)&&!"".equals(pubId)).setVerifyContent("验证pubId不为空！"));           
        }
        super.dataVerify();
    }
    
    public String getPubIdNum(){
    	return jsonResult.getData().getPubIdNum();
    }
}
