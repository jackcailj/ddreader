package com.dangdang.readerV5.search;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;

/**
 * Created by cailianjie on 2015-8-4.
 */
public class SearchAssociate extends FixtureBase {
    public SearchAssociate(){}

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){

        }
        else{
            //dataVerifyManager.add(new ValueVerify<Object>());
        }


        super.dataVerify();
    }
}
