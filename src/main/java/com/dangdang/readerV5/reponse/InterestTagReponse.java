package com.dangdang.readerV5.reponse;

import com.dangdang.readerV5.read_plan.PlanTagInfo;

import java.util.List;

/**
 * Created by cailianjie on 2016-4-15.
 */
public class InterestTagReponse {

    List<PlanTagInfo> tags;

    public List<PlanTagInfo> getTags() {
        return tags;
    }

    public void setTags(List<PlanTagInfo> tags) {
        this.tags = tags;
    }
}
