package com.dangdang.readerV5.purchase;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.common.functional.login.ILogin;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.ShoppingCartDb;
import com.dangdang.digital.meta.ShoppingCart;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.readerV5.reponse.GetShoppingCartIdReponse;

/**
 * Created by cailianjie on 2015-7-14.
 */
public class GetShoppingCartId extends FixtureBase{

    ReponseV2<GetShoppingCartIdReponse> reponseResult;

    public GetShoppingCartId(){addAction("getShoppingCartId");}

    public GetShoppingCartId(ILogin login){
        setLogin(login);
        paramMap.put("token",login.getToken());
        addAction("getShoppingCartId");
    }

    public ReponseV2<GetShoppingCartIdReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult= JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetShoppingCartIdReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            if(login!=null){
                ShoppingCart shoppingCart = ShoppingCartDb.getShoppingCart(login.getCustId());
                dataVerifyManager.add(new ValueVerify<String>(reponseResult.getData().getCartId(),shoppingCart.getCartId()).setVerifyContent("验证购物车ID正确"));
            }
            else{
                dataVerifyManager.add(new ValueVerify<String>(reponseResult.getData().getCartId(), null).setVerifyContent("验证购物车ID正确"), VerifyResult.FAILED);
            }

        }
        else{
            dataVerifyManager.add(new ValueVerify<String>(reponseResult.getData().getCartId(), null),VerifyResult.SUCCESS);
        }

        super.dataVerify();
    }
}
