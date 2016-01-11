package com.dangdang.ZuoJiaPingXuan;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.ZuoJiaPingXuan.reponse.QueryAuthorAndProductReponse;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.bookbar.meta.AuditionAuthor;
import com.dangdang.db.bookbar.AuditionAuthorDb;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;

/**
 * Created by cailianjie on 2016-1-4.
 */
public class QueryAuthorAndProduct extends FixtureBase{

    ReponseV2<QueryAuthorAndProductReponse> reponseResult;

    public QueryAuthorAndProduct(){}


    public ReponseV2<QueryAuthorAndProductReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<QueryAuthorAndProductReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0 && !paramMap.get("flag").equals("no_author")){

                AuditionAuthor auditionAuthor = AuditionAuthorDb.getAuditionAuthorInfo(paramMap.get("authorId"));
                auditionAuthor.setCreationTime(null);
                auditionAuthor.setFirstLetter(null);
                auditionAuthor.setFirstName(null);
                auditionAuthor.setLastModifiedTime(null);
                auditionAuthor.setLastName(null);
                auditionAuthor.setPageNo(null);
                auditionAuthor.setType(null);
                auditionAuthor.setVoteCount(null);


                dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getAuthorInfo(), auditionAuthor, true).setVerifyContent("验证作家信息一致"));

        }
        else{
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getAuthorInfo(), null).setVerifyContent("验证没有返回作家信息"), VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}

