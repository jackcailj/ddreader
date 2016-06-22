package com.dangdang.readerV5.read_plan;

import com.dangdang.authority.meta.MediaAuthority;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.db.authority.AuthorityDb;
import com.dangdang.db.digital.PlanDb;
import com.dangdang.db.digital.PlanDetailDb;
import com.dangdang.ddframework.dataverify.*;
import com.dangdang.ddframework.util.Util;
import com.dangdang.digital.meta.*;
import com.dangdang.readerV5.personal_center.GetAccountInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AutoPopulatingList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cailianjie on 2016-4-6.
 */
public class BuyReadPlan extends FixtureBase {

    GetAccountInfo master;
    Long planPrice=0l;
    List<MediaAuthority> planMedias=new ArrayList();


    public BuyReadPlan(){}


    @Override
    protected void beforeParseParam() throws Exception {
        super.beforeParseParam();

        if(login!=null) {
            master = new GetAccountInfo(login, true);
            master.doWorkAndVerify();

            Long planId =Long.parseLong(paramMap.get("planId"));
            Long custId=Long.parseLong(login.getCustId());

            List<PlanOrderDetail> orderDetails = new ArrayList();

            if(StringUtils.isNotBlank(paramMap.get("planId"))){
                //  获取计划价格
                List<PlanDetailInfo> details = PlanDetailDb.getPlanDetails(paramMap.get("planId"),false,false);
                //检查书籍是否有权限
                List<MediaAuthority> mediaAuthorities = AuthorityDb.getUserEbooks(login.getCustId());
                List<String> mediaIds = Util.getFields(mediaAuthorities,"productId");




                for(PlanDetailInfo detail:details){
                    if(!mediaIds.contains(detail.getMediaId().toString())){
                        planPrice+=Math.round(detail.getSalePrice()*100);

                        MediaAuthority authority = new MediaAuthority();
                        authority.setProductId(detail.getMediaId());
                        authority.setRelationType("1001");
                        authority.setAuthorityType((short) 1);
                        authority.setCustId(custId);
                        planMedias.add(authority);


                        PlanOrderDetail orderDetail = new PlanOrderDetail();
                        orderDetail.setPlanId(planId);
                        orderDetail.setCustId(custId);
                        orderDetail.setTrainingId(detail.getTrainingId());
                        orderDetail.setTrainingPrice(Math.round(detail.getSalePrice()*100));

                        orderDetails.add(orderDetail);
                        //dataVerifyManager.add(new RecordExVerify(Config.YCDBConfig,orderDetail,"pod_id"," from plan_order_detail ").setVerifyContent("验证plan_order_detail数据正确"));
                    }
                }
            }

            Plan plan = PlanDb.getPlan(paramMap.get("planId"));
            planPrice=((Double) (planPrice*plan.getDiscount())).longValue();

            PlanOrder planOrder = new PlanOrder();
            planOrder.setCustId(custId);
            planOrder.setPlanId(planId);
            planOrder.setTotalPrice(planPrice);

            dataVerifyManager.add( new RecordExVerify(Config.YCDBConfig,planOrder,"po_id"," from plan_order",orderDetails,"order_no").setVerifyContent("验证plan_order数据正确"));


        }



    }


    /*付费计划，校验价格正确
                   价格=书籍价格*设置折扣之和，有购买权限的数据集不算价格。
                   只能花费金铃铛，验证铃铛扣款正确
                   plan_progress数据正确
                   plan_order数据正确
                   书籍权限添加正确

         */
    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
            //Thread.sleep(10000);

            //获取购买后用户帐户金额
            GetAccountInfo afterMaster = new GetAccountInfo(login,true);
            afterMaster.doWorkAndVerify();

            dataVerifyManager.add(new ValueVerify<Long>(afterMaster.getReponseMasterResult().getData().getAccountTotal(),master.getReponseMasterResult().getData().getAccountTotal()-planPrice)
                    .setVerifyContent("验证金铃铛扣款正确"));


            //验证plan_progress中有计划记录
            PlanProcess planProcess = new PlanProcess();
            planProcess.setCustId(Long.parseLong(login.getCustId()));
            planProcess.setPlanId(Long.parseLong(paramMap.get("planId")));

            dataVerifyManager.add(new RecordVerify(Config.YCDBConfig,planProcess));



            //验证计划中书籍都有购买权限
            List<MediaAuthority> buyMedias = AuthorityDb.getUserEbooks(login.getCustId());

            dataVerifyManager.add(new ListVerify(buyMedias,planMedias,true,VerifyType.CONTAINS).setVerifyContent("验证书籍权限正确"));


        }
        else
        {

        }
        super.dataVerify();
    }
}
