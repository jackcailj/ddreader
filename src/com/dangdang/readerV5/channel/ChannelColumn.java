package com.dangdang.readerV5.channel;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ExpressionVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.Channel;
import com.dangdang.db.digital.MediaColumnContentDb;
import com.dangdang.db.digital.MediaColumnDb;
import com.dangdang.db.ucenter.LoginRecordDb;
import com.dangdang.readerV5.reponse.ChannelColumnReponse;
import com.dangdang.readerV5.reponse.ChannelList;
import com.dangdang.readerV5.reponse.UserBaseInfo;

/**
 * 频道列表接口
 * @author guohaiying
 */
public class ChannelColumn extends FixtureBase{
	ReponseV2<ChannelColumnReponse> jsonResult;
		
	public ChannelColumn(){
    	addAction("column");
	}

    @Override
    public void doWork() throws Exception {
        super.doWork();
        jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<ChannelColumnReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0&&jsonResult.getData().getCount()!=0){
        	String columnType = paramMap.get("columnType");
        	//验证 name 
        	String actualName = jsonResult.getData().getName();
        	String expectedName = MediaColumnDb.getMediaColumn(columnType).getName();
        	dataVerifyManager.add(new ValueVerify<String>(actualName.replace("•",""), expectedName.replace("?","")).setVerifyContent("验证栏目name"));   	       	      	
        	
        	//验证 channelId title subNumber icon description
        	List<ChannelList> actualList = jsonResult.getData().getChannelList();
        	int num = Integer.valueOf(paramMap.get("end"))-Integer.valueOf(paramMap.get("start"))+1;
        	List<Channel> expectedList = MediaColumnContentDb.getChannelList(columnType, num);      	
        	for(int i=0; i<actualList.size();i++){
        		String channelId = actualList.get(i).getChannelId();
        		dataVerifyManager.add(new ValueVerify<String>(channelId, String.valueOf(expectedList.get(i).getChannelId())).setVerifyContent("验证channelId"));
        		
        		//验证title
        		String actualTitle = actualList.get(i).getTitle().replace("∇","").replace("♪","");
        		String expectedTitle = expectedList.get(i).getTitle().replace("?","");
        		dataVerifyManager.add(new ValueVerify<String>(actualTitle, expectedTitle).setVerifyContent("验证title"));
        		
        		//验证subNumber
        		int actualSubNumber = actualList.get(i).getSubNumber();
        		int expectedSubNumber = expectedList.get(i).getSubNumber();
        		System.out.println(channelId+"的actualSubNumber： "+ actualSubNumber);
        		System.out.println(channelId+"的expectedSubNumber： "+ expectedSubNumber);
        		dataVerifyManager.add(new ExpressionVerify(Math.abs(actualSubNumber-expectedSubNumber)<10?true:false).setVerifyContent("验证subNumber"));
        		dataVerifyManager.add(new ValueVerify<String>(actualList.get(i).getIcon(), expectedList.get(i).getIcon()).setVerifyContent("验证icon"));
        		dataVerifyManager.add(new ValueVerify<String>(actualList.get(i).getDescription().replace("∇","").replace("❀",""), expectedList.get(i).getDescription().replace("?","")).setVerifyContent("验证description"));
        		
        		//验证 nickName ChannelOwner
        		UserBaseInfo actualUser = actualList.get(i).getUserBaseInfo();
        		Map<String, Object> expectedUser = LoginRecordDb.getUserLoginRecord(actualList.get(i).getChannelId());
        		if(expectedUser==null||expectedUser.equals(null))
        			dataVerifyManager.add(new ValueVerify<String>(actualUser.getChannelOwner(), "0").setVerifyContent("验证ChannelOwner"));
        		else{
        			dataVerifyManager.add(new ValueVerify<String>(actualUser.getNickNameAll(), expectedUser.get("cust_nickname").toString()).setVerifyContent("验证nickName"));
        			dataVerifyManager.add(new ValueVerify<String>(actualUser.getChannelOwner(), String.valueOf(expectedUser.get("channel_owner").toString())).setVerifyContent("验证ChannelOwner"));
        		}
        	}        	
        	dataVerifyManager.add(new ValueVerify<Integer>(actualList.size(), expectedList.size()).setVerifyContent("验证ChannelList size"));
     
        }
        super.dataVerify();
    }
	
	public static void main(String[] args){
		String actualTitle = "欧巴（≧∇≦）传递爱与正能量";
		String actual = actualTitle.replace("∇","");
		System.out.println(actual);
		
	}
	
}
