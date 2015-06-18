package com.dangdang.readerV5.discovery;

import java.util.Map;



import org.testng.Assert;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.autotest.config.Config;
import com.dangdang.common.functional.login.Login;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.drivers.HttpDriver;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.reader.functional.param.model.ParseResult;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.reponse.Data;

/**
 * 精品阅读收藏，包括（收藏，取消，收藏列表）    action：storeUpDigest
 * @author wuhaiyan 
 */
public class StoreupDigest extends FixtureBase{

	ReponseV2<Data> reponseResult;
		
	public ReponseV2<Data> getResponseResult(){
		return reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<Data>>(){});
	}
	
	public String getResult(){
		return result.toString();
	}
	
	public String getMap(){
		return paramMap.toString();
	}
	
	String digestId;
	public void setParameters(Map<String, String> params) throws Exception {
		String sql = "SELECT id FROM `media_digest` ORDER BY rand() limit 1";
		digestId = DbUtil.selectOne(Config.YCDBConfig, sql).get("id").toString();
		if(params.get("digestIds").equals("FromDB")){
			params.put("digestIds", digestId);
		}
		parseParameters(params);
	}
	
	//@Override
	public void dataVerify(int status) throws Exception {
	
	}
	
//	//media/api2.go?action=storeUpDigest&op=save&digestIds=319&token=e5ec8456a8a27f2bb0ef1bb4c49597d3&returnType=json&deviceType=FP_Android&channelId=60000&clientVersionNo=1.0.1&serverVersionNo=1.0.0&permanentId=20150522070611523664833360843852483&deviceSerialNo=864338010887072&macAddr=30%3Af3%3A1d%3A5c%3A96%3Abb&resolution=720*1280&clientOs=4.2.1&platformSource=FP-P&channelType=all&token=e5ec8456a8a27f2bb0ef1bb4c49597d3//media/api2.go?action=storeUpDigest&op=save&digestIds=319&token=e5ec8456a8a27f2bb0ef1bb4c49597d3&returnType=json&deviceType=FP_Android&channelId=60000&clientVersionNo=1.0.1&serverVersionNo=1.0.0&permanentId=20150522070611523664833360843852483&deviceSerialNo=864338010887072&macAddr=30%3Af3%3A1d%3A5c%3A96%3Abb&resolution=720*1280&clientOs=4.2.1&platformSource=FP-P&channelType=all&token=e5ec8456a8a27f2bb0ef1bb4c49597d3
//	//GET /media/api2.go?action=storeUpDigest&op=get&token=cc9c1943d250f787a0ff542b42deef5d&returnType=json&deviceType=FP_Android&channelId=61000&clientVersionNo=1.0.0&serverVersionNo=1.0.0&permanentId=20150521110509038381237169605661659&deviceSerialNo=864587026900416&macAddr=c0%3Aee%3Afb%3A04%3A27%3A3e&resolution=1080*1920&clientOs=4.3&platformSource=FP-P&channelType=all&token=cc9c1943d250f787a0ff542b42deef5d 
//    public static void main(String[] args) throws Exception{
//    	dataVerify(0);
//    }
	
//	SELECT * FROM `media_storeup` where user_name='whytest@dd.con';

}

