package com.dangdang.readerV5.bookstore;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.autotest.common.ResponseVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.db.digital.BookStoreCommSQL;
import com.dangdang.db.digital.BookStoreTestEvnSQL;
import com.dangdang.readerV5.reponse.ColumnReponse;
import com.dangdang.readerV5.reponse.GetBackgroundImgListResponse;

import fitnesse.slim.SystemUnderTest;

/**
 * 书城栏目接口
 * @author guohaiying
 *
 */
public class Column extends FixtureBase{
	ReponseV2<ColumnReponse> jsonResult;
	
	@SystemUnderTest
	public BookStoreTestEvnSQL service = new BookStoreTestEvnSQL();
	
    @Override
    public void doWork() throws Exception {
        super.doWork();
        jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<ColumnReponse>>(){});
    }
	
	//验证结果
    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){	
			//验证json中返回字段
			log.info("验证栏目返回的数据：");	
			//int size = Integer.valueOf(paramMap.get("end"))-Integer.valueOf(paramMap.get("start")) + 1;
			//ColumnReponse dbResponse = BookStoreCommSQL.getColumnReponse(paramMap.get("columnType"), size, false);
			//dataVerifyManager.add(new ResponseVerify(jsonResult.getData(), dbResponse));
			
		}
        super.dataVerify();    	
	}
}
