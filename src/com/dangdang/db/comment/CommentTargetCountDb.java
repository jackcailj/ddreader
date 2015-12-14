package com.dangdang.db.comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dangdang.BaseComment.meta.CommentTargetCount;
import com.dangdang.config.Config;
import com.dangdang.db.SqlUtil;
import com.dangdang.db.digital.MediaDigestDb;
import com.dangdang.ddframework.dbutil.DbUtil;

/**
 * @author guohaiying
 */
public class CommentTargetCountDb {
	
	//获取阅读书 target：4000频道, 7000攻略  
	public static List<CommentTargetCount> get(String target, String num) throws Exception{
		String selectSQL = "SELECT * FROM `comment_target_count` " +
				" WHERE target_source= "+ target +
				" AND target_id IN "+SqlUtil.getListToString(MediaDigestDb.getDigest(5))+
						" ORDER BY praise_count DESC " +
						" LIMIT "+num;
		List<CommentTargetCount> infos =  DbUtil.selectList(Config.BSAECOMMENT,selectSQL, CommentTargetCount.class);
		return infos;
	}
	
	//获取频道  频道文章推荐接口
	//type文章类型  3:频道; 5:攻略
	public static List<CommentTargetCount> get2(String id, int type) throws Exception{
		String selectSQL = "SELECT a.* " +
				" FROM `comment_target_count` a,tag_relation b " +
				" WHERE a.target_id=b.source_id " +
				" AND a.target_source=4000 " +
				" AND a.target_id IN " +SqlUtil.getListToString(MediaDigestDb.getDigest(type))+
				" AND b.tag_id IN " +SqlUtil.getListToString(TagRelationDb.getTargets(5000,id))+
				" AND b.recommend_status=0"+
				" ORDER BY praise_count DESC ";// +
				//" LIMIT 30";
		List<CommentTargetCount> infos =  DbUtil.selectList(Config.BSAECOMMENT,selectSQL, CommentTargetCount.class);
		return infos;		
	}
	
	//相关频道推荐
	//Recommendarticle.java used
	public static List<String> getRelatedChannel(String channelId) throws Exception{
		String selectSQL = "SELECT DISTINCT(source_id) FROM `tag_relation` " +
				" WHERE target_source=4000 AND tag_id IN(SELECT tag_id " +
														" FROM tag_relation " +
													    " WHERE source_id="+channelId+ 
													    " AND target_source=4000)";
		List<Map<String, Object>> infos = DbUtil.selectList(Config.BSAECOMMENT, selectSQL);
		List<String> list = new ArrayList<String>();
		for(int i=0; i<infos.size(); i++){
			list.add(infos.get(i).get("source_id").toString());
		}
		return list;
	}
	
	//获取频道和攻略文章的点赞数和评论数
	public static CommentTargetCount get(String targetId) throws Exception{
		String selectSQL = "SELECT * FROM `comment_target_count` WHERE target_source IN (4000,7000) AND target_id="+targetId;
		List<CommentTargetCount> infos =  DbUtil.selectList(Config.BSAECOMMENT,selectSQL, CommentTargetCount.class);
		if(infos.size()==0) return null;
		return infos.get(0);
	}
	
	//获取有标签的频道  4000:频道;5000:文章;6000:书 7000:攻略
	public static List<String> getHaveTagChannels(String targetSource) throws Exception{
		String selectSQL = "SELECT DISTINCT(source_id) " +
				"FROM tag_relation " +
				"WHERE target_source="+targetSource;
		List<Map<String, Object>> infos = DbUtil.selectList(Config.BSAECOMMENT, selectSQL);
		List<String> list = new ArrayList<String>();
		for(int i=0;i<infos.size();i++){
			list.add(infos.get(i).get("source_id").toString());
		}
		return list;
	}
	
	//根据targetId和targetSource获取记录
	public static CommentTargetCount getCommentTargetCount(String targetId, String targetSource) throws Exception{
		String selectSQL = "SELECT * FROM `comment_target_count` WHERE target_id="+targetId+" AND target_source="+targetSource;
		List<CommentTargetCount> infos = DbUtil.selectList(Config.BSAECOMMENT, selectSQL, CommentTargetCount.class);
		CommentTargetCount count = null;
		try{
			count = infos.get(0);
		}catch(Exception e){
			System.out.println("targetId："+targetId+" targetSource："+targetSource+"记录为空");
		}
		return count;
	
	}
	

	public static void main(String[] args){
		try {
			List<String> list = CommentTargetCountDb.getRelatedChannel("84");
			System.out.println(list.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
