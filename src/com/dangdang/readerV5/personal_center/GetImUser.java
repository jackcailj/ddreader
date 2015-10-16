package com.dangdang.readerV5.personal_center;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.im.IMSqlUtil;
import com.dangdang.im.meta.ImUser;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.readerV5.reponse.GetImUserReponse;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by cailianjie on 2015-7-9.
 */
public class GetImUser extends FixtureBase{

    ReponseV2<GetImUserReponse> reponseResult;

    public GetImUser(){addAction("getImUser");}

    @Override
    protected void parseParam() throws Exception {
        setLogin(ParseParamUtil.parseLogin(paramMap));

        if(login !=null && paramMap.get("custId")!=null && StringUtils.isEmpty(paramMap.get("custId"))){
            paramMap.put("custId",login.getPubId());
        }
    }

    public ReponseV2<GetImUserReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetImUserReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            ImUser imUser = IMSqlUtil.getImUser(login.getCustId());

            dataVerifyManager.add(new ValueVerify<ImUser>(imUser,reponseResult.getData().getImUser(),true).setVerifyContent("验证获取的ImUser信息是否正确"));
        }
        else
        {
            dataVerifyManager.add(new ValueVerify<ImUser>(null,reponseResult.getData().getImUser()).setVerifyContent("验证获取失败没有返回ImUser"),VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
