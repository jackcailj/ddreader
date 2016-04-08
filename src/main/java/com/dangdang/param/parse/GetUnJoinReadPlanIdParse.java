package com.dangdang.param.parse;

import com.dangdang.common.functional.login.ILogin;
import com.dangdang.db.digital.PlanDb;
import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.digital.meta.Plan;
import com.dangdang.enumeration.ReadPlanFeeStatus;
import com.dangdang.enumeration.ReadPlanLifeStatus;
import com.dangdang.enumeration.ReadPlanStatus;
import com.dangdang.enumeration.VarKey;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by cailianjie on 2016-4-6.
 * 获取未参加的计划id,使用格式#GetUnJoinReadPlanId#FREE,VALID，INPROGRESS
 */
public class GetUnJoinReadPlanIdParse implements IParamParse {
    @Override
    public Object parse(Map<String, String> param) throws Exception {
        return null;
    }

    @Override
    public void parse(Map<String, String> paramMap, String key, String param) throws Exception {
        String[] params = ParamParse.parseParam(param);

        ReadPlanFeeStatus feeStatus = ReadPlanFeeStatus.valueOf(params[0]);
        ReadPlanStatus planStatus = ReadPlanStatus.valueOf(params[1]);
        ReadPlanLifeStatus lifeStatus = ReadPlanLifeStatus.valueOf(params[2]);

        ILogin login = (ILogin) VariableStore.get(VarKey.LOGIN);

        List<Plan> unJoinedPlans = PlanDb.getUnJoinPlans(login.getCustId(),planStatus,feeStatus,lifeStatus);
        Random random = new Random();
        int randomIndex = random.nextInt(unJoinedPlans.size());

        paramMap.put(key,unJoinedPlans.get(randomIndex).getPlanId().toString());
    }
}
