package com.dangdang.readerV5.channel;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.channel.BookListSQL;
import com.dangdang.readerV5.reponse.ChannelBookList;
import com.dangdang.readerV5.reponse.ChannelBookListResponse;

import fitnesse.slim.SystemUnderTest;

/**
 * 书单基本信息接口
 * @author guohaiying
 *
 */
public class BookList extends FixtureBase{

	ReponseV2<ChannelBookListResponse> reponseResult;	
	
	@SystemUnderTest
	public BookListSQL sql = new BookListSQL();
	
	public boolean verifyResult() throws Exception{
		dataVerifyManager.setCaseExpectResult(true);
		reponseResult =JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<ChannelBookListResponse>>(){});
		if(reponseResult.getStatus().getCode()==0){	
			//验证json中返回字段
			log.info("验证书单基本信息");	
			ChannelBookList  bookList = BookListSQL.getBookList(paramMap.get("bookListId"));
			dataVerifyManager.add(new ValueVerify<ChannelBookList>(bookList, reponseResult.getData().getBookList(), true));	
		}
		return dataVerifyManager.dataVerify();    
	 }
	
}
