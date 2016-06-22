package com.dangdang.db.comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dangdang.BaseComment.meta.TagInfo;
import com.dangdang.BaseComment.meta.TagRelation;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.enumeration.TagContentType;
import com.dangdang.enumeration.TagType;

/**
 * @author guohaiying
 */
public class TagInfoDb {
	
	//获取某频道下的标签
	public static List<String> getTagNames(String channelId) throws Exception {
		int _channelId= Integer.valueOf(channelId);
        String selectString ="SELECT tag_name FROM `tag_info` " +
        		"WHERE tag_id IN (SELECT tag_id " +
        				"FROM `tag_relation` " +
        				"WHERE target_source=4000 " +
        				"AND source_id="+_channelId+")";
        List<Map<String, Object>> infos = DbUtil.selectList(Config.BSAECOMMENT,selectString);
        if(infos.size()==0) return null;
        else{
        	List<String> list = new ArrayList<String>();
        	for(int i=0; i<infos.size(); i++){
        		list.add(infos.get(i).get("tag_name").toString());
        	}
        	return list;
        }
    }


	/*
	获取读书计划标签
	 */
	public static List<TagInfo> getPlanTags(TagType type, int num) throws Exception {

		String filter="";
		switch (type){
			case PLANTAG:{
				filter=" is_plan_tag=1 ";
				break;
			}
			case OFFICETAG:{
				filter=" tag_type=1 ";
				break;
			}
			case CUSTOMTAG:{
				filter=" tag_type=2 ";
				break;
			}
		}
		String selectString ="select * from tag_info where "+filter+ " order by sales_num desc,last_modified_time desc "+  (num==-1?"":" limit "+ num);

		List<TagInfo> tagInfos = DbUtil.selectList(Config.BSAECOMMENT,selectString,TagInfo.class);

		return tagInfos;
	}


    /*
        获取标签内容
         */
    public static List<TagRelation> getPlanTags(TagContentType tagContentType) throws Exception {
        String selectString ="SELECT * from tag_relation where tag_id=52 and target_source="+tagContentType.toString()+" and recommend_status=1";

        List<TagRelation> tagInfos = DbUtil.selectList(Config.BSAECOMMENT,selectString,TagRelation.class);

        return tagInfos;
    }


}
