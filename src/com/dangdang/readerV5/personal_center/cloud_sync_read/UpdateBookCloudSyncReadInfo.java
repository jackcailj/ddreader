package com.dangdang.readerV5.personal_center.cloud_sync_read;

import com.dangdang.autotest.common.FixtureBase;

/**
 * Created by cailianjie on 2015-9-21.
 */
public class UpdateBookCloudSyncReadInfo extends FixtureBase{

    public UpdateBookCloudSyncReadInfo(){}


    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){

        }
        super.dataVerify();
    }
}
