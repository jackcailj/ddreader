package com.dangdang.reader.functional.personcenter;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.core.TestDevice;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ecms.meta.DeviceImageUpload;
import com.dangdang.reader.functional.reponse.GetDeviceStartPageInfoReponse;

/*
 * author:cailj
 * desc：获取设备启动页
 */
public class GetDeviceStartPageInfo extends FunctionalBaseEx{

	ReponseV2<GetDeviceStartPageInfoReponse> reponseResult;
	
	public ReponseV2<GetDeviceStartPageInfoReponse> getReponseResult() {
		return reponseResult;
	}


	public GetDeviceStartPageInfo(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("getDeviceStartPageInfo");
	}
	
	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		super.doWork();
		reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetDeviceStartPageInfoReponse>>(){});
	}
	
	@Override
	protected void dataVerify() throws Exception {
		// TODO Auto-generated method stub
		if(reponseResult.getStatus().getCode()==0){
			String selectString = "select PATH,IMAGE_DIGITAL_INCREMENTS_VERSION from device_image_upload where DEVICE_TYPE='"+Config.getDevice().toString().toLowerCase()+"' and status=1 "+
								" and (case when channelCode is null then true else (case when channelCode like '%"+Config.getDevice().toString().toLowerCase()+"%' then true else false end) end)"+  
								" and (case when clientVersion is null then true else (case when clientVersion like '%"+paramMap.get("clientVersionNo").toString()+"%' then true else false end) end)"+ 
								" order by id desc ";

            //如果没有符合分辨率的字段，默认选择第一个
			List<DeviceImageUpload> expecteds = DbUtil.selectList(Config.ECMSDBConfig, selectString, DeviceImageUpload.class);
            DeviceImageUpload expected=expecteds.get(0);
            for(DeviceImageUpload di : expecteds ){
                if(expecteds.contains(paramMap.get("resolution").toString())){
                    expected=di;
                    break;
                }
            }
			dataVerifyManager.add(new ValueVerify<String>(reponseResult.getData().getImagePath(), expected.getPath()));
		}
		super.dataVerify();
	}
}
