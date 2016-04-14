package com.dangdang.readerV5.bookstore;

import java.util.List;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.ChannelBgImgDb;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.GetMediaReponse;
import com.dangdang.readerV5.reponse.UrlList;

/**
 * 电子书单品页接口
 * @author guohaiying
 *
 */
public class GetMedia extends FixtureBase{

	ReponseV2<GetMediaReponse> jsonResult;
		
    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
        	List<UrlList>  actual = ChannelBgImgDb.getBackImg(paramMap.get("type"));
           // dataVerifyManager.add(new ListVerify(jsonResult.getData().getUrlList(),actual, true));           
        }
        super.dataVerify();
    }
}
