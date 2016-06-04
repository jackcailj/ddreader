package com.dangdang.readerV5.read_activitiy;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.RecommendActivityListReponse;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by cailianjie on 2016-4-28.
 */
public class RecommendActivityList extends FixtureBase{

    ReponseV2<RecommendActivityListReponse> reponseResult;

    public RecommendActivityList(){}

    public ReponseV2<RecommendActivityListReponse> getReponseResult() {
        return reponseResult;
    }


    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<RecommendActivityListReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
            if(StringUtils.isBlank(paramMap.get("flag"))) {
                //dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getActivityList(), null), VerifyResult.FAILED);
            }
        }
        super.dataVerify();
    }
}
