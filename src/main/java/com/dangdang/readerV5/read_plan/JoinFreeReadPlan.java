package com.dangdang.readerV5.read_plan;

import com.dangdang.authority.meta.MediaAuthority;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.common.functional.login.ILogin;
import com.dangdang.config.Config;
import com.dangdang.db.authority.AuthorityDb;
import com.dangdang.db.digital.PlanDetailDb;
import com.dangdang.ddframework.dataverify.*;
import com.dangdang.ddframework.util.Util;
import com.dangdang.digital.meta.PlanOrder;
import com.dangdang.digital.meta.PlanOrderDetail;
import com.dangdang.digital.meta.PlanProcess;
import com.dangdang.readerV5.personal_center.GetAccountInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cailianjie on 2016-4-6.
 */
public class JoinFreeReadPlan extends FixtureBase{


    GetAccountInfo master;

    List<MediaAuthority> beforeBuyMedias;

    public JoinFreeReadPlan(){}

    public JoinFreeReadPlan(ILogin user,String planId){
        paramMap.put("token",user.getToken());
        paramMap.put("planId",planId);
    }


    @Override
    protected void beforeParseParam() throws Exception {
        super.beforeParseParam();

        if(login!=null) {
            master = new GetAccountInfo(login, true);
            master.doWorkAndVerify();
            Long planId=null;
            if(StringUtils.isNotBlank(paramMap.get("planId"))) {
                planId = Long.parseLong(paramMap.get("planId"));
            }
            Long custId=Long.parseLong(login.getCustId());


            beforeBuyMedias = AuthorityDb.getUserEbooks(login.getCustId());


            //验证没有生成订单
            PlanOrder planOrder = new PlanOrder();
            planOrder.setCustId(custId);
            planOrder.setPlanId(planId);
            planOrder.setTotalPrice(0l);

            dataVerifyManager.add( new RecordExVerify(Config.YCDBConfig,planOrder,"po_id"," from plan_order").setVerifyContent("验证plan_order数据正确"),VerifyResult.FAILED);


        }
    }

    @Override
    protected void dataVerify() throws Exception {

        if(reponseV2Base.getStatus().getCode()==0){
            //Thread.sleep(10000);

            //获取购买后用户帐户金额
            GetAccountInfo afterMaster = new GetAccountInfo(login,true);
            afterMaster.doWorkAndVerify();

            dataVerifyManager.add(new ValueVerify<Long>(afterMaster.getReponseMasterResult().getData().getAccountTotal(),master.getReponseMasterResult().getData().getAccountTotal())
                    .setVerifyContent("验证金铃铛扣款正确"));


            //验证plan_progress中有计划记录
            PlanProcess planProcess = new PlanProcess();
            planProcess.setCustId(Long.parseLong(login.getCustId()));
            planProcess.setPlanId(Long.parseLong(paramMap.get("planId")));


            dataVerifyManager.add(new RecordVerify(Config.YCDBConfig,planProcess));



            //验证计划中书籍都有购买权限
            List<MediaAuthority> buyMedias = AuthorityDb.getUserEbooks(login.getCustId());

            dataVerifyManager.add(new ListVerify(buyMedias,beforeBuyMedias,true).setVerifyContent("验证书籍权限正确"));


        }
        else
        {

        }
        super.dataVerify();
    }
}
