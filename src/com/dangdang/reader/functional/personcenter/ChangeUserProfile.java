package com.dangdang.reader.functional.personcenter;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.reponse.ReponseV2Base;
import com.dangdang.reader.functional.param.model.ChangeUserProfileParam;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.GetUserProfileReponse;
import org.apache.ibatis.migration.Change;

import java.util.Map;

/**
 * Created by cailianjie on 2015-5-12.
 */
public class ChangeUserProfile extends FunctionalBaseEx {

    ReponseV2Base reponseResult;
    ChangeUserProfileParam paramObject;
    String beforeIntro;

    public ChangeUserProfile(Map<String,String> param){
        super(param);
        addAction("changeUserProfile");
    }

    public ReponseV2Base getReponseResult() {
        return reponseResult;
    }

    @Override
    protected void parseParam() throws Exception {
        setLogin(ParseParamUtil.parseLogin(paramMap));
        ParseParamUtil.removeBlackParam(paramMap);

        paramObject = JSONObject.parseObject(JSONObject.toJSONString(paramMap),ChangeUserProfileParam.class);

    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2Base>(){});
    }

    @Override
    protected void genrateVerifyData() throws Exception {

        //获取更改前个人介绍
        if(!isEXCEPTSUCCESS()) {
            GetUserProfile getUserProfile = new GetUserProfile(login);
            getUserProfile.doWorkAndVerify();
            beforeIntro = getUserProfile.getReponseResult().getData().getUserProfile().getIntroduct();
        }

    }

    @Override
    protected void dataVerify() throws Exception {
        if(login!=null) {
            GetUserProfile getUserProfile = new GetUserProfile(login);
            getUserProfile.doWorkAndVerify();

            if (reponseResult.getStatus().getCode() == 0) {
                dataVerifyManager.add(new ValueVerify<String>(paramObject.getContent(), getUserProfile.getReponseResult().getData().getUserProfile().getIntroduct())
                        .setVerifyContent("验证个人介绍是否为修改后的值"));
            } else {
                dataVerifyManager.add(new ValueVerify<String>(beforeIntro, getUserProfile.getReponseResult().getData().getUserProfile().getIntroduct())
                        .setVerifyContent("验证修改失败，用户介绍不变"), VerifyResult.SUCCESS);
            }
        }
        super.dataVerify();
    }
}
