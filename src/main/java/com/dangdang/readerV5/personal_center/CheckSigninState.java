package com.dangdang.readerV5.personal_center;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.SignDb;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.SigninDetail;
import com.dangdang.digital.meta.SigninMain;
import com.dangdang.readerV5.reponse.CheckSigninStateReponse;



import javax.persistence.criteria.CriteriaBuilder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cailianjie on 2015-8-24.
 */
public class CheckSigninState extends FixtureBase{

    ReponseV2<CheckSigninStateReponse> reponseResult;

    public CheckSigninState(){}

    public ReponseV2<CheckSigninStateReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult= JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<CheckSigninStateReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
            dataVerifyManager.add(new ValueVerify<String>(reponseResult.getData().getIsSign().toString().toLowerCase(),paramMap.get("flag").toLowerCase()));

            int signCount= SignDb.getSigninCountPerDay();
            dataVerifyManager.add(new ValueVerify<Integer>(reponseResult.getData().getTotalNum(),signCount).setVerifyContent("验证每日签到总数是否一致"));

            List<SigninDetail> signinDetails = SignDb.getSigninDetail(login.getCustId());
            List<String> sidninDates=new ArrayList<String>();
            for(SigninDetail signinDetail : signinDetails){
                SimpleDateFormat sdp =new SimpleDateFormat("yyyyMMdd");
                sidninDates.add(sdp.format(signinDetail.getSigninTime()));
            }

            dataVerifyManager.add(new ListVerify(reponseResult.getData().getSigninCalendar(),sidninDates,false).setVerifyContent("验证签到记录是否一致"));


            SigninMain signinMain= SignDb.getSignRecord(login.getCustId());

            dataVerifyManager.add(new ValueVerify<Integer>(reponseResult.getData().getContinueDays(),signinMain.getContinueDays()).setVerifyContent("验证连续签到天数是否一致"));
        }
        else{
            dataVerifyManager.add(new ValueVerify<Object>(null,reponseResult.getData().getIsSign()), VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
