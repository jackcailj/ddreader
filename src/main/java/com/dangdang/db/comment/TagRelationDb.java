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

    /*
    获取某种tag下推荐内容
    参数：
        tagIds：标签id，多个用，分隔
        tagContentType：推荐内容，1000,书吧;4000:频道;5000:文章;6000:书
     */
    public static List<TagRelation> getTagRelation(String tagIds, TagContentType tagContentType) throws Exception {
        String selectSql = "SELECT * from tag_relation where tag_id in ("+tagIds+") and recommend_status=1 and target_source="+tagContentType.toString();

        List<TagRelation> tagRelations = DbUtil.selectList(Config.BSAECOMMENT,selectSql,TagRelation.class);

        return tagRelations;
    }


    /*
    获取某种tag下推荐内容
    参数：
        tagIds：标签id，多个用，分隔
        tagContentType：推荐内容，1000,书吧;4000:频道;5000:文章;6000:书
     */
    public static List<TagRelation> getTagRelation(TagContentType tagContentType,int num) throws Exception {
        String selectSql = "SELECT * from tag_relation where  recommend_status=1 and target_source="+tagContentType.toString() +(num==-1?"":" limit "+num);

        List<TagRelation> tagRelations = DbUtil.selectList(Config.BSAECOMMENT,selectSql,TagRelation.class);

        return tagRelations;
    }
    
}
