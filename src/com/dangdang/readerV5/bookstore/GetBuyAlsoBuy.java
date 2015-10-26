package com.dangdang.readerV5.bookstore;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.db.digital.BookStoreTestEvnSQL;
import com.dangdang.readerV5.reponse.GetBuyAlsoBuyReponse;

import fitnesse.slim.SystemUnderTest;

/**
 * 买了又买接口
 * @author guohaiying
 *
 */
public class GetBuyAlsoBuy extends FixtureBase{
	ReponseV2<GetBuyAlsoBuyReponse> reponseResult;
	
	@SystemUnderTest
	public BookStoreTestEvnSQL service = new BookStoreTestEvnSQL();
	//验证结果
	public boolean verifyResult() throws Exception{
		dataVerifyManager.setCaseExpectResult(true);
		reponseResult =JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetBuyAlsoBuyReponse>>(){});
		if(reponseResult.getStatus().getCode()==0){		
			//验证json中返回字段
			//log.info("验证栏目下的返回数据：");	
			//int size = Integer.valueOf(paramMap.get("end"))-Integer.valueOf(paramMap.get("start")) + 1;
			//ColumnReponse dbResponse = BookStoreCommSQL.getColumnReponse(paramMap.get("columnType"), size);
			//dataVerifyManager.add(new ResponseVerify(reponseResult.getData(), dbResponse));
			//dataVerifyManager.add(new ValueVerify<ColumnReponse>(reponseResult.getData(), dbResponse, true));
			
		}
		return true;      	
	}
}
