package com.dangdang.readerV5.read_plan;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.PlanProcessDetailDb;
import com.dangdang.db.digital.UserReadProgressTrainingInfoEx;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.GetUserMultiReadProgressReponse;
import com.dangdang.readerV5.reponse.UserReadProgressInfo;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by cailianjie on 2016-4-19.
 */
public class GetUserMultiReadProgress extends FixtureBase{

    ReponseV2<GetUserMultiReadProgressReponse> reponseResult;

    public GetUserMultiReadProgress(){}

    public ReponseV2<GetUserMultiReadProgressReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetUserMultiReadProgressReponse>>(){});


    }


    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){

            List<UserReadProgressTrainingInfoEx> userReadProgressTrainingInfoExes = PlanProcessDetailDb.getAllUserReadProcessTrainings(login.getCustId(), StringUtils.isBlank(paramMap.get("planStatus"))?"0,1,2":paramMap.get("planStatus"));

            Map<Long,UserReadProgressInfo> map =new TreeMap<>();
            List<UserReadProgressInfo> userReadProgressInfos = new ArrayList<>();
            for(UserReadProgressTrainingInfoEx infoEx:userReadProgressTrainingInfoExes){
                UserReadProgressInfo userReadProgressInfo;
                if(!map.containsKey(infoEx.getPlanId())) {
                    userReadProgressInfo = new UserReadProgressInfo();
                    userReadProgressInfo.setPlanId(infoEx.getPlanId());
                    //userReadProgressInfo.setCustId(infoEx.getCustId().toString());
                    userReadProgressInfo.setName(infoEx.getName());
                    userReadProgressInfo.setDesc(infoEx.getDesc());
                    userReadProgressInfo.setFinishReadRate(infoEx.getFinishReadRate());
                    userReadProgressInfos.add(userReadProgressInfo);
                    map.put(infoEx.getPlanId(),userReadProgressInfo);

                }else{
                    userReadProgressInfo = map.get(infoEx.getPlanId());
                }

                if(infoEx.getTrainingId()!=null) {

                    if(userReadProgressInfo.getTrainingReadProgress()==null) {
                        List<UserReadProgressTrainingInfo> trainingReadProgress = new ArrayList<>();
                        userReadProgressInfo.setTrainingReadProgress(trainingReadProgress);
                    }

                    UserReadProgressTrainingInfo trainingInfo = new UserReadProgressTrainingInfo();
                    trainingInfo.setPlanId(infoEx.getPlanId());
                    trainingInfo.setTrainingId(infoEx.getTrainingId());
                    //trainingInfo.setCustId(infoEx.getCustId());
                    trainingInfo.setChapter(infoEx.getChapter());
                    trainingInfo.setChapterOffset(infoEx.getChapterOffset());
                    trainingInfo.setChapterOffsetStart(infoEx.getChapterOffsetStart());
                    trainingInfo.setChapterStart(infoEx.getChapterStart());
                    trainingInfo.setLastSynTime(infoEx.getLastSynTime());
                    trainingInfo.setMediaId(infoEx.getMediaId());
                    //trainingInfo.setTrainingStatus(infoEx.getStatus());
                    trainingInfo.setReadTime(infoEx.getReadTime());
                    userReadProgressInfo.getTrainingReadProgress().add(trainingInfo);
                }

            }

            dataVerifyManager.add(new ListVerify(reponseResult.getData().getMultiReadProgressInfos(),userReadProgressInfos,true));

        }
        super.dataVerify();
    }
}
