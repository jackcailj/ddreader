package com.dangdang.reader.functional.purchaseAndDownload;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ecms.meta.OrderForm;
import com.dangdang.param.parse.ParseParamUtil;

import java.util.Map;

/**
 * Created by cailianjie on 2015-5-21.
 */
public class GetSmsOrderStatus extends FunctionalBaseEx{



    ReponseV2<Map<String,Object>> reponseResult;

    public GetSmsOrderStatus(Map<String,String> param){
        super(param);
        addAction("getSmsOrderStatus");
    }

    @Override
    protected void parseParam() throws Exception {
        setLogin(ParseParamUtil.parseLogin(paramMap));
        ParseParamUtil.removeBlackParam(paramMap);

        if(login!=null && paramMap.get("orderNo").equals("auto")){

            SmsCallback smsCallback = new SmsCallback(login,isEXCEPTSUCCESS()?1:2);
            smsCallback.setEXCEPTSUCCESS(true);
            smsCallback.doWorkAndVerify();;

            paramMap.put("orderNo",smsCallback.getParamObject().getApp_orderid());
        }
    }

    public ReponseV2<Map<String, Object>> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();
        reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<Map<String, Object>>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0) {
            OrderForm orderForm = DbUtil.selectOne(Config.ECMSDBConfig, "select * from order_form where ORDER_NO='" + paramMap.get("orderNo").toString() + "'", OrderForm.class);
            dataVerifyManager.add(new ValueVerify<String>(reponseResult.getData().get("result").toString(), (orderForm.getPayState() == 1 ? "true" : "false")), VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
