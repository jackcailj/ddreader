package com.dangdang.param.parse;

import com.dangdang.ddframework.fitnesse.ParamParse;
import com.dangdang.enumeration.GetDateLongEnum;
import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by cailianjie on 2016-4-18.
 * 获取时间的Long值
 * 使用格式:#GetDateLong# CUR or #GetDateLong# ADD 1 or #GetDateLong# SUB 1 or #GetDateLong# DATE 2016-4-18 18:29:30
 */
public class GetDateLongParse implements IParamParse{
    @Override
    public Object parse(Map<String, String> param) throws Exception {
        return null;
    }

    @Override
    public void parse(Map<String, String> paramMap, String key, String param) throws Exception {
        String[] params = ParamParse.parseParam(param);

        Long time=null;
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        GetDateLongEnum getDateLongEnum = GetDateLongEnum.valueOf(params[0].trim());
        if (getDateLongEnum == GetDateLongEnum.ADD) {
            Date curDate = new Date();
            Date newDate = DateUtils.addDays(curDate,Integer.parseInt(params[1].trim()));

            time =df1.parse(df1.format(newDate)).getTime();
        } else if (getDateLongEnum == GetDateLongEnum.SUB) {
            Date curDate = new Date();
            Date newDate = DateUtils.addDays(curDate,-Integer.parseInt(params[1].trim()));
            time =df1.parse(df1.format(newDate)).getTime();
        }
        else if (getDateLongEnum == GetDateLongEnum.DATE) {

            Date date=df1.parse(params[1].trim());
            time = date.getTime();
        }
        else if (getDateLongEnum == GetDateLongEnum.CUR) {
            String date=df1.format(new Date());

            time = df1.parse(date).getTime();
        }

        paramMap.put(key,time.toString());
    }
}
