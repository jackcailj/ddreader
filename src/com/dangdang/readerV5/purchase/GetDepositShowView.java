package com.dangdang.readerV5.purchase;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.autotest.common.PlatForm;
import com.dangdang.autotest.config.Config;
import com.dangdang.common.functional.login.ILogin;
import com.dangdang.ddframework.core.TestDevice;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.MediaActivityInfo;
import com.dangdang.ecms.EcmsUtil;
import com.dangdang.reader.functional.reponse.ActivityInfoListReponse;
import com.dangdang.readerV5.reponse.GetDepositShowViewReponse;

import java.util.List;


/**
 * Created by cailianjie on 2015-7-31.
 */
public class GetDepositShowView extends FixtureBase{

    ReponseV2<GetDepositShowViewReponse> reponseResult;

    public  GetDepositShowView(){}

    public  GetDepositShowView(ILogin login,PlatForm fromPlat,String payment){
        setLogin(login);
        paramMap.put("token", login.getToken());
        paramMap.put("fromPaltform",fromPlat.toString());
        paramMap.put("paymentId",payment);

    }

    public ReponseV2<GetDepositShowViewReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetDepositShowViewReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
      /*  if(reponseResult.getStatus().getCode()==0){
            //操作成功，验证数据正确
            String fromPaltform="";
            if(paramMap.get("fromPaltform")==null){
                fromPaltform= PlatForm.DDREADER_ANDROID.toString();
            }
            else {
                fromPaltform= Config.getDevice()== TestDevice.ANDROID?paramMap.get("fromPaltform").toString():PlatForm.DDREADER_IOS.toString();
            }
			*//*String selectString="select  deposit_gift_read_price,deposit_money,deposit_read_price,end_time,relation_product_id,start_time"
							+" from media_activity_info where from_paltform='"+fromPaltform+"' "+(fromPaltform.equals("ds_ios")?" and relation_product_id like '"+Config.getDevice().toString()+"%'":" and activity_name like "+(Config.getEnvironment()==TestEnvironment.ONLINE?"'%支付宝%'":"'%微信%'"))+ " and status=1"
						    +" ORDER BY  deposit_read_price";*//*
            List<MediaActivityInfo> infos = EcmsUtil.getDepositShowView(fromPaltform);
            //返回数量不能为0
            dataVerifyManager.add(new ValueVerify<Integer>(reponseResult.getData().getdSDepositPayInfoVo().size(),0), VerifyResult.FAILED);
            //返回数据与查询数据一致
            dataVerifyManager.add(new ListVerify(infos, reponseResult.getData().getdSDepositPayInfoVo(), true));
        }
        else {
            //dataVerifyManager.add(new ValueVerify<>(infos, reponseResult.getData(), true));
        }*//*       //操作成功，验证数据正确
            String fromPaltform="";
            if(paramMap.get("fromPaltform")==null){
                fromPaltform= PlatForm.DDREADER_ANDROID.toString();
            }
            else {
                fromPaltform= Config.getDevice()== TestDevice.ANDROID?paramMap.get("fromPaltform").toString():PlatForm.DDREADER_IOS.toString();
            }
			*//*String selectString="select  deposit_gift_read_price,deposit_money,deposit_read_price,end_time,relation_product_id,start_time"
							+" from media_activity_info where from_paltform='"+fromPaltform+"' "+(fromPaltform.equals("ds_ios")?" and relation_product_id like '"+Config.getDevice().toString()+"%'":" and activity_name like "+(Config.getEnvironment()==TestEnvironment.ONLINE?"'%支付宝%'":"'%微信%'"))+ " and status=1"
						    +" ORDER BY  deposit_read_price";*//*
            List<MediaActivityInfo> infos = EcmsUtil.getDepositShowView(fromPaltform);
            //返回数量不能为0
            dataVerifyManager.add(new ValueVerify<Integer>(reponseResult.getData().getdSDepositPayInfoVo().size(),0), VerifyResult.FAILED);
            //返回数据与查询数据一致
            dataVerifyManager.add(new ListVerify(infos, reponseResult.getData().getdSDepositPayInfoVo(), true));
        }
        else {
            //dataVerifyManager.add(new ValueVerify<>(infos, reponseResult.getData(), true));
        }*/
        super.dataVerify();
    }
}
