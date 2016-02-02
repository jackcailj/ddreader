package com.dangdang.readerV5.personal_center.cloud_sync_read;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.BaseComment.meta.CloudBookMark;
import com.dangdang.BaseComment.meta.CloudBookNote;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.RecordVerify;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Created by cailianjie on 2015-9-21.
 */
public class UpdateBookCloudSyncReadInfo extends FixtureBase{



    public UpdateBookCloudSyncReadInfo(){
        bPost=true;
    }


    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
            List<CloudBookNote> noteInfos = JSONObject.parseArray(paramMap.get("noteInfo"),CloudBookNote.class);
            List<CloudBookMark> markInfos = JSONObject.parseArray(paramMap.get("markInfo"),CloudBookMark.class);

            if(!CollectionUtils.isEmpty(noteInfos)){
                for(CloudBookNote info:noteInfos){
                    info.setCustId(Long.parseLong(login.getCustId()));
                    info.setModifyTime(Long.MIN_VALUE);
                    dataVerifyManager.add(new RecordVerify(Config.BSAECOMMENT,info));
                }
            }

            if(!CollectionUtils.isEmpty(markInfos)){
                for(CloudBookMark mark:markInfos){
                    mark.setCustId(Long.parseLong(login.getCustId()));
                    mark.setModifyTime(Long.MIN_VALUE);
                    dataVerifyManager.add(new RecordVerify(Config.BSAECOMMENT,mark));
                }
            }
        }
        super.dataVerify();
    }
}
