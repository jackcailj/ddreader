package com.dangdang.readerV5.bookstore;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.autotest.common.ResponseVerify;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.BookStoreCommSQL;
import com.dangdang.digital.BookStoreTestEvnSQL;
import com.dangdang.readerV5.reponse.ColumnReponse;
import com.dangdang.readerV5.reponse.MediaList;
import com.dangdang.readerV5.reponse.SaleList;

import fitnesse.slim.SystemUnderTest;

/**
 * 书城栏目接口
 * @author guohaiying
 *
 */
public class Column extends FixtureBase{
	ReponseV2<ColumnReponse> reponseResult;
	
	@SystemUnderTest
	public BookStoreTestEvnSQL service = new BookStoreTestEvnSQL();
	
	//验证结果
	public boolean verifyResult() throws Exception{
		dataVerifyManager.setCaseExpectResult(true);
		reponseResult =JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<ColumnReponse>>(){});
		if(reponseResult.getStatus().getCode()==0){		
			//验证json中返回字段
			log.info("验证栏目返回的数据：");	
			int size = Integer.valueOf(paramMap.get("end"))-Integer.valueOf(paramMap.get("start")) + 1;
			ColumnReponse dbResponse = BookStoreCommSQL.getColumnReponse(paramMap.get("columnType"), size, false);
			dataVerifyManager.add(new ResponseVerify(reponseResult.getData(), dbResponse));
			
//			dataVerifyManager.add(new ValueVerify<String>(dbResponse.getColumnCode(), reponseResult.getData().getColumnCode()).setVerifyContent("验证columnCode"));
//			dataVerifyManager.add(new ValueVerify<String>(dbResponse.getCount(),reponseResult.getData().getCount()).setVerifyContent("验证count"));			
//			dataVerifyManager.add(new ValueVerify<String>(dbResponse.getName(),reponseResult.getData().getName()).setVerifyContent("验证name"));
//			dataVerifyManager.add(new ValueVerify<String>(dbResponse.getTips(),reponseResult.getData().getTips()).setVerifyContent("验证tips"));
//			dataVerifyManager.add(new ValueVerify<String>(dbResponse.getTotal(),reponseResult.getData().getTotal()).setVerifyContent("验证total"));
//			
//			for(int i=0; i<dbResponse.getSaleList().size(); i++){
//				SaleList db = dbResponse.getSaleList().get(i);
//				SaleList json = reponseResult.getData().getSaleList().get(i);
//				dataVerifyManager.add(new ValueVerify<String>(db.getIsStore(),json.getIsStore()).setVerifyContent("验证isStore"));
//				dataVerifyManager.add(new ValueVerify<String>(db.getIsSupportFullBuy(), json.getIsSupportFullBuy()).setVerifyContent("验证isSupportFullBuy"));
//				dataVerifyManager.add(new ValueVerify<String>(db.getPrice(), json.getPrice()).setVerifyContent("验证price"));
//				dataVerifyManager.add(new ValueVerify<String>(db.getSaleId(), json.getSaleId()).setVerifyContent("验证saleId"));
//				dataVerifyManager.add(new ValueVerify<String>(db.getType(), json.getType()).setVerifyContent("验证type"));				
//				dataVerifyManager.add(new ListVerify(db.getMediaList(), json.getMediaList(), true).setVerifyContent("验证mediaList"));
//				for(int j=0; j<db.getMediaList().size(); j++){
//					MediaList mdb = db.getMediaList().get(j);
//					MediaList mjson = json.getMediaList().get(j);
//					dataVerifyManager.add(new ValueVerify<String>(mdb.getAuthorId(),mjson.getAuthorId()).setVerifyContent("验证authorId"));
//					dataVerifyManager.add(new ValueVerify<String>(mdb.getAuthorPenname(),mjson.getAuthorPenname()).setVerifyContent("验证authorPenname"));
//					dataVerifyManager.add(new ValueVerify<String>(mdb.getChapterCnt(),mjson.getChapterCnt()).setVerifyContent("验证chapterCnt"));
//					dataVerifyManager.add(new ValueVerify<String>(mdb.getDescs(),mjson.getDescs()).setVerifyContent("验证descs"));
//					dataVerifyManager.add(new ValueVerify<String>(mdb.getIsFull(),mjson.getIsFull()).setVerifyContent("验证isFull"));
//					dataVerifyManager.add(new ValueVerify<String>(mdb.getIsStore(),mjson.getIsStore()).setVerifyContent("验证isStore"));
//					dataVerifyManager.add(new ValueVerify<String>(mdb.getMediaId(),mjson.getMediaId()).setVerifyContent("验证mediaId"));
//					dataVerifyManager.add(new ValueVerify<String>(mdb.getMediaType(),mjson.getMediaType()).setVerifyContent("验证mediaType"));
//					dataVerifyManager.add(new ValueVerify<String>(mdb.getRecommandWords(),mjson.getRecommandWords()).setVerifyContent("验证recommandWords"));
//					dataVerifyManager.add(new ValueVerify<String>(mdb.getSaleId(),mjson.getSaleId()).setVerifyContent("验证saleId"));
//					dataVerifyManager.add(new ValueVerify<String>(mdb.getTitle(),mjson.getTitle()).setVerifyContent("验证title"));
//					
//				}
			}
//		}
		return dataVerifyManager.dataVerify();      	
	}
}
