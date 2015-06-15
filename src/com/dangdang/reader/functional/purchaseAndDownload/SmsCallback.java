package com.dangdang.reader.functional.purchaseAndDownload;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.autotest.config.Config;
import com.dangdang.common.functional.login.Login;
import com.dangdang.ddframework.dataverify.RecordVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ecms.meta.OrderForm;
import com.dangdang.ecms.meta.OrderItem;
import com.dangdang.reader.functional.account.GetAccount;
import com.dangdang.reader.functional.param.model.SmsCallbackParam;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.Account;
import com.dangdang.reader.functional.reponse.Data;
import com.dangdang.reader.functional.reponse.SaveSmsOrderReponse;
import com.dangdang.digital.meta.MediaActivityInfo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import javax.xml.ws.handler.LogicalHandler;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by cailianjie on 2015-5-20.
 */
public class SmsCallback extends FunctionalBaseEx{

    Account beforeAccount;



    SmsCallbackParam paramObject;
    MediaActivityInfo activityInfo;

    public SmsCallback(Map<String,String> param){
        super(param);
        addAction("smsCallback");
    }

    public SmsCallback(Login login,int status){
        setLogin(login);
        paramObject  = new SmsCallbackParam();
        paramObject.setSign("auto");
        paramObject.setApp_orderid("auto");
        paramObject.setCh_type("2");
        paramObject.setMerc_id("0001");
        paramObject.setOrderid("aa0001");
        paramObject.setTime("" + new Date().getTime());
        paramObject.setUserid("22226498");
        paramObject.setStatus("1");
        if(login!=null) {
            paramObject.setToken(login.getToken());
        }
        paramObject.setAction("smsCallback");

        //smsCallbackParam.setRec_amount("aa0001");
    }

    @Override
    public void beforeParam() {
        //不添加公共参数
    }

    public SmsCallbackParam getParamObject() {
        return paramObject;
    }

    @Override
    protected void parseParam() throws Exception {
        setLogin(ParseParamUtil.parseLogin(paramMap));
        ParseParamUtil.removeBlackParam(paramMap);

        //自动生成短信订单
        if(paramObject==null) {
            paramObject = JSONObject.parseObject(JSONObject.toJSONString(paramMap), SmsCallbackParam.class);
        }
        if(login!=null && paramObject.getApp_orderid().equals("auto")){
            SaveSmsOrder saveSmsOrder =new SaveSmsOrder(login);
            saveSmsOrder.doWorkAndVerify();
            activityInfo = saveSmsOrder.getActivityInfo();
            ReponseV2<SaveSmsOrderReponse> result = saveSmsOrder.getReponseResult();
            paramObject.setApp_orderid(result.getData().getOrderNo().toString());
            paramObject.setRec_amount(activityInfo.getDepositMoney().toString());
        }

        if(login!=null) {
            paramObject.setUserid(login.getCustId());
        }

        //生成sign参数
        if(paramObject.getSign().equals("auto")){
            SortedMap<String,String> sortedMap = new TreeMap<String, String>();
            paramMap = (Map<String,String>)JSONObject.toJSON(paramObject);
            sortedMap.putAll(paramMap);
            String sign= SmsUtil.buildSignParamStr(sortedMap, SmsUtil.MERC_KEY);
            paramObject.setSign(DigestUtils.md5Hex(sign.getBytes("utf-8")));

        }

        paramMap = (Map<String,String>)JSONObject.toJSON(paramObject);
    }

    @Override
    protected void genrateVerifyData() throws Exception {
        if(login!=null){
            GetAccount getAccount = new GetAccount(login,paramObject.getDeviceType());
            getAccount.doWorkAndVerify();
            beforeAccount=getAccount.getReponseResult().getData().getAccount();

            if(isEXCEPTSUCCESS()) {
                beforeAccount.setMasterAccountMoney(beforeAccount.getMasterAccountMoney()+activityInfo.getDepositMoney().longValue());
                beforeAccount.setAttachAccountMoney(beforeAccount.getAttachAccountMoney()+activityInfo.getDepositGiftReadPrice().longValue());
            }
        }

        //订单信息验证内容
        if(paramObject.getApp_orderid()!=null && !paramObject.getApp_orderid().equals("auto")){

            OrderForm orderForm =new OrderForm();
            orderForm.setOrderNo(paramObject.getApp_orderid());
            orderForm.setCustId(Long.parseLong(login.getCustId()));

            if(isEXCEPTSUCCESS()){
                orderForm.setPayState(1);
            }
            else
            {
                orderForm.setPayState(2);
            }
            dataVerifyManager.add(new RecordVerify(Config.ECMSDBConfig,orderForm).setVerifyContent("验证订单主表信息正确"),VerifyResult.SUCCESS);

            //selectString = "select * from order_item where ORDER_NO='"+paramObject.getApp_orderid()+"'";
            //OrderItem orderItem =DbUtil.selectOne(Config.ECMSDBConfig,selectString,OrderItem.class);
            OrderItem orderItem =new OrderItem();
            orderItem.setOrderNo(paramObject.getApp_orderid());

            if(isEXCEPTSUCCESS()){

            }
            dataVerifyManager.add(new RecordVerify(Config.ECMSDBConfig,orderItem).setVerifyContent("验证订单子表信息正确"),VerifyResult.SUCCESS);
        }
    }

    @Override
    protected void dataVerify() throws Exception {

        GetAccount getAccount = new GetAccount(login,Config.getDevice().toString());
        getAccount.doWorkAndVerify();
        Account afterAccount = getAccount.getReponseResult().getData().getAccount();

        if(result.toString().equals("success")){

            dataVerifyManager.add(new ValueVerify<Account>(afterAccount,beforeAccount,true).setVerifyContent("验证账户金额是否正确"),VerifyResult.SUCCESS);
            //验证订单状态变化
        }
        else{//失败时验证账户金额不变
            dataVerifyManager.add(new ValueVerify<Account>(afterAccount, beforeAccount, true).setVerifyContent("验证账户金额是否正确"), VerifyResult.SUCCESS);
        }

        super.dataVerify();
    }


}
