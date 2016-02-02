package com.dangdang.reader.functional.personcenter;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.autotest.common.PlatForm;
import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.core.TestDevice;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.StringUtil;
import com.dangdang.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.GetPaymentReponse;
import com.dangdang.reader.functional.reponse.PayMent;
import com.dangdang.db.digital.MediaActivityInfoDb;

import java.util.List;
import java.util.Map;

/**
 * Created by cailianjie on 2015-5-26.
 */
public class GetPayment extends FunctionalBaseEx{



    ReponseV2<GetPaymentReponse> reponseResult;


    public GetPayment(Map<String,String> param){
        super(param);
        addAction("getPayment");
        URL= Config.getTempUrl();
    }

    public ReponseV2<GetPaymentReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    protected void parseParam() throws Exception {
        setLogin(ParseParamUtil.parseLogin(paramMap));
        ParseParamUtil.removeBlackParam(paramMap);
        if(paramMap.get("fromPaltform")!=null && paramMap.get("fromPaltform").toString().equals("auto")){
            paramMap.put("fromPaltform",PlatForm.getPlatForm(Config.getDevice()).toString());
        }



    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetPaymentReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            List<PayMent> payMentList=null;
            if(StringUtil.isBlank(paramMap.get("fromPaltform"))){
                payMentList = MediaActivityInfoDb.getPayment(PlatForm.getPlatForm(TestDevice.ANDROID));
            }
            else {
                payMentList = MediaActivityInfoDb.getPayment(PlatForm.getPlatForm(Config.getDevice()));
            }

            dataVerifyManager.add(new ListVerify(payMentList, reponseResult.getData().getPayment(), true));
        }
        super.dataVerify();
    }
}
