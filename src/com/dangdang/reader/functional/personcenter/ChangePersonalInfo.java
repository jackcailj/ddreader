package com.dangdang.reader.functional.personcenter;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.ddframework.reponse.ReponseV2Base;
import com.dangdang.reader.functional.param.model.ChangePersonalInfoParam;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;


import java.util.Date;
import java.util.Map;

/**
 * Created by cailianjie on 2015-5-12.
 */
public class ChangePersonalInfo extends FunctionalBaseEx{


    ReponseV2Base reponseResult;
    ChangePersonalInfoParam paramObject;

    public ChangePersonalInfo(Map<String,String> param){
        super(param);
        addAction("changePersonalInfo");
    }

    public ReponseV2Base getReponseResult() {
        return reponseResult;
    }


    @Override
    protected void parseParam() throws Exception {
        setLogin(ParseParamUtil.parseLogin(paramMap));
        ParseParamUtil.removeBlackParam(paramMap);
        paramObject=JSONObject.parseObject(JSONObject.toJSONString(paramMap),ChangePersonalInfoParam.class);
        if(isEXCEPTSUCCESS()){
            paramObject.setNickname(paramObject.getNickname()+new Date().getTime());
        }

        paramMap = (Map<String,String>)JSONObject.toJSON(paramObject);
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult =  JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2Base>(){});
    }
}
