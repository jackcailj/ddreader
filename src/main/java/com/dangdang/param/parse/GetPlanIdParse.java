package com.dangdang.param.parse;

import com.dangdang.common.functional.login.ILogin;
import com.dangdang.db.digital.PlanDb;
import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.digital.meta.Plan;
import com.dangdang.enumeration.VarKey;

import java.util.Map;

/**
 * Created by cailianjie on 2016-5-27.
 *
 * #GetPlanId#I|Other
 */
public class GetPlanIdParse implements IParamParse{
    @Override
    public Object parse(Map<String, String> param) throws Exception {
        return null;
    }

    @Override
    public void parse(Map<String, String> paramMap, String key, String param) throws Exception {

        Plan plan=null;
        ILogin login= (ILogin) VariableStore.get(VarKey.LOGIN);
        Boolean myCreate=true;
        if(param.toLowerCase().equals("other")){
            myCreate=false;
        }
        plan= PlanDb.getPlan(login.getCustId(),myCreate);

        paramMap.put(key,plan.getPlanId().toString());
    }
}
