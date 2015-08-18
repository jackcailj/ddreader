package com.dangdang.readerV5.personal_center;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.autotest.common.PlatForm;
import com.dangdang.common.functional.login.ILogin;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.MediaBought;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.readerV5.reponse.GetMyBoughtListReponse;

import java.util.List;


/**
 * Created by cailianjie on 2015-7-8.
 */
public class GetMyBoughtList extends FixtureBase{

    ReponseV2<GetMyBoughtListReponse> reponseResult;

    public  GetMyBoughtList(){}

    public  GetMyBoughtList(ILogin login){
        setLogin(login);
        paramMap.put("token", login.getToken());
        paramMap.put("start", "0");
        paramMap.put("end","100");
        paramMap.put("fromPaltform","ds");

    }

    public  GetMyBoughtList(ILogin login,int number){
        setLogin(login);
        paramMap.put("token", login.getToken());
        paramMap.put("start", "0");
        paramMap.put("end",""+number);
        paramMap.put("fromPaltform","ds");

    }

    @Override
    protected void parseParam() throws Exception {
        setLogin(ParseParamUtil.parseLogin(paramMap));
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult= JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetMyBoughtListReponse>>(){});
    }

    public ReponseV2<GetMyBoughtListReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
            //fromPaltform=ds返回当当读书和当读小说已购数据,fromPaltform=yc或者为空或者不传默认返回当读小说数据
            List<MediaBought> boughts=MediaBought.getBoughtList(login.getCustId(), paramMap.get("fromPaltform")==null?PlatForm.YC:PlatForm.valueOf(paramMap.get("fromPaltform").toUpperCase()),
                    paramMap.get("start")==null?0:Integer.parseInt(paramMap.get("start"))
                    ,paramMap.get("end")==null?100000:Integer.parseInt(paramMap.get("end")));

            dataVerifyManager.add(new ListVerify(boughts,reponseResult.getData().getBoughtList(),true).setVerifyContent("验证已购列表数据是否正确"));
        }
        else{
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData(),null), VerifyResult.FAILED);
        }

        super.dataVerify();
    }
}
