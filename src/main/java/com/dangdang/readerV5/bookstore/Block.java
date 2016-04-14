package com.dangdang.readerV5.bookstore;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ExpressionVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.ExtractRegular;
import com.dangdang.digital.meta.MediaSpecialTopic;
import com.dangdang.db.digital.ChannelArticlesDigestDb;
import com.dangdang.db.digital.MediaBlockDb;
import com.dangdang.db.digital.MediaColumnDb;
import com.dangdang.db.digital.MediaSpecialTopicDb;
import com.dangdang.readerV5.reponse.BlockReponse;

/**
 * Banner
 * @author guohaiying
 */
public class Block extends FixtureBase{
	ReponseV2<BlockReponse> jsonResult;
	static List<String> codes;
	
	static{
		try {
			codes = MediaBlockDb.getBlocks();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	  
    @Override
    public void doWork() throws Exception {
    	jsonResult = null;
    	String code = paramMap.get("code");
    	if(code.equals("auto")){
    		for(int i=0; i<codes.size()	; i++){   	
    			paramMap.put("code",codes.get(i));
    			super.doWork();
    			jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<BlockReponse>>(){});
    			verify(codes.get(i));
    		}
    	}else{
    		super.doWork();
			jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<BlockReponse>>(){});
			verify(code);
    	}
    }
    
    public void verify(String code) throws Exception{
      	if(reponseV2Base.getStatus().getCode()==0){	
    		String expectedCode = MediaBlockDb.getBlock(paramMap.get("code"));
    		String actualCode = jsonResult.getData().getBlock();
    		dataVerifyManager.add(new ValueVerify<String>(jsonResult.getData().getBlock(), expectedCode).setVerifyContent("验证块"+code+"的内容"));
    		
    		//验证专题类型Banner  验证专题id和column_code值在表中是否存在
    		if(actualCode.contains("specialId")){
    			String regular= "specialId\": \"(\\d+)";
    			List<String> specialIds = ExtractRegular.get(actualCode, regular);
    			for(int i=0; i<specialIds.size(); i++){
    				String specialId = specialIds.get(i);
    				MediaSpecialTopic topic = MediaSpecialTopicDb.getSpecialTop(specialId);
    				dataVerifyManager.add(new ExpressionVerify(topic==null?false:true).setVerifyContent("验证块"+code+"的specialId"));
    				if(topic!=null){
    					String columnCode = topic.getColumnCode();
    					dataVerifyManager.add(new ValueVerify<String>(MediaColumnDb.getRecordNum(columnCode), "1").setVerifyContent("验证块"+code+"的specialId - columnCode"));
    				}
    			}
//    		}	 
//    		else if(actualCode.contains("booklist")||actualCode.contains("columnCode")){ //验证booklist类型Banner
//    			String regular= "columnCode\": \"(.+)";
//    			List<String> bookLists = ExtractRegular.get(actualCode, regular);
//    			for(int i=0; i<bookLists.size(); i++){
//    				String bookList = bookLists.get(i);
//    				dataVerifyManager.add(new ValueVerify<String>(MediaColumnDb.getRecordNum(bookList), "1").setVerifyContent("验证块"+code+"的booklist or columnCode"));
//    			}   			
    		}else if(actualCode.contains("toChannelList")){ //验证toChannelList类型Banner
    			String regular= "toChannelList\": \"(.+)\"";
    			List<String> channelLists = ExtractRegular.get(actualCode, regular);
    			for(int i=0; i<channelLists.size(); i++){
    				String channelList = channelLists.get(i);
    				dataVerifyManager.add(new ValueVerify<String>(MediaColumnDb.getRecordNum(channelList), "1").setVerifyContent("验证块"+code+"的channelList"));
    			}
    		}
//    		else if(actualCode.contains("toChannelDetail")){ //验证toChannelDetail
//    			String regular= "toChannelDetail\": \"(\\d+)";
//    			List<String> channelDetails = ExtractRegular.get(actualCode, regular);
//    			for(int i=0; i<channelDetails.size(); i++){
//    				String channelDetail = channelDetails.get(i);
//    				dataVerifyManager.add(new ValueVerify<String>(ChannelArticlesDigestDb.getRecordNum(channelDetail), "1").setVerifyContent("验证块"+code+"的channelDetail"));
//    			}
//    		}
    		//else if(){ //toProduct
    			
    		//}
			//dataVerifyManager.add(new ValueVerify<String>(jsonResult.getData().getBlock(), expectedCode).setVerifyContent("验证块"+code+"的内容"));			
		}
    }  
    
    @Override
    protected void dataVerify() throws Exception {
        super.dataVerify();    	
	}
 
}
