package com.dangdang.reader.functional.purchaseAndDownload;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.autotest.config.Config;
import com.dangdang.common.functional.login.Login;
import com.dangdang.ddframework.core.TestDevice;
import com.dangdang.ddframework.dataverify.MapVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ecms.meta.OrderForm;
import com.dangdang.ecms.meta.OrderItem;
import com.dangdang.reader.functional.bookstore.BookList;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.MobileEbookInfo;
import com.dangdang.reader.functional.reponse.SaveSmsOrderReponse;
import com.dangdang.digital.meta.MediaActivityInfo;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cailianjie on 2015-5-19.
 * desc:短信支付订单，只用于充值
 */
public class SaveSmsOrder extends FunctionalBaseEx{

    List<String> productIds =new ArrayList<String>();
    int totalCost;



    MediaActivityInfo activityInfo;

    ReponseV2<SaveSmsOrderReponse> reponseResult;
    
    public SaveSmsOrder(){
    	addAction("saveSmsOrder");
    }

    public SaveSmsOrder(Map<String,String> param){
        super(param);
        addAction("saveSmsOrder");
    }

    public SaveSmsOrder(Login login){
        addAction("saveSmsOrder");
        setLogin(login);
        paramMap.put("token", login.getToken());
        paramMap.put("productId", "exist");
        paramMap.put("from_url", "103");
    }

    public MediaActivityInfo getActivityInfo() {
        return activityInfo;
    }

    @Override
    protected void parseParam() throws Exception {
        setLogin(ParseParamUtil.parseLogin(paramMap));
        ParseParamUtil.removeBlackParam(paramMap);

        //解析用例参数，获取期望的productid
        if(login !=null && paramMap.get("productId")!=null)
        {

            //获取充值productid
            String productIdString=paramMap.get("productId").toString();
            if(productIdString.contains("exist")){
                String ebookSting ="select * from media_activity_info where activity_type_id=1019";
                List<MediaActivityInfo> activityInfos = DbUtil.selectList(Config.YCDBConfig, ebookSting, MediaActivityInfo.class);

                activityInfo=activityInfos.get(0);
                productIds.add(activityInfo.getRelationProductId());
                totalCost+=activityInfos.get(0).getDepositMoney();

            }

            if(productIds.size()>0) {
                paramMap.put("productId", StringUtils.join(productIds, ","));
            }

        }
    }

    public ReponseV2<SaveSmsOrderReponse> getReponseResult() {
        return reponseResult;
    }

    public String doWork1() throws Exception {
        super.doWork();
        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<SaveSmsOrderReponse>>(){});
        return  reponseResult.toString();
    }

    public String get(String doWork) throws Exception{
    	return doWork1();
    
    }
    
    @Override
    protected void genrateVerifyData() throws Exception {
        //验证订单信息是否正确

    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            OrderForm expectOrderForm =new OrderForm();
            expectOrderForm.setCustId(Long.parseLong(login.getCustId()));
            expectOrderForm.setPayTypeSub(1);
            expectOrderForm.setFromPlatform(Integer.parseInt(paramMap.get("from_url").toString()));
            expectOrderForm.setPayState(0);
            expectOrderForm.setPrice(new BigDecimal(totalCost));
            expectOrderForm.setOrderNo(reponseResult.getData().getOrderNo());


            String selectString="select * from order_form where ORDER_NO='"+reponseResult.getData().getOrderNo()+"'";
            OrderForm form = DbUtil.selectOne(Config.ECMSDBConfig,selectString,OrderForm.class);

            dataVerifyManager.add(new ValueVerify<OrderForm>(form,expectOrderForm, true).setVerifyContent("验证订单主表（OrderForm）数据是否正确"));

            OrderItem expectOrderItem = new OrderItem();
            expectOrderItem.setOrderNo(reponseResult.getData().getOrderNo());
            expectOrderItem.setBarginPrice(totalCost);
            expectOrderItem.setItemId(Long.parseLong(paramMap.get("productId").toString()));

            selectString = "SELECT * from order_item where ORDER_NO='"+reponseResult.getData().getOrderNo()+"'";
            OrderItem item = DbUtil.selectOne(Config.ECMSDBConfig,selectString,OrderItem.class);

            dataVerifyManager.add(new ValueVerify<OrderItem>(item,expectOrderItem, true).setVerifyContent("验证订单子表（OrderItem）数据是否正确"));
        }
        super.dataVerify();
    }
}
