package com.dangdang.readerV5.reponse;

import com.dangdang.BaseComment.meta.CloudExperienceInfo;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.readerV5.personal_center.cloud_sync_read.CloudExperienceInfoEx;

import java.util.List;

/**
 * Created by cailianjie on 2015-9-24.
 */
public class GetExperienceInfoListReponse{

    List<CloudExperienceInfoEx> experienceInfoList;
    Integer incrementIsHasNextPage;
    Integer isHasNextPage;
    Long recordTime;

    public List<CloudExperienceInfoEx> getExperienceInfoList() {
        return experienceInfoList;
    }

    public void setExperienceInfoList(List<CloudExperienceInfoEx> experienceInfoList) {
        this.experienceInfoList = experienceInfoList;
    }

    public Integer getIncrementIsHasNextPage() {
        return incrementIsHasNextPage;
    }

    public void setIncrementIsHasNextPage(Integer incrementIsHasNextPage) {
        this.incrementIsHasNextPage = incrementIsHasNextPage;
    }

    public Integer getIsHasNextPage() {
        return isHasNextPage;
    }

    public void setIsHasNextPage(Integer isHasNextPage) {
        this.isHasNextPage = isHasNextPage;
    }

    public Long getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Long recordTime) {
        this.recordTime = recordTime;
    }
}
