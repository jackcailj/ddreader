package com.dangdang.readerV5.channel;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.ddframework.dataverify.ExpressionVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.db.digital.ChannelDb;
import com.dangdang.db.digital.MediaColumnDb;
import com.dangdang.readerV5.reponse.ChannelColumnReponse;
import com.dangdang.readerV5.reponse.ChannelList;


import fitnesse.slim.SystemUnderTest;

/**
 * 频道列表接口
 * @author guohaiying
 *
 */
public class ChannelColumn extends FixtureBase{
	ReponseV2<ChannelColumnReponse> jsonResult;
		
	@SystemUnderTest
	public MediaColumnDb db = new MediaColumnDb();
	
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
        if(reponseV2Base.getStatus().getCode()==0){
        	log.info("验证频道栏目"+paramMap.get("columnType")+"下的频道列表: ");
        	int num = Integer.valueOf(paramMap.get("end"))-Integer.valueOf(paramMap.get("start"))+1;
        	ChannelColumnReponse dbResult = db.getChannelColumn(paramMap.get("columnType"), num);
        	
			dataVerifyManager.add(new ValueVerify<String>(jsonResult.getData().getColumnCode(), dbResult.getColumnCode()).setVerifyContent("验证ColumnCode"));
			dataVerifyManager.add(new ValueVerify<Integer>(jsonResult.getData().getCount(), dbResult.getCount()).setVerifyContent("验证Count"));
			String jsonName = jsonResult.getData().getName();
			String dbName = dbResult.getName();
			dataVerifyManager.add(new ValueVerify<String>(jsonName.replace("•",""), dbName.replace("?","")).setVerifyContent("验证Name"));
			dataVerifyManager.add(new ValueVerify<String>(jsonResult.getData().getTips(), dbResult.getTips()).setVerifyContent("验证Tips"));
			dataVerifyManager.add(new ValueVerify<Integer>(jsonResult.getData().getTotal(), dbResult.getTotal()).setVerifyContent("验证Total"));
       
			//验证ChannelList
			List<ChannelList> jsonList = jsonResult.getData().getChannelList();
			List<ChannelList> dbList = dbResult.getChannelList();
			for(int i=0; i<jsonList.size(); i++){
				dataVerifyManager.add(new ValueVerify<String>(jsonList.get(i).getChannelId(), dbList.get(i).getChannelId()).setVerifyContent("验证ChannelId"));
				dataVerifyManager.add(new ValueVerify<String>(jsonList.get(i).getIcon(), dbList.get(i).getIcon()).setVerifyContent("验证Icon"));
				dataVerifyManager.add(new ValueVerify<String>(jsonList.get(i).getOwnerType(), dbList.get(i).getOwnerType()).setVerifyContent("验证OwnerType"));
				int jsonSubNumber = jsonList.get(i).getSubNumber();
				int dbSubNumber = dbList.get(i).getSubNumber();
				dataVerifyManager.add(new ExpressionVerify(Math.abs(jsonSubNumber-dbSubNumber)<=10).setVerifyContent("验证SubNumber,jsonValue"+jsonSubNumber+" dbValue"+dbSubNumber));
				dataVerifyManager.add(new ValueVerify<String>(jsonList.get(i).getTitle(), dbList.get(i).getTitle()).setVerifyContent("验证Title"));				
			}
			     
        }
        super.dataVerify();
    }
    
	//获取某栏目下频道的总数+1
	public int getNum(String columnCode) throws Exception{
		return ChannelDb.getChannelList(columnCode).size()+ 1;
	}
	
}
