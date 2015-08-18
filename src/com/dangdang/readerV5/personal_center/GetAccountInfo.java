package com.dangdang.readerV5.personal_center;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.account.AccountType;
import com.dangdang.account.AccountUtils;
import com.dangdang.account.meta.AccountConsumeItems;
import com.dangdang.account.meta.AttachAccountItems;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.common.functional.login.ILogin;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.readerV5.reponse.AttachAccountItem;
import com.dangdang.readerV5.reponse.GetAccountInfoReponse;
import com.dangdang.readerV5.reponse.GetAttachAccountInfoReponse;

import java.util.List;

/**
 * Created by cailianjie on 2015-6-19.
 */
public class GetAccountInfo extends FixtureBase{

    ReponseV2<GetAccountInfoReponse> reponseMasterResult;
    ReponseV2<GetAttachAccountInfoReponse> reponseAttachResult;

    public GetAccountInfo(){}

    public GetAccountInfo(ILogin login,boolean isMaster){
        setLogin(login);
        paramMap.put("token", login.getToken());
        paramMap.put("accountType",isMaster?"master":"attach");

    }

    @Override
    protected void parseParam() throws Exception {
        setLogin(ParseParamUtil.parseLogin(paramMap));
        //ParseParamUtil.removeBlackParam(paramMap);
    }

    public ReponseV2<GetAccountInfoReponse> getReponseMasterResult() {
        return reponseMasterResult;
    }

    public ReponseV2<GetAttachAccountInfoReponse> getReponseAttachResult() {
        return reponseAttachResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseMasterResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetAccountInfoReponse>>(){});
        reponseAttachResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetAttachAccountInfoReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){

            List accountItems=null;
            if(paramMap.get("accountType").equals("master")){
                //获取金铃铛信息
                accountItems= AccountUtils.getMasterLingDangDetail(login.getCustId(),Config.getDevice());

                if(reponseMasterResult.getData().getResult()!=null) {
                    dataVerifyManager.add(new ListVerify(accountItems, reponseMasterResult.getData().getResult(), true).setVerifyContent("验证账户金额和记录是否正确"));
                }
            }
            else if(paramMap.get("accountType").equals("attach"))
            {
                //获取银铃铛信息
                accountItems= AccountUtils.getAttachLingDangDetail(login.getCustId(), Config.getDevice());

                List<AttachAccountItem> items= JSONObject.parseArray(JSONObject.toJSONString(accountItems), AttachAccountItem.class);

                if(reponseAttachResult.getData().getResult()!=null) {
                    for(Object item:accountItems){
                        AttachAccountItems attachAccountItems= (AttachAccountItems) item;
                        attachAccountItems.setEffectiveDateMsec(null);
                        attachAccountItems.setCreationDate(null);
                    }
                    dataVerifyManager.add(new ListVerify(items,reponseAttachResult.getData().getResult(), true).setVerifyContent("验证账户金额和记录是否正确"));
                }
            }
        }
        super.dataVerify();
    }
}
