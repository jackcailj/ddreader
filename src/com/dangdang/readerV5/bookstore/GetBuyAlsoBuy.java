package com.dangdang.readerV5.bookstore;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.MediaSaleDb;
import com.dangdang.ddframework.dataverify.ExpressionVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.MediaSale;
import com.dangdang.readerV5.reponse.GetBuyAlsoBuyReponse;
import com.dangdang.readerV5.reponse.MediaList;

/**
 * 买了又买接口
 * @author guohaiying
 */
public class GetBuyAlsoBuy extends FixtureBase{
	ReponseV2<GetBuyAlsoBuyReponse> jsonResult;
	
    @Override
    public void doWork() throws Exception {
        super.doWork();
        jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetBuyAlsoBuyReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
        	List<MediaList> actualMediaList = jsonResult.getData().getMediaList();
        	for(int i=0; i<actualMediaList.size(); i++){        		
        		String actualSaleId = String.valueOf(actualMediaList.get(i).getSaleId());
        		MediaSale expectedMediaSale = MediaSaleDb.getMediaSale(actualSaleId);
        		//验证coverPic,title,authorPenname,price
        		//dataVerifyManager.add(new ExpressionVerify(size>0).setVerifyContent("验证数量 "+size));
        	}
        	
        	//dataVerifyManager.add(new ExpressionVerify(size>0).setVerifyContent("验证数量 "+size));           
        }
        super.dataVerify();
    }

}
