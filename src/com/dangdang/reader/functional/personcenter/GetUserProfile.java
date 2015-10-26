package com.dangdang.reader.functional.personcenter;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.common.functional.login.ILogin;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.GetUserProfileReponse;

import java.util.Map;

/**
 * Created by cailianjie on 2015-5-12.
 */
public class GetUserProfile extends FunctionalBaseEx {


    ReponseV2<GetUserProfileReponse> reponseResult;

    public GetUserProfile(Map<String,String> param){
        super(param);
        addAction("getUserProfile");
    }

    public GetUserProfile(ILogin login){
        setLogin(login);
        paramMap.put("token",login.getToken());
        paramMap.put("keyword ","introduct");
        addAction("getUserProfile");
    }

    public ReponseV2<GetUserProfileReponse> getReponseResult() {
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
        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetUserProfileReponse>>(){});

    }
}
