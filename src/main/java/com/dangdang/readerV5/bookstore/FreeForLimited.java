package com.dangdang.readerV5.bookstore;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.MediaColumnContentDb;
import com.dangdang.db.digital.MediaColumnDb;
import com.dangdang.db.digital.MediaDb;
import com.dangdang.db.digital.MediaSaleDb;
import com.dangdang.ddframework.dataverify.ExpressionVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.Media;
import com.dangdang.digital.meta.MediaColumn;
import com.dangdang.digital.meta.MediaSale;
import com.dangdang.readerV5.reponse.FreeForLimitedReponse;
import com.dangdang.readerV5.reponse.MediaList;
import com.dangdang.readerV5.reponse.SaleList;


public class FreeForLimited extends FixtureBase{
	ReponseV2<FreeForLimitedReponse> jsonResult;   
	 
	public FreeForLimited(){
		addAction("freeforlimited");
	}
	
    @Override
    public void doWork() throws Exception {
        super.doWork();
        jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<FreeForLimitedReponse>>(){});
    }
    
    @Override
    protected void dataVerify() throws Exception {
    	if(reponseV2Base.getStatus().getCode()==0){	
    	
    	}
        super.dataVerify();    	
	}	

}
