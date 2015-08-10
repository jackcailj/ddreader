package com.dangdang.readerV5.channel;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.autotest.common.ResponseVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.channel.BookListDetailSQL;
import com.dangdang.readerV5.reponse.BookListDetailReponse;

import fitnesse.slim.SystemUnderTest;

/**
 * 书单书列表接口
 * @author guohaiying
 *
 */
public class BookListDetail extends FixtureBase{

	 ReponseV2<BookListDetailReponse> reponseResult;
	   
	 public ReponseV2<BookListDetailReponse> getReponseResult() {
		return reponseResult;
	}
	 
	 @SystemUnderTest
	 public BookListDetailSQL sql = new BookListDetailSQL();
	
	//验证结果
	public boolean verifyResult() throws Exception{
		dataVerifyManager.setCaseExpectResult(true);
		reponseResult =JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<BookListDetailReponse>>(){});
		if(reponseResult.getStatus().getCode()==0){		
			//验证json中返回字段
			log.info("验证书单书列表");	
			int bookListId=Integer.valueOf(paramMap.get("bookListId"));
			int size = Integer.valueOf(paramMap.get("end"))-Integer.valueOf(paramMap.get("start"))+1;
			BookListDetailReponse dbResponse = new BookListDetailReponse();
			dbResponse.setMediaList(BookListDetailSQL.getMediaList(bookListId, size));
			dbResponse.setTotal(BookListDetailSQL.getMediaListCount(bookListId));
			log.info("dbResponse"+dbResponse);
			log.info("reponseResult.getData()"+reponseResult.getData());
			dataVerifyManager.add(new ResponseVerify(reponseResult.getData(), dbResponse));	
		}
		return dataVerifyManager.dataVerify();
	}
}
