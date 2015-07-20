package com.dangdang.readerV5.bookbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.bookbar.meta.Bar;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.BarListResponse;

public class MyBarList extends FixtureBase {
	ReponseV2<BarListResponse>   reponseResult;
	 
	public ReponseV2<BarListResponse> getResult(){
		return reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<BarListResponse>>(){});
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			String sql = null;
			if(paramMap.get("type").equals("1")){
				sql ="select * from bar where bar_id in ("
						+ "select bar_id from bar_member where 1=1 and "
						+ "cust_id = "+login.getCustId()+" and member_status=3)";
			}
			if(paramMap.get("type").equals("2")){
				sql ="select * from bar where bar_id in ("
						+ "select bar_id from bar_member where 1=1 and "
						+ "cust_id = "+login.getCustId()+" and member_status in (1,2))";
			}
			List<Bar> barList = DbUtil.selectList(Config.BOOKBARDBConfig, sql, Bar.class);
			//一页默认有50个吧列表
			if(barList.size() > 50){
				if(Integer.parseInt(paramMap.get("pageNo")) < 2){
					dataVerifyManager.add(new ValueVerify<Integer>(50, 
							reponseResult.getData().getBarList().size(), false));
				}
				else{
					int size = barList.size()-50*(Integer.parseInt(paramMap.get("pageNo"))-1);
					dataVerifyManager.add(new ValueVerify<Integer>(size, 
							reponseResult.getData().getBarList().size(), false));
				}
				
			}
			else{
				if(Integer.parseInt(paramMap.get("pageNo")) < 2){
					dataVerifyManager.add(new ValueVerify<Integer>(barList.size(), 
							reponseResult.getData().getBarList().size(), false));
				}
				else{
					dataVerifyManager.add(new ValueVerify<Integer>(0, 
							reponseResult.getData().getBarList().size(), false));
				}
				
			}
			List<Map<String,String>> list1 = new ArrayList<Map<String,String>>();
			List<Map<String,String>> list2 = new ArrayList<Map<String,String>>();
			for(int i=0; i<reponseResult.getData().getBarList().size(); i++){
				Map<String,String> map1 = new HashMap<String,String>();
				Map<String,String> map2 = new HashMap<String,String>();
				map1.put("barId", barList.get(i).getBarId().toString());
				map1.put("barName", barList.get(i).getBarName().toString());
				map2.put("barId", reponseResult.getData().getBarList().get(i).getBarId().toString());
				map2.put("barName", reponseResult.getData().getBarList().get(i).getBarName().toString());
				list1.add(map1);
				list2.add(map2);
			}
			dataVerifyManager.add(new ListVerify(list1, list2,false));
			super.dataVerify();
		}
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
		
	}
}
