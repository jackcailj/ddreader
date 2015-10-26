package com.dangdang.reader.functional.reponse;

import java.util.List;

import com.dangdang.db.ecms.ExperienceInfoEx;

public class GetExperienceInfoListReponse {
	List<ExperienceInfoEx> experienceInfoList;

	public List<ExperienceInfoEx> getExperienceInfoList() {
		return experienceInfoList;
	}

	public void setExperienceInfoList(List<ExperienceInfoEx> experienceInfoList) {
		this.experienceInfoList = experienceInfoList;
	}
	
}
