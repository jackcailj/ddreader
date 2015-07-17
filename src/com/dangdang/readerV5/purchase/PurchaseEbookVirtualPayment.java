package com.dangdang.readerV5.purchase;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.ddframework.dataverify.RecordExVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.security.SignUtils;
import com.dangdang.digital.meta.Media;
import com.dangdang.ecms.meta.OrderForm;
import com.dangdang.ecms.meta.OrderItem;
import com.dangdang.ecms.meta.UserEbook;
import com.dangdang.reader.functional.param.parse._enum.VarKey;
import com.dangdang.readerV5.personal_center.GetAccountInfo;
import com.dangdang.readerV5.reponse.PurchaseEbookVirtualPaymentReponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Created by cailianjie on 2015-7-14.
 */
public class PurchaseEbookVirtualPayment extends FixtureBase{

    ReponseV2<PurchaseEbookVirtualPaymentReponse> reponseResult;

    GetAccountInfo beforeMasterAcount;
    GetAccountInfo beforeAttachAcount;

    public PurchaseEbookVirtualPayment(){addAction("purchaseEbookVirtualPayment");}

    @Override
    protected void parseParam() throws Exception {


        paramMap.put("timestamp", "" + new Date().getTime());

        //生成sign参数
        if(paramMap.get("sign")!=null && login!=null){
            List products = JSONObject.parseArray(paramMap.get("productArray"));
            List<String> productIds=new ArrayList<String>();
            if(products!=null) {
                for (Object o : products) {
                    Map<String, String> product = (Map<String, String>) o;
                    productIds.add(product.get("productId"));
                }
            }
            String productIdString=(CollectionUtils.isEmpty(productIds)?"": StringUtils.join(productIds,","));
            String key = SignUtils.encryptOrderVirtualPayment(Long.parseLong(login.getCustId()), productIdString);
            String source = productIdString+(paramMap.get("timestamp")==null?"":paramMap.get("timestamp"));
            String sign =SignUtils.createBindPermissionSign(source, key);
            paramMap.put("sign", sign);
        }
    }

    @Override
    protected void genrateVerifyData() throws Exception {
        if(login!=null){	//验证主副账户扣款正确，先扣附账户、后扣主账户
            //attachAccount = DbUtil.selectOne(Config.ACCOUNTDBConfig, "select * from attach_account where cust_id="+custid,AttachAccount.class);

            beforeAttachAcount = new GetAccountInfo(login,false);
            beforeAttachAcount.doWork();


            /*//验证订单数据正确
            if(EXCEPTSUCCESS){
                OrderForm orderForm =new OrderForm();
                orderForm.setCustId(Long.parseLong(login.getCustId()));
                //orderForm.setPrice(new BigDecimal(totalCost));
                orderForm.setOrderType("98");
                //orderForm.setPayTypeSub(2);

                List<OrderItem> orderItems=new ArrayList<OrderItem>();
                for(Long productId: productIds){
                    OrderItem orderItem =new OrderItem();
                    orderItem.setItemId((long)productId);

                    orderItems.add(orderItem);
                }
                dataVerifyManager.add(new RecordExVerify(Config.YCDBConfig, orderForm, "ID", " from order_form where CUST_ID="+login.getCustId(),orderItems,"ORDER_NO").setVerifyContent("验证订单数据是否正确"));
            }*/
        }
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult=JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<PurchaseEbookVirtualPaymentReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){

            List<Media> medias = (List<Media>) VariableStore.get(VarKey.MEDIAS);
            int totalCost=0;
            List<Long> productIds = new ArrayList<Long>();
            for(Media media:medias){
                totalCost+=media.getPrice();
                productIds.add(media.getProductId());
            }


            Long attachAccountMoney = beforeAttachAcount.getReponseAttachResult().getData().getAccountTotal();
            int diff=(int) (totalCost-attachAccountMoney);
            if( attachAccountMoney>0){
                beforeAttachAcount.getReponseAttachResult().getData().setAccountTotal((long) (diff < 0 ? Math.abs(diff) : 0));
            }


            beforeMasterAcount = new GetAccountInfo(login,true);
            beforeMasterAcount.doWork();

            // TODO Auto-generated method stub
            //masterAccount = DbUtil.selectOne(Config.ACCOUNTDBConfig, "select * from master_account where cust_id="+custid, MasterAccount.class);
            if( diff>0){
                beforeMasterAcount.getReponseMasterResult().getData().setAccountTotal(beforeMasterAcount.getReponseMasterResult().getData().getAccountTotal()-diff);
            }


            GetAccountInfo afterMasterAccountInfo = new GetAccountInfo(login,true);
            afterMasterAccountInfo.doWork();

            dataVerifyManager.add(new ValueVerify<Long>(afterMasterAccountInfo.getReponseMasterResult().getData().getAccountTotal(), beforeMasterAcount.getReponseMasterResult().getData().getAccountTotal()).setVerifyContent("验证主账户金额是否正确"));

            GetAccountInfo afterAttachAccountInfo = new GetAccountInfo(login,false);
            afterAttachAccountInfo.doWork();

            dataVerifyManager.add(new ValueVerify<Long>(afterAttachAccountInfo.getReponseAttachResult().getData().getAccountTotal(), beforeAttachAcount.getReponseAttachResult().getData().getAccountTotal()).setVerifyContent("验证副账户金额是否正确"));

        }
        super.dataVerify();
    }
}
