package com.dangdang.readerV5.bookbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.BaseComment.meta.CloudExperienceInfo;
import com.dangdang.account.meta.AttachAccount;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.RecordVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.enumeration.ExperienceEnum;
import com.dangdang.readerV5.reponse.CreateBarResponse;

/**
 * 创建吧、修改吧简介、修改吧背景图片接口
 * @author wuhaiyan
 */
public class CreateBar  extends FixtureBase {
	ReponseV2<CreateBarResponse>   reponseResult;
	int createBarGrade = 10;
	static String barId;
	static int grade;
	//建吧，加经验和积分各30分
	int experience = 30;
    int integral = 30;
    int account_experience;
    int account_integral;
	
	public ReponseV2<CreateBarResponse> getResult(){
		return reponseResult=JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<CreateBarResponse>>(){});
	}
	
	public  String getBarId(){
		if(reponseResult.getStatus().getCode() == 0){
			return reponseResult.getData().getBarId();
		}
		return null;
	}
	
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		super.setParameters(params);
		if(login!=null&&login.getCustId()!=null){
			String sql = "SELECT * FROM `attach_account` where cust_id="+login.getCustId();
			AttachAccount account = DbUtil.selectOne(Config.ACCOUNTDBConfig, sql, AttachAccount.class);
			grade = account.getAccountGrade();
			account_experience = account.getAccountExperience();
			account_integral = account.getAccountIntegral();
			//用户等级大于等于10时，用户才有权限建吧
			if(grade < createBarGrade){
				DbUtil.executeUpdate(Config.ACCOUNTDBConfig, 
						"update attach_account set account_experience=2500 and account_grade="+createBarGrade+" where cust_id="+login.getCustId());
			}
		}
		String rBarName = "建吧-"+((new Random()).nextInt());
		if(paramMap.get("barName")!=null&&paramMap.get("barName").equals("Random")){
			paramMap.put("barName", rBarName);
			Thread.sleep(3000);
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
//		    list1.add(paramMap.get("barImgUrl"));
//			list2.add(map.get("bar_img_url").toString());
			if(paramMap.get("actionType").equals("1")){
				list1.add(Integer.toString(experience));
				list2.add(Integer.toString(reponseResult.getData().getExperience()));
				list1.add(Integer.toString(integral));
				list2.add(Integer.toString(reponseResult.getData().getIntegral()));
				
				sql = "SELECT * FROM `attach_account` where cust_id="+login.getCustId();
				AttachAccount account = DbUtil.selectOne(Config.ACCOUNTDBConfig, sql, AttachAccount.class);
				list1.add(Integer.toString(account_experience + experience));
				list1.add(Integer.toString(account_integral + integral));
				list2.add(Integer.toString(account.getAccountExperience()));
				list2.add(Integer.toString(account.getAccountIntegral()));
			}
			dataVerifyManager.add(new ValueVerify<List<String>>(list2, list1));

			//验证增加阅历信息，add by cailj
			CloudExperienceInfo cloudExperienceInfo = new CloudExperienceInfo();
			cloudExperienceInfo.setProductId(Long.parseLong(barId));
			cloudExperienceInfo.setCustId(Long.parseLong(login.getCustId()));
			cloudExperienceInfo.setDeviceType(Config.getDevice().toString());
			cloudExperienceInfo.setType(Short.parseShort(ExperienceEnum.CREATE_BAR.toString()));

			dataVerifyManager.add(new RecordVerify(Config.BSAECOMMENT,cloudExperienceInfo));

			super.dataVerify();
		}	
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}
}
