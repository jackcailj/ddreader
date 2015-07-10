package com.dangdang.readerV5.bookstore;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.BookStoreTestEvnSQL;
import com.dangdang.readerV5.reponse.BorrowReponse;

import fitnesse.slim.SystemUnderTest;

public class Borrow extends FixtureBase{
	ReponseV2<BorrowReponse> reponseResult;
	 
	@SystemUnderTest
	public BookStoreTestEvnSQL service = new BookStoreTestEvnSQL();
	 
	//验证结果
	public boolean verifyResult() throws Exception{
		dataVerifyManager.setCaseExpectResult(true);
		reponseResult =JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<BorrowReponse>>(){});
		if(reponseResult.getStatus().getCode()==0){		
			//验证json中返回字段
			log.info("验证借阅返回结果：");	
			int size = Integer.valueOf(paramMap.get("end"))-Integer.valueOf(paramMap.get("start")) + 1;
			BorrowReponse dbResponse = BookStoreTestEvnSQL.getBorrowReponse(paramMap.get("channelType")+"_"+paramMap.get("columnType"), size);
			log.info("aaa");
			dataVerifyManager.add(new ValueVerify<BorrowReponse>(reponseResult.getData(), dbResponse, true));
		}
		return dataVerifyManager.dataVerify();
	}
}
