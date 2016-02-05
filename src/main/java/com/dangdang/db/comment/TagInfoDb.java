package com.dangdang.db.comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dangdang.BaseComment.meta.TagRelation;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.enumeration.TagContentType;

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



}
