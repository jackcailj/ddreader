package com.dangdang.readerV5.bookstore;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.BookStoreCommSQL;
import com.dangdang.digital.BookStoreTestEvnSQL;
import com.dangdang.readerV5.reponse.ColumnReponse;
import com.dangdang.readerV5.reponse.MediaCategoryReponse;

import fitnesse.slim.SystemUnderTest;

/**
 * 分类一级页面
 * @author guohaiying
 *
 */
public class MediaCategory extends FixtureBase{
	
	ReponseV2<MediaCategoryReponse> reponseResult;
	
	@SystemUnderTest
	public BookStoreTestEvnSQL service = new BookStoreTestEvnSQL();
	
	//验证结果
	public boolean verifyResult() throws Exception{
		dataVerifyManager.setCaseExpectResult(true);
		reponseResult =JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<MediaCategoryReponse>>(){});
		if(reponseResult.getStatus().getCode()==0){		
			//验证json中返回字段
			log.info("验证分类一级页面的返回数据：");	
			int size = Integer.valueOf(paramMap.get("end"))-Integer.valueOf(paramMap.get("start")) + 1;
			//ColumnReponse dbResponse = BookStoreCommSQL.getColumnReponse(paramMap.get("columnType"), size);
			//dataVerifyManager.add(new ResponseVerify(reponseResult.getData(), dbResponse));
			//dataVerifyManager.add(new ValueVerify<MediaCategoryReponse>(reponseResult.getData(), dbResponse, true));
			
			
			//#SELECT * FROM `media_catetory` WHERE path LIKE ('DDDS%') AND `status`=1;
			//#SELECT * FROM media_catetory WHERE parent_id=596  AND `status`=1 ORDER BY index_order DESC
			//#SELECT * FROM media_catetory WHERE parent_id=149
			//#SELECT * FROM media_catetory WHERE `name` IN ('出版物','女频','男频','杂志') AND `status`=1 ORDER BY index_order DESC

			//#SELECT * FROM media_catetory WHERE `parent_id` IN (149)AND `status`=1 ORDER BY index_order DESC;
			//SELECT * FROM media_catetory WHERE `parent_id` IN (80)AND `status`=1 ORDER BY index_order DESC;
			//#SELECT * FROM media_catetory WHERE `parent_id` IN (79)AND `status`=1 ORDER BY index_order DESC;
			//#SELECT * FROM media_catetory WHERE `parent_id` IN (596)AND `status`=1 ORDER BY index_order DESC;
		}
		return dataVerifyManager.dataVerify();      	
	}
}
