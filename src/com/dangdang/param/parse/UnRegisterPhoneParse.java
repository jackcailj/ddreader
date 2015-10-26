package com.dangdang.param.parse;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Created by cailianjie on 2015-6-26.
 */
public class UnRegisterPhoneParse implements IParamParse{
    public  static Logger logger=Logger.getLogger(UnRegisterPhoneParse.class);

    public static final String phonePerfix="1300000";
    //public static final Long defaultPhoneNum=new Long("13000000001");

    @Override
    public Object parse(Map<String, String> param) throws Exception {
        return null;
    }

    @Override
    /*
    自动生成未注册手机号
     */
    public void parse(Map<String, String> paramMap, String key, String param) throws Exception {
        String getMaxPhone ="select max(USERNAME) as maxPhone from user_device where USERNAME like '"+phonePerfix+"%'";

        Long phoneNum =new Long("13000000000");
        try{
            Map<String,Object> result = DbUtil.selectOne(Config.UCENTERDBConfig, getMaxPhone);
            phoneNum=Long.parseLong(result.get("maxPhone").toString());
        }catch (Exception e){
            logger.info("获取未注册手机号异常,将使用默认手机号13000000001："+e);
        }

        paramMap.put(key,""+(phoneNum+1));

    }
}
