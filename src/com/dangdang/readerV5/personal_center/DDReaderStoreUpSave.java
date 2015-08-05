package com.dangdang.readerV5.personal_center;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.RegexVerify;
import com.dangdang.ddframework.util.Util;
import com.dangdang.digital.StoreUpType;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;

/**
 * Created by cailianjie on 2015-7-1.
 */
public class DDReaderStoreUpSave extends FixtureBase{

    public DDReaderStoreUpSave(){addAction("dDReaderStoreUpSave");}


    @Override
    protected void parseParam() throws Exception {
        setLogin(ParseParamUtil.parseLogin(paramMap));
    }


    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){

            StoreUpType type=StoreUpType.valueOf(paramMap.get("type").toUpperCase());
            DDReaderStoreUpList storeUpList = new DDReaderStoreUpList(login,type );
            storeUpList.doWorkAndVerify();

            String[] targetIds=paramMap.get("targetIds").split(",");

            if(type==StoreUpType.MEDIA) {
                for (String targetId : targetIds) {
                    dataVerifyManager.add(new RegexVerify(Util.getJsonRegexString("productId", targetId), storeUpList.getResult().toString()));
                }
            }
            else{
                for (String targetId : targetIds) {
                    dataVerifyManager.add(new RegexVerify(Util.getJsonRegexString("articleId", targetId), storeUpList.getResult().toString()));
                }
            }

        }
        super.dataVerify();
    }
}
