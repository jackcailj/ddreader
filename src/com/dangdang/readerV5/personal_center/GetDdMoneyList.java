package com.dangdang.readerV5.personal_center;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.readerV5.reponse.DdMoney;
import com.dangdang.readerV5.reponse.GetDdMoneyListReponse;
import org.hibernate.dialect.unique.DB2UniqueDelegate;

import java.util.List;

/**
 * Created by cailianjie on 2015-6-24.
 */
public class GetDdMoneyList extends FixtureBase{

    ReponseV2<GetDdMoneyListReponse> reponseResult;

    public GetDdMoneyList(){addAction("getDdMoneyList");}

    @Override
    protected void parseParam() throws Exception {
        setLogin(ParseParamUtil.parseLogin(paramMap));
    }

    public ReponseV2<GetDdMoneyListReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();
        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetDdMoneyListReponse>>(){});
    }

}
