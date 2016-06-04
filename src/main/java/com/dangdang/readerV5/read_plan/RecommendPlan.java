package com.dangdang.readerV5.read_plan;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.BaseComment.meta.TagRelation;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.comment.TagInfoDb;
import com.dangdang.db.comment.TagRelationDb;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.Util;
import com.dangdang.enumeration.TagContentType;
import com.dangdang.readerV5.reponse.RecommendPlanReponse;

import java.util.List;

/**
 * Created by cailianjie on 2016-4-15.
 */
public class RecommendPlan extends FixtureBase{

    ReponseV2<RecommendPlanReponse> reponseResult;

    public  RecommendPlan(){}

    public ReponseV2<RecommendPlanReponse> getReponseResult() {
        return reponseResult;
    }


    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<RecommendPlanReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){

            List<TagRelation> recommentPlans = TagRelationDb.getTagRelation(paramMap.get("tagId"), TagContentType.PLAN);

            List<String> expectPlanIds = Util.getFields(recommentPlans,"sourceId");
            List<String> returnPlanIds = Util.getFields(reponseResult.getData().getPlans(),"planId");

            dataVerifyManager.add(new ListVerify(returnPlanIds,expectPlanIds,false).setVerifyContent("验证返回的计划id正确"));
        }
        super.dataVerify();
    }
}
