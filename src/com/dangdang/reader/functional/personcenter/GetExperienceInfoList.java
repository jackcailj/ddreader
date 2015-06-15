package com.dangdang.reader.functional.personcenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.metamodel.source.annotations.entity.ConfiguredClassType;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.autotest.config.Config;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ecms.EcmsUtil;
import com.dangdang.ecms.ExperienceInfoEx;
import com.dangdang.ecms.meta.ExperienceInfo;

import com.dangdang.reader.functional.param.model.GetExperienceInfoListParam;
import com.dangdang.reader.functional.param.model.ParseResult;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;

import com.dangdang.reader.functional.param.model.GetExperienceInfoListParam;
import com.dangdang.reader.functional.reponse.GetExperienceInfoListReponse;

public class GetExperienceInfoList extends FunctionalBaseEx{
	
	GetExperienceInfoListParam paramObject;
	ReponseV2<GetExperienceInfoListReponse> reponseResult;
	
	public GetExperienceInfoList(Map<String, String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("getExperienceInfoList");
	}
	
	public ReponseV2<GetExperienceInfoListReponse> getReponseResult() {
		return reponseResult;
	}

	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		super.doWork();
		reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<GetExperienceInfoListReponse>>(){});
	}

	@Override
	protected void parseParam() throws Exception {
		// TODO Auto-generated method stub
		ParseResult parseResult = ParseParamUtil.parseParam(paramMap);
		setLogin(parseResult.getLogin());
		
		paramObject=GetParam(GetExperienceInfoListParam.class);
		
		//解析处理参数
		if(login!= null && paramObject.getRecordTime()!=null && paramObject.getRecordTime().equals("auto")){
			//从记录中随机产生一个时间
			ExperienceInfo info =DbUtil.Random(Config.ECMSDBConfig, "experience_info", "experience_id", "cust_id="+login.getCustId(),ExperienceInfo.class);
			paramObject.setRecordTime(info.getRecordTime().toString());
		}
		paramMap=(Map<String, String>) JSONObject.toJSON(paramObject);
	}
	
	@Override
	protected void dataVerify() throws Exception {
		// TODO Auto-generated method stub
		if(reponseResult.getStatus().getCode()==0){
			List<ExperienceInfo> ExperienceInfos=EcmsUtil.getExperienceInfo(login.getCustId(),paramObject.parseRecordTimeSql(),Integer.parseInt(paramObject.getPageSize()));
			List<ExperienceInfoEx> exceptedDatas= new ArrayList<ExperienceInfoEx>();
			
			for(ExperienceInfo info: ExperienceInfos){
				ExperienceInfoEx infoEx = new ExperienceInfoEx(info);
				exceptedDatas.add(infoEx);
			}
			
			dataVerifyManager.add(new ListVerify(reponseResult.getData().getExperienceInfoList(),exceptedDatas,true));
		}
		super.dataVerify();
	}
}
