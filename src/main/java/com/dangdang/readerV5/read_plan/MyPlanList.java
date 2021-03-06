package com.dangdang.readerV5.read_plan;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.common.functional.login.LoginManager;
import com.dangdang.db.digital.PlanProcessDb;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.PlanProcess;
import com.dangdang.readerV5.reponse.MyPlanListInfo;
import com.dangdang.readerV5.reponse.MyPlanListReponse;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by cailianjie on 2016-4-19.
 */
public class MyPlanList extends FixtureBase{

    ReponseV2<MyPlanListReponse> reponseResult;

    public MyPlanList(){}


    public ReponseV2<MyPlanListReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<MyPlanListReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {

        if(reponseResult.getStatus().getCode()==0){

            List<MyPlanListInfo> myPlanListInfos=null;
            if(StringUtils.isNotBlank(paramMap.get("pubCustId"))){
                myPlanListInfos = PlanProcessDb.getUserPlans(LoginManager.getLoginByPubID(paramMap.get("pubCustId")).getCustId(), paramMap.get("statusType"),
                        (StringUtils.isBlank(paramMap.get("pageDate")) ? new Date().getTime() : Long.parseLong(paramMap.get("pageDate"))),
                        StringUtils.isBlank(paramMap.get("pageSize")) ? null : Integer.parseInt(paramMap.get("pageSize")));
            }
            else {
                myPlanListInfos = PlanProcessDb.getUserPlans(login.getCustId(), paramMap.get("statusType"),
                        (StringUtils.isBlank(paramMap.get("pageDate")) ? new Date().getTime() : Long.parseLong(paramMap.get("pageDate"))),
                        StringUtils.isBlank(paramMap.get("pageSize")) ? null : Integer.parseInt(paramMap.get("pageSize")));

            }

            for(MyPlanListInfo info:myPlanListInfos){
                info.setDesc(null);
                info.setName(null);
            }
            dataVerifyManager.add(new ListVerify(reponseResult.getData().getMyPlanList(),myPlanListInfos,true));
        }
        else{
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getMyPlanList(),null), VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
