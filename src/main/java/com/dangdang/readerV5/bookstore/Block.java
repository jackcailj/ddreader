package com.dangdang.readerV5.bookstore;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ExpressionVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.ExtractRegular;
import com.dangdang.digital.meta.MediaSpecialTopic;
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
    		String expectedCode = MediaBlockDb.getBlock(paramMap.get("code")).getContent();
    		String actualCode = jsonResult.getData().getBlock();
    		//验证coverPic
    		if(actualCode.contains("coverPic")){
    			String regular = "coverPic\": \"(.+)";
    			List<String> coverPics = ExtractRegular.get(actualCode, regular);
    			for(int i=0;i<coverPics.size(); i++){
    				dataVerifyManager.add(new ExpressionVerify(expectedCode.contains(coverPics.get(i))).setVerifyContent("验证块"+code+"的coverPic内容" + expectedCode+" "+coverPics.get(i)));   		
    			}
    		}
    		//验证bannerImg
    		if(actualCode.contains("bannerImg")){
    			String regular = "bannerImg\": \"(.+)";
    			List<String> coverPics = ExtractRegular.get(actualCode, regular);
    			for(int i=0;i<coverPics.size(); i++){
    				dataVerifyManager.add(new ExpressionVerify(expectedCode.contains(coverPics.get(i))).setVerifyContent("验证块"+code+"的bannerImg内容"));   		
    			}
    		}
    		
    		//验证icon
    		if(actualCode.contains("icon")){
    			String regular = "icon\": \"(.+)";
    			List<String> coverPics = ExtractRegular.get(actualCode, regular);
    			for(int i=0;i<coverPics.size(); i++){
    				dataVerifyManager.add(new ExpressionVerify(expectedCode.contains(coverPics.get(i))).setVerifyContent("验证块"+code+"的icon内容"));   		
    			}
    		}
    		
    		//验证title
    		if(actualCode.contains("title")){
    			String regular = "title\": \"(.+)";
    			List<String> coverPics = ExtractRegular.get(actualCode, regular);
    			for(int i=0;i<coverPics.size(); i++){
    				dataVerifyManager.add(new ExpressionVerify(expectedCode.contains(coverPics.get(i))).setVerifyContent("验证块"+code+"的title内容"));   		
    			}
    		}
    		
    		//验证toH5Page
    		if(actualCode.contains("toH5Page")){
    			String regular = "toH5Page\": \"(.+)";
    			List<String> coverPics = ExtractRegular.get(actualCode, regular);
    			for(int i=0;i<coverPics.size(); i++){
    				dataVerifyManager.add(new ExpressionVerify(expectedCode.contains(coverPics.get(i))).setVerifyContent("验证块"+code+"的toH5Page内容"));   		
    			}
    		}
    		
    		//验证address
    		if(actualCode.contains("address")){
    			String regular = "address\": \"(.+)";
    			List<String> coverPics = ExtractRegular.get(actualCode, regular);
    			for(int i=0;i<coverPics.size(); i++){
    				dataVerifyManager.add(new ExpressionVerify(expectedCode.contains(coverPics.get(i))).setVerifyContent("验证块"+code+"的address内容"));   		
    			}
    		}
    		
    		//验证serverTime
    		if(actualCode.contains("serverTime")){
    			String regular = "serverTime\": \"(.+)";
    			List<String> coverPics = ExtractRegular.get(actualCode, regular);
    			for(int i=0;i<coverPics.size(); i++){
    				dataVerifyManager.add(new ExpressionVerify(expectedCode.contains(coverPics.get(i))).setVerifyContent("验证块"+code+"的serverTime内容"));   		
    			}
    		}
    		
    		//验证moduleList
    		if(actualCode.contains("moduleList")){
    			if(actualCode !=null){
    				Pattern p = Pattern.compile("\\s*|\t|\r|\n");
    				Matcher m = p.matcher(actualCode);
    				actualCode = m.replaceAll("");
    				dataVerifyManager.add(new ValueVerify<String>(actualCode, expectedCode).setVerifyContent("验证块"+code+"的moduleList内容"));   		
    			}
    		}
    		   		
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
    		}else 
    			if(actualCode.contains("toChannelList")){ //验证toChannelList类型Banner
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
    
    public static void main(String[] args){
    	String s1 = "{\"banner\":[{\"title\":\"HarperColins原版精品\",\"coverPic\":\"http://img61.ddimg.cn/ddreader/yuanchuang/harper0122.jpg\",\"specialId\":\"613\"},{\"title\":\"【纸书】生活图书\",\"coverPic\":\"http://img61.ddimg.cn/ddreader/zhishu/shhuo_330x130_1118.jpg\",\"specialId\":\"338\"}]}";
    	String s2="http://img61.ddimg.cn/ddreader/zhishu/shhuo_330x130_1118.jpg";
    	System.out.println(s1.contains(s2));
    }
 
}
