package com.dangdang.readerV5.bookstore;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.BookStoreTestEvnSQL;
import com.dangdang.readerV5.reponse.BlockReponse;
import com.dangdang.readerV5.reponse.BorrowReponse;

import fitnesse.slim.SystemUnderTest;

public class Block extends FixtureBase{

	ReponseV2<BlockReponse> reponseResult;
	 
	@SystemUnderTest
	BookStoreTestEvnSQL sql = new BookStoreTestEvnSQL();
	 
	//验证结果
//	public boolean verifyResult() throws Exception{
//		dataVerifyManager.setCaseExpectResult(true);
//		reponseResult =JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<BlockReponse>>(){});
//		if(reponseResult.getStatus().getCode()==0){		
//			//验证json中返回字段
//			log.info("验证Block返回结果：");	
//			BorrowReponse dbResponse = BookStoreTestEvnSQL.getBorrowReponse(paramMap.get("channelType") +"_"+paramMap.get("columnType"), size);
//			dataVerifyManager.add(new ValueVerify<BlockReponse>(reponseResult.getData(), dbResponse, true));
//		}
//		return dataVerifyManager.dataVerify();
//	}
}
