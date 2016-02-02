package com.dangdang.readerV5.bookstore;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.BorrowReponse;

public class Borrow extends FixtureBase{
	ReponseV2<BorrowReponse> jsonResult;
	
	@Override
	public void doWork() throws Exception {
		super.doWork();
		jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<BorrowReponse>>(){});
	}
	    
	@Override
	protected void dataVerify() throws Exception {
		if(reponseV2Base.getStatus().getCode()==0){	
			//String expectedCode = BlockDb.getBlock(paramMap.get("code"));
			//dataVerifyManager.add(new ValueVerify<String>(jsonResult.getData().getBlock(), expectedCode).setVerifyContent("验证块的code内容"));			
}
		super.dataVerify();    	
	}	
		
}
