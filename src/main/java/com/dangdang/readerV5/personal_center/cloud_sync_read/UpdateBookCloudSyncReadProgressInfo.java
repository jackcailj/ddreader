package com.dangdang.readerV5.personal_center.cloud_sync_read;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.BaseComment.meta.CloudReadingProgress;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.comment.CloudSyncSql;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.RecordVerify;

import java.util.List;

/**
 * Created by cailianjie on 2015-9-22.
 */
public class UpdateBookCloudSyncReadProgressInfo extends FixtureBase{



    public UpdateBookCloudSyncReadProgressInfo(){}

    @Override
    protected void genrateVerifyData() throws Exception {
        if(isEXCEPTSUCCESS()){
            List<CloudReadingProgress> params=JSONObject.parseArray(paramMap.get("progressInfo"), CloudReadingProgress.class);
            for(CloudReadingProgress param: params) {
                CloudReadingProgress progress = CloudSyncSql.getCloudReadingProgress(login.getCustId(), "" + param.getProductId());
                progress.setReadingTime(progress.getReadingTime() + param.getReadingTime());
                progress.setCreateTime(null);
                progress.setLastModifiedTime(null);
                dataVerifyManager.add(new RecordVerify(Config.BSAECOMMENT, progress));
            }
        }
    }

    @Override
    protected void dataVerify() throws Exception {

        super.dataVerify();
    }
}
