package com.dangdang.readerV5.bookbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.Util;
import com.dangdang.readerV5.reponse.CreateBarResponse;

import fitnesse.slim.SystemUnderTest;
/**
 * 创建吧、修改吧简介、修改吧背景图片接口
 * @author wuhaiyan
 */
public class CreateBar  extends FixtureBase {
	ReponseV2<CreateBarResponse>   reponseResult;
	int createBarGrade = 10;
	static String barId;
	static int grade;
	
	public ReponseV2<CreateBarResponse> getResult(){
		return reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<CreateBarResponse>>(){});
	}
	
	public static String getBarId(){
		return barId;
	}
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		super.setParameters(params);
		if(login!=null&&login.getCustId()!=null&& grade < createBarGrade){
			String sql = "SELECT account_grade FROM `attach_account` where cust_id="+login.getCustId();
			grade = Integer.parseInt(DbUtil.selectOne(Config.ACCOUNTDBConfig, sql).get("account_grade").toString());
			//用户等级大于等于10时，用户才有权限建吧
			if(grade < createBarGrade){
				DbUtil.executeUpdate(Config.ACCOUNTDBConfig, "update attach_account set account_grade="+createBarGrade+" where cust_id="+login.getCustId());
			}
		}
		String rBarName = "建吧-"+((new Random()).nextInt());
		if(paramMap.get("barName")!=null&&paramMap.get("barName").equals("Random")){
			paramMap.put("barName", rBarName);
		}
		if(barId!=null&&!(barId.isEmpty())){
			String sql = "SELECT bar_name, bar_desc, bar_img_url  FROM `bar` where bar_id="+barId;
			Map<String,Object> map = DbUtil.selectOne(Config.BOOKBARDBConfig, sql);
			String barName = map.get("bar_name").toString();
			if(paramMap.get("barId")!=null&&paramMap.get("barId").equals("FromDB")){
				paramMap.put("barId", barId);
			}
			if(paramMap.get("barName")!=null&&paramMap.get("barName").equals("FromDB")){
				paramMap.put("barName", barName);
			}
			if(paramMap.get("barDesc")!=null&&paramMap.get("barDesc").equals("FromDB")){
				paramMap.put("barDesc", map.get("bar_desc").toString());
			}
			if(paramMap.get("barImgUrl")!=null&&paramMap.get("barImgUrl").equals("FromDB")){
				paramMap.put("barImgUrl",  map.get("bar_img_url").toString());
			}
		}		
  	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		List<String> list1 = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){
			barId = reponseResult.getData().getBarId();
			String sql = "SELECT bar_id, bar_name, bar_desc, bar_img_url FROM `bar` where bar_id="+barId;
			Map<String,Object>  map = DbUtil.selectList(Config.BOOKBARDBConfig, sql).get(0);
			if(paramMap.get("barId")!=null){
				list1.add(paramMap.get("barId"));
				list2.add(map.get("bar_id").toString());
			}
			list1.add(paramMap.get("barName"));
			list2.add(map.get("bar_name").toString());
			list1.add(paramMap.get("barDesc"));
			list2.add(map.get("bar_desc").toString());
			list1.add(paramMap.get("barImgUrl"));
			list2.add(map.get("bar_img_url").toString());
			dataVerifyManager.add(new ValueVerify<List<String>>(list1, list2));
			super.dataVerify();
		}
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}
}
