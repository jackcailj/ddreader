package com.dangdang.readerV5.bookstore;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.autotest.common.ResponseVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.BookListReponse;

import fitnesse.slim.SystemUnderTest;

/**
 * 书单基本信息接口
 * @author guohaiying
 *
 */
public class Booklist extends FixtureBase{
	
	ReponseV2<BookListReponse> reponseResult;
	 
	//@SystemUnderTest
	//public ChannelSQL sql = new ChannelSQL();
	   	
	//验证结果
//	public boolean verifyResult() throws Exception{
//		dataVerifyManager.setCaseExpectResult(true);
//		reponseResult =JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<BookListReponse>>(){});
//		if(reponseResult.getStatus().getCode()==0){		
//			//验证json中返回字段
//			log.info("验证书单基本信息");	
//			com.dangdang.readerV5.reponse.BookList dbResponse=ChannelSQL.getBookMessage(Integer.valueOf(paramMap.get("bookListId")));
//			dataVerifyManager.add(new ResponseVerify(reponseResult.getData().getBookList(), dbResponse));
//		}
//		
//		return dataVerifyManager.dataVerify();  
//	}

}
