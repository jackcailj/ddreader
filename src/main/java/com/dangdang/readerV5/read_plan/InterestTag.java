package com.dangdang.readerV5.read_plan;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.BaseComment.meta.TagInfo;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.comment.TagInfoDb;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.enumeration.TagType;
import com.dangdang.readerV5.reponse.InterestTagReponse;

import java.util.List;

/**
 * Created by cailianjie on 2016-4-15.
 */
public class InterestTag extends FixtureBase{

    ReponseV2<InterestTagReponse> reponseResult;


    public InterestTag(){}


    public ReponseV2<InterestTagReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<InterestTagReponse>>(){});

    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){


            List<TagInfo> expectDatas= TagInfoDb.getPlanTags(TagType.PLANTAG,-1);

            dataVerifyManager.add(new ListVerify(reponseResult.getData().getTags(),expectDatas,true).setVerifyContent("验证返回的标签数据正确"));
        }
        super.dataVerify();
    }
}
