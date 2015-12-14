package com.dangdang.readerV5.bookstore;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.GetMediaReponse;

import fitnesse.slim.SystemUnderTest;

/**
 * 电子书单品页接口
 * @author guohaiying
 *
 */
public class GetMedia extends FixtureBase{

	ReponseV2<GetMediaReponse> reponseResult;
	
	@SystemUnderTest
	//public BookStoreTestEvnSQL service = new BookStoreTestEvnSQL();
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception{
		String saleIdName = params.get("saleId");
		String saleID = "";
		if(saleIdName.equals("可借阅")) 
	//		saleID = BookStoreCommSQL.getBorrowBook();
//		else if(saleIdName.equals("已借阅"))
//			//
//		else if(saleIdName.equals("借阅过期"))
//			
//		else if(saleIdName.equals("免费"))
//		else if(saleIdName.equals("已获取免费"))
//		else if(saleIdName.equals("购买"))
//		else if(saleIdName.equals("已购买"))
//		else if(saleIdName.equals("包月"))
//		else if(saleIdName.equals("已获取包月"))
//		else if(saleIdName.equals("免费原创"))
//		else if(saleIdName.equals("已获取免费原创"))
//		else if(saleIdName.equals("购买章节"))
//		else if(saleIdName.equals("已获取购买章节"))
//		else if(saleIdName.equals("原创包月"))
//		else if(saleIdName.equals("已获取原创包月"))
		params.put("saleId",saleID);
		super.setParameters(params);
	}
	
	//验证结果
	public boolean verifyResult() throws Exception{
		dataVerifyManager.setCaseExpectResult(true);
		reponseResult =JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetMediaReponse>>(){});
		if(reponseResult.getStatus().getCode()==0){		
			//验证json中返回字段
			int custID = Integer.valueOf(login.getCustId());
			log.info("验证分类一级页面的返回数据：");	
		//	GetMediaReponse dbResponse = BookStoreCommSQL.getMediaReponse(Integer.valueOf(paramMap.get("saleId")),custID);
		//	dataVerifyManager.add(new ValueVerify<GetMediaReponse>(reponseResult.getData(), dbResponse, true));
			
		}
		return dataVerifyManager.dataVerify();      	
	}
}
