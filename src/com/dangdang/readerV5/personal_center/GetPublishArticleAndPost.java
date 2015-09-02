package com.dangdang.readerV5.personal_center;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.MediaDigestDb;
import com.dangdang.digital.meta.MediaDigest;
import com.dangdang.readerV5.reponse.GetPublishArticleAndPostReponse;
import com.dangdang.ucenter.UserInfoSql;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by cailianjie on 2015-8-21.
 */
public class GetPublishArticleAndPost extends FixtureBase{

    ReponseV2<GetPublishArticleAndPostReponse> reponseResult;

    public GetPublishArticleAndPost(){}


    public ReponseV2<GetPublishArticleAndPostReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetPublishArticleAndPostReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            List<MediaDigest> mediaDigests = MediaDigestDb.getMyCreateMediaDigest(StringUtils.isBlank(paramMap.get("pubId"))?login.getCustId(): UserInfoSql.getCustIdByPubId(paramMap.get("pubId")),10);
            if(StringUtils.isBlank(paramMap.get("flag"))) {
                dataVerifyManager.add(new ListVerify(reponseResult.getData().getPublishList(), mediaDigests, true));
            }else{
                dataVerifyManager.add(new ValueVerify<Object>(null, reponseResult.getData().getPublishList()), VerifyResult.SUCCESS);
            }
        }
        else{
            dataVerifyManager.add(new ValueVerify<Object>(null, reponseResult.getData().getPublishList()), VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
