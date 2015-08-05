package com.dangdang.readerV5.personal_center;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.ChannelSQL;
import com.dangdang.digital.meta.Channel;
import com.dangdang.readerV5.reponse.MyChannelListReponse;
import com.dangdang.ucenter.UserInfoSql;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by cailianjie on 2015-8-1.
 */
public class MyChannelList extends FixtureBase{

    ReponseV2<MyChannelListReponse> reponseResut;

    public MyChannelList(){}

    public ReponseV2<MyChannelListReponse> getReponseResut() {
        return reponseResut;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResut = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<MyChannelListReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResut.getStatus().getCode()==0){
            if(StringUtils.isBlank(paramMap.get("custId"))){
                //获取自己的
                List<Channel> subChannels = ChannelSQL.getSubChannels(login.getCustId());
                if(reponseResut.getData().getChannelList()==null){
                    dataVerifyManager.add(new ValueVerify<Integer>(0,subChannels.size()));
                }
                else {
                    dataVerifyManager.add(new ListVerify(reponseResut.getData().getChannelList(), subChannels, true));
                }
            }
            else{
                //获取他人的

                List<Channel> subChannels = ChannelSQL.getSubChannels(UserInfoSql.getCustIdByPubId(paramMap.get("custId")));
                if(reponseResut.getData().getChannelList()==null){
                    dataVerifyManager.add(new ValueVerify<Integer>(0,subChannels.size()));
                }
                else {
                    dataVerifyManager.add(new ListVerify(reponseResut.getData().getChannelList(), subChannels, true));
                }
            }
        }
        else{
            dataVerifyManager.add(new ValueVerify<Object>(reponseResut.getData().getChannelList(), null), VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
