package com.dangdang.param.parse;

import com.dangdang.common.functional.login.ILogin;
import com.dangdang.db.digital.MediaTrainingDb;
import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.ddframework.fitnesse.ParamParse;
import com.dangdang.ddframework.util.Util;
import com.dangdang.digital.meta.MediaTraining;
import com.dangdang.enumeration.TrainingStatus;
import com.dangdang.enumeration.VarKey;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by cailianjie on 2016-5-18.
 * 获取训练ID使用格式：#getTrainingIds#TrainingStatus,数量
 */
public class GetTrainingIdsParse implements IParamParse {
    @Override
    public Object parse(Map<String, String> param) throws Exception {
        return null;
    }

    @Override
    public void parse(Map<String, String> paramMap, String key, String param) throws Exception {

        String[] params= ParamParse.parseParam(param);

        TrainingStatus trainingStatus = TrainingStatus.valueOf(params[0]);
        Integer num = Integer.parseInt(params[1]);

        ILogin login = (ILogin) VariableStore.get(VarKey.LOGIN);

        List<MediaTraining> trainingList = MediaTrainingDb.getTrainings(trainingStatus,num,login.getCustId());

        List<String> trainingIds = Util.getFields(trainingList,"mtId");

        paramMap.put(key, StringUtils.join(trainingIds,","));
    }
}
