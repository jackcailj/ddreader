package com.dangdang.readerV5.personal_center;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.authority.BorrowAuthority;
import com.dangdang.authority.BorrowAuthorityDb;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.RegexVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.Util;
import com.dangdang.digital.meta.MediaStoreup;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.readerV5.reponse.GetBorrowAuthorityListReponse;

import java.util.List;

/**
 * Created by cailianjie on 2015-7-7.
 */
public class GetBorrowAuthorityList extends FixtureBase{

    ReponseV2<GetBorrowAuthorityListReponse> reponseResult;

    public GetBorrowAuthorityList(){addAction("getBorrowAuthorityList");}

    public ReponseV2<GetBorrowAuthorityListReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetBorrowAuthorityListReponse>>(){});
    }

    @Override
    protected void parseParam() throws Exception {
        setLogin(ParseParamUtil.parseLogin(paramMap));
    }


    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
            List<BorrowAuthority> borrowAuthorities = BorrowAuthorityDb.getBorrowMedias(login.getCustId());
            if(borrowAuthorities.size()==0){
                dataVerifyManager.add(new ValueVerify<Integer>(reponseResult.getData().getBorrowList().size(),0));
            }
            else{
                StringBuilder regexArticleId = new StringBuilder();
                for (BorrowAuthority borrowAuthority : borrowAuthorities) {
                    regexArticleId.append(Util.getJsonRegexString("productId", ""+borrowAuthority.getProductId()));
                    regexArticleId.append(".*?");
                }
                dataVerifyManager.add(new RegexVerify(regexArticleId.toString(),result.toString()));
            }
        }
        else {
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getBorrowList(), null), VerifyResult.SUCCESS);
        }

        super.dataVerify();
    }
}
