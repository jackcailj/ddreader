package com.dangdang.readerV5.personal_center;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.account.meta.AccountExperienceItems;
import com.dangdang.account.meta.AccountGradeExperienceRelation;
import com.dangdang.account.meta.AttachAccount;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.account.AccountExperienceItemsDb;
import com.dangdang.db.account.AccountGradeExperienceRelationDb;
import com.dangdang.db.account.AttachAccountDb;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.GetExperienceItemListReponse;

import java.util.List;

/**
 * Created by cailianjie on 2015-8-5.
 */
public class GetExperienceItemList extends FixtureBase{

    ReponseV2<GetExperienceItemListReponse> reponseResult;

    public GetExperienceItemList(){}


    public ReponseV2<GetExperienceItemListReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetExperienceItemListReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){

            GetExperienceItemListReponse expect=new GetExperienceItemListReponse();
            List<AccountExperienceItems> itemses= AccountExperienceItemsDb.getAccountExpreienceItems(login.getCustId(),Long.parseLong(paramMap.get("lastExperienceItemsId")),
                    Integer.parseInt(paramMap.get("pageSize")));

            expect.setAccountExperienceList(itemses);


            AttachAccount attachAccount = AttachAccountDb.getAttachAccount(login.getCustId());
            expect.setCurrentGrade(attachAccount.getAccountGrade());
            expect.setAccountExperienceTotal(attachAccount.getAccountExperience());
            expect.setPreGrade(attachAccount.getAccountGrade()-1);
            expect.setNextGrade(attachAccount.getAccountGrade()+1);


            AccountGradeExperienceRelation currentGrade = AccountGradeExperienceRelationDb.getAccountGradeExperience(expect.getCurrentGrade());
            expect.setCurrentExperience(currentGrade.getExperience());

            AccountGradeExperienceRelation nextGrade = AccountGradeExperienceRelationDb.getAccountGradeExperience(expect.getNextGrade());
            expect.setNextExperience(nextGrade.getExperience());

            AccountGradeExperienceRelation preGrade = AccountGradeExperienceRelationDb.getAccountGradeExperience(expect.getPreGrade());
            expect.setPreExperience(preGrade.getExperience());

            dataVerifyManager.add(new ValueVerify<GetExperienceItemListReponse>(reponseResult.getData(),expect,true).setVerifyContent("验证返回数据是否正确"));

        }
        else{
            dataVerifyManager.add(new ValueVerify<GetExperienceItemListReponse>(reponseResult.getData(),null).setVerifyContent("验证返回数据应为null"));
        }
        super.dataVerify();
    }
}
