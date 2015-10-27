package com.dangdang.readerV5.personal_center.cloud_sync_read;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.BaseComment.meta.CloudExperienceInfo;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.db.comment.CloudSyncSql;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.GetExperienceInfoListReponse;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cailianjie on 2015-9-22.
 */
public class GetExperienceInfoList extends FixtureBase{

    ReponseV2<GetExperienceInfoListReponse> reponseResult;

    public GetExperienceInfoList(){URL= Config.getMobileUrl();}

    public ReponseV2<GetExperienceInfoListReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetExperienceInfoListReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){

           /* Integer pageSize=Integer.parseInt(paramMap.get("pageSize"));
            List<CloudExperienceInfo> cloudExperienceInfos= CloudSyncSql.getExperienceInfos(login.getCustId(),
                    pageSize + 1,
                    StringUtils.isBlank(paramMap.get("recordTime")) ? null : Long.parseLong(paramMap.get("recordTime")),
                    StringUtils.isBlank(paramMap.get("isIncrement")) ? false : Boolean.getBoolean(paramMap.get("isIncrement"))
            );

            List<CloudExperienceInfoEx> cloudExperienceInfoExes = new ArrayList<CloudExperienceInfoEx>();
            for(CloudExperienceInfo info: cloudExperienceInfos) {
                CloudExperienceInfoEx infoEx = new CloudExperienceInfoEx();
                infoEx.setCustId(info.getCustId());
                infoEx.setDeviceType(info.getDeviceType());
                infoEx.setProductId(info.getProductId());
                infoEx.setRecordTime(info.getRecordTime());
                infoEx.setType(info.getType());
                infoEx.setRemarks(info.getRemarks());
                infoEx.setId(info.getExperienceId());

                cloudExperienceInfoExes.add(infoEx);

            }


            Integer hasNextPage=0;
            if(cloudExperienceInfos.size()>pageSize){
                hasNextPage=1;
                cloudExperienceInfoExes.remove(cloudExperienceInfoExes.size()-1);
            }

            dataVerifyManager.add(new ListVerify(reponseResult.getData().getExperienceInfoList(),cloudExperienceInfoExes,true));

            if(cloudExperienceInfoExes.size()>0) {
                dataVerifyManager.add(new ValueVerify<Long>(reponseResult.getData().getRecordTime(), cloudExperienceInfoExes.get(cloudExperienceInfoExes.size()-1).getRecordTime()));
            }
            else{
                dataVerifyManager.add(new ValueVerify<Long>(reponseResult.getData().getRecordTime(),Long.valueOf(0)));

            }


            if(StringUtils.isBlank(paramMap.get("isIncrement"))){
                dataVerifyManager.add(new ValueVerify<Integer>(reponseResult.getData().getIsHasNextPage(),null).setVerifyContent("验证IsHasNextPage"));
                dataVerifyManager.add(new ValueVerify<Integer>(reponseResult.getData().getIncrementIsHasNextPage(),hasNextPage).setVerifyContent("验证IncrementIsHasNextPage"));
            }
            else{
                if(Boolean.parseBoolean(paramMap.get("isIncrement"))){
                    dataVerifyManager.add(new ValueVerify<Integer>(reponseResult.getData().getIsHasNextPage(),null).setVerifyContent("验证IsHasNextPage"));
                    dataVerifyManager.add(new ValueVerify<Integer>(reponseResult.getData().getIncrementIsHasNextPage(),hasNextPage).setVerifyContent("验证IncrementIsHasNextPage"));
                }
                else{
                    dataVerifyManager.add(new ValueVerify<Integer>(reponseResult.getData().getIsHasNextPage(),hasNextPage).setVerifyContent("验证IsHasNextPage"));
                    dataVerifyManager.add(new ValueVerify<Integer>(reponseResult.getData().getIncrementIsHasNextPage(),null).setVerifyContent("验证IncrementIsHasNextPage"));
                }
            }*/

        }
        else{
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getExperienceInfoList(), null), VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
