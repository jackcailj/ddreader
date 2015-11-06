package com.dangdang.readerV5.channel;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.digital.ChannelSubUserDb;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.ChannelsubReponse;

import fitnesse.slim.SystemUnderTest;

/**
 * 频道订阅接口
 * @author guohaiying
 *
 */
public class ChannelSub extends FixtureBase{
	ReponseV2<ChannelsubReponse> jsonResult;	
	
	@SystemUnderTest
	public ChannelSubUserDb db = new ChannelSubUserDb();	
	
	public ChannelSub(){
		addAction("channelSub");
	}

	//批量订阅时使用 fitnesse上频道-》辅助工具
	public boolean doGets(String exceptedCode) throws Exception {	
		boolean flag = true;
		String cName = paramMap.get("cId");
		System.out.println("aaa"+cName);
		List<String>  cIds = new ArrayList<String>();
		cIds = db.getChannelId(cName);
		for(int i=0; i<cIds.size(); i++){
			paramMap.put("cId", cIds.get(i));
			if(!doGet(exceptedCode))
				flag = false;
			Thread.sleep(1000);
		}
		return flag;
	}
	
	@Override
	public void doWork() throws Exception {
		super.doWork();
		jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<ChannelsubReponse>>(){});
	}
	
    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
        	int op = Integer.valueOf(paramMap.get("op"));
        	String cId = paramMap.get("cId");
			int sub = db.isSub(login.getCustId(), cId, op);
			dataVerifyManager.add(new ValueVerify<Integer>(1, sub).setVerifyContent("验证订阅/取消订阅是否成功"));
		
			//验证表channel和channel_sub_user表中的订阅总数是否一致 (由于可以直接改库数据，所以这个验证条件不验证了)
			//dataVerifyManager.add(new ValueVerify<Integer>(db.getChannelSub(cId), db.getChannelSubUser(cId)).setVerifyContent("验证表channel和channel_sub_user表中的订阅总数是否一致"));
			
			//验证json与表channel中的值
			int actual = jsonResult.getData().getSubNumber();
			dataVerifyManager.add(new ValueVerify<Integer>(db.getChannelSub(cId), actual).setVerifyContent("验证json与表channel中的值"));
       
        }
        super.dataVerify();
    }
	  

}
