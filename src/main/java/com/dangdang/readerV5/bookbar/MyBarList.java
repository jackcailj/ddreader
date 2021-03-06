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
			int barSize = 0;
			List<Bar> barList = new ArrayList<Bar>();
			//bar_status(1.待审核 2.通过 3.干预审核 4.下架)
			//用户创建的吧初始状态为"待审核"，无法被其他用户搜索到；后台审核通过后，为“通过”状态，可以被其他用户搜索到
			if(paramMap.get("type").equals("1")){
				sql ="select * from bar where bar_id in ("
						+ "select bar_id from bar_member where 1=1 and "
						+ "cust_id = "+login.getCustId()+" and member_status=3) and bar_status in(1,2)"
						+ " order by bar_id DESC";

			}

			if(paramMap.get("type").equals("2")){
				sql = "SELECT b.* from (select distinct bar_id from bar_member where 1=1 and cust_id = "+login.getCustId()
						   + " and member_status in (1,2) order by create_date desc) a left join bar b on a.bar_id=b.bar_id WHERE b.bar_status in(1,2)";
			}
			if(paramMap.get("type").equals("3")){
				sql = "SELECT b.* from (select distinct bar_id from bar_member where 1=1 and cust_id = "+login.getCustId()
						   + " and member_status in (1,2,3) order by create_date desc) a left join bar b on a.bar_id=b.bar_id WHERE b.bar_status in(1,2)";
			}
			barList.addAll(DbUtil.selectList(Config.BOOKBARDBConfig, sql, Bar.class));
			
		    //一页默认有50个吧列表
			if(barList.size() > 50){
				if(Integer.parseInt(paramMap.get("pageNo")) < 2){
					dataVerifyManager.add(new ValueVerify<Integer>( 
							reponseResult.getData().getBarList().size(), 50, false));
					barSize = 50;
				}
				else{
					int size = barList.size()-50*(Integer.parseInt(paramMap.get("pageNo"))-1);
					dataVerifyManager.add(new ValueVerify<Integer>(
							reponseResult.getData().getBarList().size(), size, false));
					barSize = size;
				}				
			}
			else{
				if(Integer.parseInt(paramMap.get("pageNo")) < 2){
					if(reponseResult.getData().getBarList()==null){
						dataVerifyManager.add(new ValueVerify<Integer>(reponseResult.getData().getBarCnt(), barList.size(), false));
					}
					else{
						dataVerifyManager.add(new ValueVerify<Integer>(reponseResult.getData().getBarList().size(),
							       	                                   barList.size(), false));
						//type=3时没有返回此字段
						if(!paramMap.get("type").equals("3")) {
							dataVerifyManager.add(new ValueVerify<Integer>(
									reponseResult.getData().getBarCnt(), barList.size(), false));
						}
					}
					barSize = barList.size();
				}
				else{
					dataVerifyManager.add(new ValueVerify<Integer>(0, 
							reponseResult.getData().getBarList().size(), false));
				}
				
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
