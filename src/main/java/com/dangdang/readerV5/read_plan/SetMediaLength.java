package com.dangdang.readerV5.read_plan;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.MediaDb;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.digital.meta.Media;



/**
 * Created by cailianjie on 2016-4-22.
 */
public class SetMediaLength extends FixtureBase{

    public SetMediaLength(){}

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
            Media media = MediaDb.getMedia(paramMap.get("mediaId"));
            if(media!=null){
                dataVerifyManager.add(new ValueVerify<String>(media.getMediaLength().toString(),paramMap.get("mediaLength"),false));
            }
        }
        super.dataVerify();
    }
}
