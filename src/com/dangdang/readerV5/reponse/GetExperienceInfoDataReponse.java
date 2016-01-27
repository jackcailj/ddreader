package com.dangdang.readerV5.reponse;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Map;

/**
 * Created by cailianjie on 2016-1-19.
 */
public class GetExperienceInfoDataReponse {
    ExperienceInfoData  experienceData;

    public ExperienceInfoData getExperienceData() {
        return experienceData;
    }

    public void setExperienceData(ExperienceInfoData experienceData) {
        this.experienceData = experienceData;
    }
}
