package com.dangdang.readerV5.read_plan;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.PlanProcessDb;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.digital.meta.PlanProcess;

import java.util.Date;

/**
 * Created by cailianjie on 2016-4-18.
 */
public class UpdateProcessBeginTime extends FixtureBase{

    public UpdateProcessBeginTime(){}


    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
            PlanProcess planProcess = PlanProcessDb.getPlanProcessById(paramMap.get("ppId"));



            dataVerifyManager.add(new ValueVerify<Date>(planProcess.getExpectBeginTime(),new Date(Long.parseLong(paramMap.get("beginTime"))))
                    .setVerifyContent("验证设置的时间正确"));
        }
        super.dataVerify();
    }
}
