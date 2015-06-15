package com.dangdang.reader.functional.purchaseAndDownload;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.WxPayReponse;

import java.util.Map;

/**
 * Created by cailianjie on 2015-5-26.
 */
public class WxPay extends FunctionalBaseEx{



    ReponseV2<WxPayReponse> reponseResult;

    public WxPay(Map<String,String> param){
        super(param);
        addAction("wxPay");
    }

    public ReponseV2<WxPayReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    protected void parseParam() throws Exception {
        setLogin(ParseParamUtil.parseLogin(paramMap));
        ParseParamUtil.removeBlackParam(paramMap);
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();
        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<WxPayReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            //验证返回的值不为空
            dataVerifyManager.add(new ValueVerify<String>(reponseResult.getData().getNonceStr(),""), VerifyResult.FAILED);
            dataVerifyManager.add(new ValueVerify<String>(reponseResult.getData().getPackageValue(),""), VerifyResult.FAILED);
            dataVerifyManager.add(new ValueVerify<String>(reponseResult.getData().getPartnerId(),""), VerifyResult.FAILED);
            dataVerifyManager.add(new ValueVerify<String>(reponseResult.getData().getPrepayId(),""), VerifyResult.FAILED);
            dataVerifyManager.add(new ValueVerify<String>(reponseResult.getData().getSign(),""), VerifyResult.FAILED);
        }
        super.dataVerify();
    }
}
