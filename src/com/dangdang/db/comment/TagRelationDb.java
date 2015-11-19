package com.dangdang.db.comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

public class TagRelationDb {
	
	//获取标签  
	//来源类型（1000,书吧;4000:频道;5000:文章;6000:书）
    public static List<String> getTargets(int targetSource,String sourceId) throws Exception {
    	int _sourceId = Integer.valueOf(sourceId);
        String selectString ="SELECT tag_id FROM `tag_relation` " +
        		"WHERE target_source="+targetSource+" " +
        				"AND source_id="+_sourceId;
        List<Map<String, Object>> infos = DbUtil.selectList(Config.BSAECOMMENT,selectString);
        List<String> list = new ArrayList<String>();
        for(int i=0; i<infos.size(); i++){
        	list.add(infos.get(i).get("tag_id").toString());
        }
        return list;
    }
}
