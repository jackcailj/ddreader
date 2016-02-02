package com.dangdang.readerV5.reponse;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Created by cailianjie on 2015-12-8.
 */
public class GetNeedBuyChapterCountReponse {
    List<Integer> chapterCountList;

    public List<Integer> getChapterCountList() {
        return chapterCountList;
    }

    public void setChapterCountList(List<Integer> chapterCountList) {
        this.chapterCountList = chapterCountList;
    }
}
