package com.dangdang.readerV5.channel;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.BookListBookUpdateResponse;

/**
 * 
 * @author guohaiying
 *
 */
public class BookListBookUpdate extends FixtureBase{
	ReponseV2<BookListBookUpdateResponse> jsonResult;
	
	public BookListBookUpdate(){
		addAction("bookListBookUpdate");
	}
		
    @Override
    public void doWork() throws Exception {
        super.doWork();
        jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<BookListBookUpdateResponse >>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
        	//List<UrlList>  actual = ChannelBgImgDb.getBackImg(paramMap.get("type"));
            //dataVerifyManager.add(new ListVerify(jsonResult.getData().getUrlList(),actual, true));
            
        }
        super.dataVerify();
    }
}
