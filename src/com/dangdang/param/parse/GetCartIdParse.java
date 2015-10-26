package com.dangdang.param.parse;

import com.dangdang.common.functional.login.ILogin;
import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.param.parse._enum.VarKey;
import com.dangdang.readerV5.purchase.GetShoppingCartId;

import java.util.Map;

/**
 * Created by cailianjie on 2015-7-14.
 */
public class GetCartIdParse implements IParamParse{
    @Override
    public Object parse(Map<String, String> param) throws Exception {
        return null;
    }

    @Override
    public void parse(Map<String, String> paramMap, String key, String param) throws Exception {
        ILogin login = (ILogin) VariableStore.get(VarKey.LOGIN.toString());
        GetShoppingCartId getShoppingCartId=null;
        if(login!=null){
            getShoppingCartId =new GetShoppingCartId(login);
        }
        else{
            getShoppingCartId =new GetShoppingCartId();
        }

        getShoppingCartId.doWorkAndVerify();
        paramMap.put(key,getShoppingCartId.getReponseResult().getData().getCartId());
    }
}
