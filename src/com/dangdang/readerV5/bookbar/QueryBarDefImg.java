package com.dangdang.readerV5.bookbar;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.RegexVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.BarDefImgResponse;

/**
 * 获取吧默认背景图片接口
 * @author wuhaiyan
 */
public class QueryBarDefImg  extends FixtureBase {

    ReponseV2<BarDefImgResponse>   reponseResult;
	
	public ReponseV2<BarDefImgResponse> getResult(){
		return reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<BarDefImgResponse>>(){});
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			//String sql = "SELECT * FROM default_image";
			//List<DefaultImage>  list = DbUtil.selectList(Config.BOOKBARDBConfig, sql, DefaultImage.class);
			String pattern = "(http|ftp|https)://(img.*)?";		
			for(int i=0; i<reponseResult.getData().getBarDefImg().size(); i++){
			    dataVerifyManager.add(new RegexVerify(pattern, reponseResult.getData().getBarDefImg().get(i)));
			}
			super.dataVerify();
		}	
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}
}
