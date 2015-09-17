package com.dangdang.reader.functional.gd_tongji;

import com.dangdang.autotest.common.FixtureBase;
import org.hibernate.id.GUIDGenerator;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Created by cailianjie on 2015-7-10.
 */
public class GdtClickAd extends FixtureBase{


    @Override
    public void parseParameters(Map<String, String> params) throws Exception {
        paramMap=params;
        paramMap.put("muid",MD5Utils.getInstance().cell32(paramMap.get("muid")));
        paramMap.put("click_time", ""+new Date().getTime());
        paramMap.put("click_id", UUID.randomUUID().toString());
    }

    public  static  void main(String[] args){
        System.out.println(MD5Utils.getInstance().cell32("320B9284-63D8-42A1-A434-81991994D287"));
    }
}
