package com.dangdang.readerV5.purchase;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.autotest.common.PlatForm;
import com.dangdang.common.functional.login.ILogin;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.db.digital.MediaActivityInfoDb;
import com.dangdang.digital.meta.MediaActivityInfo;
import com.dangdang.readerV5.reponse.GetDepositShowViewReponse;
import org.apache.commons.lang3.StringUtils;

import java.util.List;


/**
 * Created by cailianjie on 2015-7-31.
 */
public class GetDepositShowView extends FixtureBase{

    ReponseV2<GetDepositShowViewReponse> reponseResult;

    public  GetDepositShowView(){}

    public  GetDepositShowView(ILogin login,PlatForm fromPlat,String payment){
        setLogin(login);
        paramMap.put("token", login.getToken());
        paramMap.put("fromPaltform",fromPlat.toString());
        paramMap.put("paymentId",payment);

    }

    public ReponseV2<GetDepositShowViewReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetDepositShowViewReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            //操作成功，验证数据正确
            PlatForm fromPaltform;
            if(StringUtils.isBlank(paramMap.get("fromPaltform"))){
                fromPaltform= PlatForm.YC_ANDROID;
            }
            else {
                fromPaltform=PlatForm.getPlatForm(paramMap.get("fromPaltform"));
            }

            List<MediaActivityInfo> infos = MediaActivityInfoDb.getMediaActivityInfo(fromPaltform, paramMap.get("paymentId"));
            //返回数量不能为0
            //dataVerifyManager.add(new ValueVerify<Integer>(reponseResult.getData().getActivityInfos().size(),0), VerifyResult.FAILED);
            //返回数据与查询数据一致
            dataVerifyManager.add(new ListVerify(infos, reponseResult.getData().getActivityInfos(), true));
        }
        else {
            dataVerifyManager.add(new ValueVerify<Object>(null, reponseResult.getData().getActivityInfos()),VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
