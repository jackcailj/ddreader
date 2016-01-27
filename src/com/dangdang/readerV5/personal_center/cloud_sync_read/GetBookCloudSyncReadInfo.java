package com.dangdang.readerV5.personal_center.cloud_sync_read;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.BaseComment.meta.CloudBookMark;
import com.dangdang.BaseComment.meta.CloudBookNote;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.db.comment.CloudSyncSql;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.GetBookCloudSyncReadInfoReponse;

import java.util.List;

/**
 * Created by cailianjie on 2015-9-22.
 */
public class GetBookCloudSyncReadInfo extends FixtureBase{

    ReponseV2<GetBookCloudSyncReadInfoReponse> reponseResult;

    public GetBookCloudSyncReadInfo(){}

    public ReponseV2<GetBookCloudSyncReadInfoReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult= JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetBookCloudSyncReadInfoReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            List<CloudBookMark> bookMarks = CloudSyncSql.getCloudBookMarks(login.getCustId(),paramMap.get("productId"));
            List<CloudBookNote> bookNotes =CloudSyncSql.getCloudBookNotes(login.getCustId(), paramMap.get("productId"));

            for(CloudBookMark bookMark :bookMarks){
                bookMark.setBookModVersion(null);
            }

            for(CloudBookNote bookNote :bookNotes){
                bookNote.setBookModVersion(null);
            }

            dataVerifyManager.add(new ListVerify(reponseResult.getData().getMarkInfo(), bookMarks,true));
            dataVerifyManager.add(new ListVerify(reponseResult.getData().getNoteInfo(), bookNotes,true));
            dataVerifyManager.add(new ValueVerify<String>(reponseResult.getData().getProductId().toString(), paramMap.get("productId")));
        }
        else{
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getMarkInfo(),null), VerifyResult.SUCCESS);
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getNoteInfo(),null), VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
