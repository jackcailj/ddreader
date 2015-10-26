package com.dangdang.readerV5.bookstore;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.db.digital.bookstore.BlockSQL;
import com.dangdang.readerV5.reponse.BlockReponse;

public class Block extends FixtureBase{

	ReponseV2<BlockReponse> reponseResult;
	  
	//验证结果
	public boolean verifyResult() throws Exception{
		dataVerifyManager.setCaseExpectResult(true);
		reponseResult =JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<BlockReponse>>(){});
		if(reponseResult.getStatus().getCode()==0){		
			//验证json中返回字段
			log.info("验证Block返回结果：");	
			String dbResponse = BlockSQL.getBlock(paramMap.get("code"));
			dataVerifyManager.add(new ValueVerify<String>(reponseResult.getData().getBlock(), dbResponse));
		}
		return dataVerifyManager.dataVerify();
	}
}
