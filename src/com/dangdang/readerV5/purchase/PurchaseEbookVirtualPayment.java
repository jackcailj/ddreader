package com.dangdang.readerV5.purchase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.ddframework.dataverify.*;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.StringUtil;
import com.dangdang.ddframework.util.Util;
import com.dangdang.ddframework.util.security.SignUtils;
import com.dangdang.digital.BookType;
import com.dangdang.digital.MediaDb;
import com.dangdang.digital.meta.Media;
import com.dangdang.ecms.meta.OrderForm;
import com.dangdang.ecms.meta.OrderItem;
import com.dangdang.ecms.meta.UserEbook;
import com.dangdang.reader.functional.param.parse._enum.VarKey;
import com.dangdang.readerV5.personal_center.GetAccountInfo;
import com.dangdang.readerV5.personal_center.bookshelf.GetUserBookList;
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

        if(paramMap.get("timestamp")!=null && StringUtils.isEmpty(paramMap.get("sign"))) {
            paramMap.put("timestamp", "" + new Date().getTime());
        }

        //生成sign参数
        if(paramMap.get("sign")!=null && StringUtils.isEmpty(paramMap.get("sign")) && login!=null){
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

            beforeMasterAcount = new GetAccountInfo(login,true);
            beforeMasterAcount.doWork();

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



        int totalCost = 0;
        /*List<Media> medias = (List<Media>) VariableStore.get(VarKey.MEDIAS);
        if(medias!=null) {
            List<Long> productIds = new ArrayList<Long>();
            for (Media media : medias) {
                if(BookType.SHIDU.isShiDu(media.getUid())){
                    continue;
                }

                totalCost += media.getPrice();
                productIds.add(media.getProductId());
                dataVerifyManager.add(new RegexVerify(Util.getJsonRegexString("mediaId",media.getProductId().toString()),getUserBookList.getResult().toString()).setVerifyContent("购买成功，验证["+media.getProductId()+"]是否有权限"));
            }
        }
        else{*/
        if(paramMap.get("productArray")!=null && login!=null) {
            List products = JSONObject.parseArray(paramMap.get("productArray"));
            if(paramMap.get("flag").equals("font")){
                for (Object product : products) {
                    Map<String, String> mapObject = (Map<String, String>) product;
                    Media media = MediaDb.getMedia(mapObject.get("productId"));
                    if (!paramMap.get("flag").equals("重复")) {
                        totalCost += media.getPrice();
                    }
                    dataVerifyManager.add(new RecordVerify(Config.AUTHORITYConfig, "select * from media_authority where cust_id = "+login.getCustId()+" and product_id="+mapObject.get("productId")+" and authority_type=3"));
                }
            }
            else {
                GetUserBookList getUserBookList = new GetUserBookList(login);
                getUserBookList.doWork();


                for (Object product : products) {
                    Map<String, String> mapObject = (Map<String, String>) product;
                    Media media = MediaDb.getMedia(mapObject.get("productId"));
                    if (media != null) {
                        if (BookType.SHIDU.isShiDu(media.getUid())) {
                            continue;
                        }
                        if (!paramMap.get("flag").equals("重复")) {
                            totalCost += media.getPrice();
                        }
                        dataVerifyManager.add(new RegexVerify(Util.getJsonRegexString("mediaId", media.getProductId().toString()), getUserBookList.getResult().toString()).setVerifyContent("购买成功，验证" + media.getProductId() + "是否有权限"));
                    } else {
                        String productId = mapObject.get("productId");
                        dataVerifyManager.add(new RegexVerify(Util.getJsonRegexString("mediaId", productId), getUserBookList.getResult().toString()).setVerifyContent("购买成功，验证" + productId + "是否有权限"), VerifyResult.FAILED);
                    }
                }
            }
        }
       // }


        if(reponseResult.getStatus().getCode()==0){

            Long attachAccountMoney = beforeAttachAcount.getReponseAttachResult().getData().getAccountTotal();
            int diff=(int) (totalCost-attachAccountMoney);
            if( attachAccountMoney>0){
                beforeAttachAcount.getReponseAttachResult().getData().setAccountTotal((long) (diff < 0 ? Math.abs(diff) : 0));
            }


            // TODO Auto-generated method stub
            //masterAccount = DbUtil.selectOne(Config.ACCOUNTDBConfig, "select * from master_account where cust_id="+custid, MasterAccount.class);
            if( diff>0){
                beforeMasterAcount.getReponseMasterResult().getData().setAccountTotal(beforeMasterAcount.getReponseMasterResult().getData().getAccountTotal()-diff);
            }


        }
        /*else{
            //验证权限列表没有这本书
            if(login!=null){
                GetUserBookList getUserBookList = new GetUserBookList(login);
                getUserBookList.doWork();

                dataVerifyManager.add(new RegexVerify(Util.getJsonRegexString("mediaId"),getUserBookList.getResult().toString()).setVerifyContent("购买失败，验证是没有有权限"), VerifyResult.FAILED);
            }
        }*/

        if(login!=null) {
            GetAccountInfo afterMasterAccountInfo = new GetAccountInfo(login, true);
            afterMasterAccountInfo.doWork();

            dataVerifyManager.add(new ValueVerify<Long>(afterMasterAccountInfo.getReponseMasterResult().getData().getAccountTotal(), beforeMasterAcount.getReponseMasterResult().getData().getAccountTotal()).setVerifyContent("验证主账户金额是否正确"), VerifyResult.SUCCESS);

            GetAccountInfo afterAttachAccountInfo = new GetAccountInfo(login, false);
            afterAttachAccountInfo.doWork();

            dataVerifyManager.add(new ValueVerify<Long>(afterAttachAccountInfo.getReponseAttachResult().getData().getAccountTotal(), beforeAttachAcount.getReponseAttachResult().getData().getAccountTotal()).setVerifyContent("验证副账户金额是否正确"), VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
