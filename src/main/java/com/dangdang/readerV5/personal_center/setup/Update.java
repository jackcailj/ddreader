package com.dangdang.readerV5.personal_center.setup;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.UpdateResponse;

/**
 * 升级接口
 * @author guohaiying
 */
public class Update extends FixtureBase{
	ReponseV2<UpdateResponse> response;
	
	@Override
	protected void dataVerify() throws Exception {
		if(reponseV2Base.getStatus().getCode()==0){
			if(paramMap.get("enable_hl")!=null||paramMap.get("enable_hl").equals("1")){
	        		
			}
	        	
		}
	}
	
}
