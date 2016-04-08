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
 */
public class GetJoinedReadPlanIdParse implements IParamParse {
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

        List<Plan> JoinedPlans = PlanDb.getJoinPlans(login.getCustId(),lifeStatus);
        Random random = new Random();
        int randomIndex = random.nextInt(JoinedPlans.size());

        paramMap.put(key,JoinedPlans.get(randomIndex).getPlanId().toString());

    }
}
