package com.dangdang.readerV5.homepage;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.BaseComment.meta.CloudReadingProgress;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.comment.CloudReadingProgressDb;
import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.GetCurrentReadingBookInfoReponse;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * Created by cailianjie on 2016-3-23.
 */
public class GetCurrentReadingBookInfo extends FixtureBase{

    ReponseV2<GetCurrentReadingBookInfoReponse> reponseResult;

    public GetCurrentReadingBookInfo(){}

    public ReponseV2<GetCurrentReadingBookInfoReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult= JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetCurrentReadingBookInfoReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            Long lastReadBookId = CloudReadingProgressDb.getLastReadBook(VariableStore.get(paramMap.get("userPubId")).toString());
            if(lastReadBookId!=null) {
                dataVerifyManager.add(new ValueVerify<Long>(reponseResult.getData().getBookInfo().getMediaId(), lastReadBookId));
            }
            else{
                dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getBookInfo(),null), VerifyResult.SUCCESS);
            }
        }
        else{
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getBookInfo(),null), VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
