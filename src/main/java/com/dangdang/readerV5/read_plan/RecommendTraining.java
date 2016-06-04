package com.dangdang.readerV5.read_plan;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.BaseComment.meta.TagRelation;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.comment.TagRelationDb;
import com.dangdang.db.digital.MediaTrainingDb;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.Util;
import com.dangdang.digital.meta.MediaTraining;
import com.dangdang.enumeration.TagContentType;
import com.dangdang.readerV5.reponse.Items;
import com.dangdang.readerV5.reponse.RecommendTrainingReponse;

import java.util.List;

/**
 * Created by cailianjie on 2016-4-15.
 */
public class RecommendTraining extends FixtureBase{

    ReponseV2<RecommendTrainingReponse> reponseResult;

    public RecommendTraining(){}

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<RecommendTrainingReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){

            List<TagRelation> recommentTrainings = TagRelationDb.getTagRelation(paramMap.get("tagId"), TagContentType.TRAINING);

            List<String> expectPlanIds = Util.getFields(recommentTrainings,"sourceId");
            List<String> returnPlanIds = Util.getFields(reponseResult.getData().getTrainings(),"mtId");

            Integer pageNo=0;
            Integer pageSize=10;
            try{
                pageNo = Integer.parseInt(paramMap.get("pageNo"));
                pageSize = Integer.parseInt(paramMap.get("pageSize"));
            }catch (Exception e){

            }

            if(expectPlanIds.size()>0) {
                List<MediaTraining> trainings = MediaTrainingDb.getTrainings(expectPlanIds, true, pageNo,pageSize);

                expectPlanIds = Util.getFields(trainings, "mtId");
            }
            dataVerifyManager.add(new ListVerify(returnPlanIds, expectPlanIds, false).setVerifyContent("验证返回的计划id正确"));
        }

        super.dataVerify();
    }
}
