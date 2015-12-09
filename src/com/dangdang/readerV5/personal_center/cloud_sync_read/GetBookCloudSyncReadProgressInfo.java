package com.dangdang.readerV5.personal_center.cloud_sync_read;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.BaseComment.meta.CloudReadingProgress;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.db.comment.CloudSyncSql;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.GetBookCloudSyncReadProgressInfoReponse;

/**
 * Created by cailianjie on 2015-9-22.
 */
public class GetBookCloudSyncReadProgressInfo extends FixtureBase{

    ReponseV2<GetBookCloudSyncReadProgressInfoReponse> reponseResult;

    public GetBookCloudSyncReadProgressInfo(){}

    public ReponseV2<GetBookCloudSyncReadProgressInfoReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult= JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetBookCloudSyncReadProgressInfoReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            CloudReadingProgress progresses=CloudSyncSql.getCloudReadingProgress(login.getCustId(), paramMap.get("productId"));
            if(progresses!=null) {
                progresses.setProgressId(null);
                dataVerifyManager.add(new ValueVerify<CloudReadingProgress>(reponseResult.getData().getBookReadingProgress(), progresses, true));

                dataVerifyManager.add(new ValueVerify<Long>(reponseResult.getData().getTotalReadingTime(), progresses.getReadingTime()));
            }
        }
        else{
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getBookReadingProgress(),null), VerifyResult.SUCCESS);
        }

        super.dataVerify();
    }
}
