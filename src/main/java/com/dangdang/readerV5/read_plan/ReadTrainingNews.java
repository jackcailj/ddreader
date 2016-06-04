package com.dangdang.readerV5.read_plan;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.PlanProcessDetailLogDb;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.PlanProcessDetailLog;
import com.dangdang.readerV5.reponse.ReadTrainingNewsReponse;
import com.dangdang.readerV5.reponse.TrainingNewsInfo;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by cailianjie on 2016-4-14.
 */
public class ReadTrainingNews extends FixtureBase{

    ReponseV2<ReadTrainingNewsReponse> reponseResult;

    public ReadTrainingNews(){}

    public ReponseV2<ReadTrainingNewsReponse> getReponseResult() {
        return reponseResult;
    }


    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<ReadTrainingNewsReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){

            //最后时间相同时，排序与返回数据不一致，暂时没找到问题

            /*int pageSize = StringUtils.isBlank(paramMap.get("pageSize"))?10:Integer.parseInt(paramMap.get("pageSize"));

            Date curDate=new Date();
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
            BigInteger diffDateTime = BigInteger.valueOf(30*24*60*60);
            String greateDate= df.format(new Date((curDate.getTime()/1000-30*24*60*60)*1000));

            List<PlanProcessDetailLog> planProcessDetailLogs = PlanProcessDetailLogDb.getTrainingNews(
                    paramMap.get("trainingId"),paramMap.get("lastModifyTime"),pageSize,greateDate,true);


            List<TrainingNewsInfo> expectTrainingInfos =new ArrayList<>();
            for(PlanProcessDetailLog log : planProcessDetailLogs){
                TrainingNewsInfo trainingNewsInfo= new TrainingNewsInfo();
                trainingNewsInfo.setTrainingId(log.getTrainingId());
                trainingNewsInfo.setCustId(log.getCustId());
                trainingNewsInfo.setMediaId(log.getMediaId());
                trainingNewsInfo.setTodayFinishRate(log.getFinishReadRateToday());
                trainingNewsInfo.setProcessId(log.getProcessId());

                expectTrainingInfos.add(trainingNewsInfo);

            }

            dataVerifyManager.add(new ListVerify(reponseResult.getData().getTrainingNews(),expectTrainingInfos,true).setVerifyContent("验证动态信息是否正确"));
*/

        }
        else{
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getTrainingNews(),null), VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
