package com.dangdang.readerV5.homepage;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.ChannelDb;
import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.Channel;
import com.dangdang.enumeration.ChannelOwnerStatus;
import com.dangdang.enumeration.ChannelShelfStatus;
import com.dangdang.enumeration.ChannelStatus;
import com.dangdang.readerV5.reponse.ChannelInfo;
import com.dangdang.readerV5.reponse.GetMyChannelInfoReponse;

/**
 * Created by cailianjie on 2016-3-23.
 */
public class GetMyChannelInfo extends FixtureBase{

   ReponseV2<GetMyChannelInfoReponse> reponseResult;

   public GetMyChannelInfo(){}

   public ReponseV2<GetMyChannelInfoReponse> getReponseResult() {
      return reponseResult;
   }

   @Override
   public void doWork() throws Exception {
      super.doWork();

      reponseResult= JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetMyChannelInfoReponse>>(){});
   }

   @Override
   protected void dataVerify() throws Exception {
      if(reponseResult.getStatus().getCode()==0){

         Channel mychannel = ChannelDb.getChannelIdByCustId(VariableStore.get(paramMap.get("userPubId")).toString(),ChannelShelfStatus.SHANGJIA);

         ChannelInfo expectChannelInfo =new ChannelInfo();
         if(mychannel!=null){
            expectChannelInfo.setId(mychannel.getChannelId());
            expectChannelInfo.setDescription(mychannel.getDescription());
            expectChannelInfo.setSubNumber(mychannel.getSubNumber());
            expectChannelInfo.setTitle(mychannel.getTitle());
            expectChannelInfo.setIcon(mychannel.getIcon());
         }
         dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getChannelInfo(),expectChannelInfo,true));
      }
      else {
         dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getChannelInfo(),new ChannelInfo(),true));
      }

      super.dataVerify();
   }
}
