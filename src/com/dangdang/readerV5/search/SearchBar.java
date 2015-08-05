package com.dangdang.readerV5.search;

import com.dangdang.autotest.common.FixtureBase;

/**
 * Created by cailianjie on 2015-8-4.
 */
public class SearchBar extends FixtureBase {
    public SearchBar(){}

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
            
        }
        super.dataVerify();
    }
}
