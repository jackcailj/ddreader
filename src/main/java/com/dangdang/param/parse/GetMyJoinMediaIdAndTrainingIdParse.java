package com.dangdang.param.parse;

import com.dangdang.common.functional.login.ILogin;
import com.dangdang.db.digital.PlanProcessDetailDb;
import com.dangdang.db.digital.UserReadProgressTrainingInfoEx;
import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.ddframework.core.VariableType;
import com.dangdang.enumeration.VarKey;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by cailianjie on 2016-5-23.
 * #GetMyJoinProgressAndTrainingId#1,field:trainingId
 */
public class GetMyJoinMediaIdAndTrainingIdParse implements IParamParse{
    @Override
    public Object parse(Map<String, String> param) throws Exception {
        return null;
    }

    @Override
    public void parse(Map<String, String> paramMap, String key, String param) throws Exception {

        ILogin login = (ILogin) VariableStore.get(VarKey.LOGIN);
        List<UserReadProgressTrainingInfoEx> progressTrainingDetails = PlanProcessDetailDb.getAllUserReadProcessTrainings(login.getCustId(),"0,1,2");

        int nRandom = new Random().nextInt(progressTrainingDetails.size());
        UserReadProgressTrainingInfoEx userReadProgressTrainingInfoEx = progressTrainingDetails.get(nRandom);

        paramMap.put(key,userReadProgressTrainingInfoEx.getMediaId().toString());
        VariableStore.add(VariableType.CASE,"trainingId",userReadProgressTrainingInfoEx.getTrainingId().toString());
    }
}
