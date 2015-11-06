package com.dangdang.readerV5.channel;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.db.digital.channel.BookListSQL;
import com.dangdang.readerV5.reponse.ChannelBookList;
import com.dangdang.readerV5.reponse.ChannelBookListResponse;


import fitnesse.slim.SystemUnderTest;

/**
 * 书单基本信息接口
 * @author guohaiying
 *
 */
public class BookList extends FixtureBase{

	ReponseV2<ChannelBookListResponse> jsonResult;	
	
	@SystemUnderTest
	public BookListSQL sql = new BookListSQL();
	
	public BookList(){
		addAction("bookList");
	}
	
    @Override
    public void doWork() throws Exception {
        super.doWork();
        jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<ChannelBookListResponse >>(){});
    }
    
    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){       	
        	//ChannelBookList  bookList = BookListSQL.getBookList(paramMap.get("bookListId"));
        	//dataVerifyManager.add(new ValueVerify<ChannelBookList>(bookList, jsonResult.getData().getBookList(), true));
            
        }
        super.dataVerify();
    }
	
}
