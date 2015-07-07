package com.dangdang.readerV5.personal_center;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.readerV5.reponse.GetPublishedCertificateReponse;
import fit.Parse;

/**
 * Created by cailianjie on 2015-7-7.
 */
public class GetPublishedCertificate extends FixtureBase{

    ReponseV2<GetPublishedCertificateReponse> reponseResult;

    public GetPublishedCertificate(){addAction("getPublishedCertificate");}

    @Override
    protected void parseParam() throws Exception {
        setLogin(ParseParamUtil.parseLogin(paramMap));
    }

    public ReponseV2<GetPublishedCertificateReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();
        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetPublishedCertificateReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            dataVerifyManager.add(new ValueVerify<String>(reponseResult.getData().getCertificate(),null), VerifyResult.FAILED);
        }
        super.dataVerify();
    }
}
