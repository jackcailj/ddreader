package com.dangdang.param.parse;

import com.dangdang.authority.meta.BorrowAuthority;
import com.dangdang.db.authority.BorrowAuthorityDb;
import com.dangdang.common.functional.login.ILogin;
import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.ddframework.fitnesse.ParamParse;
import com.dangdang.enumeration.BorrowBookStatus;
import com.dangdang.enumeration.VarKey;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cailianjie on 2015-10-10.
 * 获取借阅书籍id
 *
 * 格式：#GetBorrowProduct#借阅状态，数量
 */
public class GetBorrowProductParse implements IParamParse{
    @Override
    public Object parse(Map<String, String> param) throws Exception {
        return null;
    }

    @Override
    public void parse(Map<String, String> paramMap, String key, String param) throws Exception {

        String[] params = ParamParse.parseParam(param);
        BorrowBookStatus borrowBookStatus=BorrowBookStatus.VALID;
        int num=1;
        if(params.length>1){
            borrowBookStatus=BorrowBookStatus.valueOf(params[0]);
            num = Integer.parseInt(params[1]);
        }else{
            num = Integer.parseInt(params[0]);
        }

        List<BorrowAuthority> borrowAuthorities= BorrowAuthorityDb.getBorrowNotBuyMedias(((ILogin) VariableStore.get(VarKey.LOGIN)).getCustId(),borrowBookStatus);


        List<String> mediaIds=new ArrayList<String>();
        for(BorrowAuthority media:borrowAuthorities){
            mediaIds.add("" + media.getProductId());
            if(mediaIds.size()==num){
                break;
            }
        }

        paramMap.put(key, StringUtils.join(mediaIds,","));
    }
}
