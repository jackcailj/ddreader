package com.dangdang.readerV5.personal_center.cloud_sync_read;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.db.comment.CloudSyncSql;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.GetPersonalBookNoteInfoNoReponse;

/**
 * Created by cailianjie on 2015-9-22.
 */
public class GetPersonalBookNoteInfoNo extends FixtureBase{

    ReponseV2<GetPersonalBookNoteInfoNoReponse> reponseResult;

    public GetPersonalBookNoteInfoNo(){URL= Config.getMobileUrl();}


    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetPersonalBookNoteInfoNoReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
           /* int noteNo=0;
            try{
                noteNo=CloudSyncSql.getPersonalNoteCount(login.getCustId());
            }
            catch (Exception e){

            }

            dataVerifyManager.add(new ValueVerify<Integer>(reponseResult.getData().getBookNoteInfoNo(), noteNo));*/
        }
        else {
            dataVerifyManager.add(new ValueVerify<Integer>(reponseResult.getData().getBookNoteInfoNo(), null), VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
