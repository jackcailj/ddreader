package com.dangdang.param.parse;

import com.dangdang.BaseComment.meta.TagRelation;
import com.dangdang.db.comment.TagRelationDb;
import com.dangdang.ddframework.fitnesse.ParamParse;
import com.dangdang.ddframework.util.Util;
import com.dangdang.enumeration.TagContentType;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by cailianjie on 2016-5-18.
 * 获取标签Id，#GetTagIds#TAGTYPE,数量(-1为所有)
 */
public class GetTagIdsParse implements IParamParse{
    @Override
    public Object parse(Map<String, String> param) throws Exception {
        return null;
    }

    @Override
    public void parse(Map<String, String> paramMap, String key, String param) throws Exception {

        String[] params = ParamParse.parseParam(param);

        TagContentType tagType = TagContentType.valueOf(params[0]);
        Integer num = Integer.parseInt(params[1]);

        List<TagRelation> tagInfos =  TagRelationDb.getTagRelation(tagType,num);

        List<String> tagIds = Util.getFields(tagInfos,"tagId");
        paramMap.put(key, StringUtils.join(tagIds,","));
    }
}
