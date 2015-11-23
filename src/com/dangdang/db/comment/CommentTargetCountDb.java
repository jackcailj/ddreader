package com.dangdang.db.comment;

import java.util.List;
import com.dangdang.BaseComment.meta.CommentTargetCount;
import com.dangdang.config.Config;
import com.dangdang.db.SqlUtil;
import com.dangdang.db.digital.MediaDigestDb;
import com.dangdang.ddframework.dbutil.DbUtil;

/**
 * @author guohaiying
 */
public class CommentTargetCountDb {
	
	//获取阅读书 target：7000攻略  
	public static List<CommentTargetCount> get(String target, String num) throws Exception{
		int _target=Integer.valueOf(target);
		int _num=Integer.valueOf(num);
		String selectSQL = "SELECT * FROM `comment_target_count` " +
				" WHERE target_source=7000 " +
				" AND target_id IN "+SqlUtil.getListToString(MediaDigestDb.getDigest(5))+
						" ORDER BY praise_count DESC " +
						" LIMIT 30";
		List<CommentTargetCount> infos =  DbUtil.selectList(Config.BSAECOMMENT,selectSQL, CommentTargetCount.class);
		return infos;
	}
	
	//获取频道
	public static List<CommentTargetCount> get2(String id) throws Exception{
		String selectSQL = "SELECT a.* " +
				"FROM `comment_target_count` a,tag_relation b " +
				"WHERE a.target_id=b.source_id " +
				"AND a.target_source=4000 " +
				"AND a.target_id IN " +SqlUtil.getListToString(MediaDigestDb.getDigest(3))+
				"AND b.tag_id IN " +SqlUtil.getListToString(TagRelationDb.getTargets(5000,id))+
				" ORDER BY praise_count DESC " +
				" LIMIT 30";
		List<CommentTargetCount> infos =  DbUtil.selectList(Config.BSAECOMMENT,selectSQL, CommentTargetCount.class);
		return infos;		
	}
	
	//获取频道和攻略文章的点赞数和评论数
	public static CommentTargetCount get(String targetId) throws Exception{
		String selectSQL = "SELECT * FROM `comment_target_count` WHERE target_source IN (4000,7000) AND target_id="+targetId;
		List<CommentTargetCount> infos =  DbUtil.selectList(Config.BSAECOMMENT,selectSQL, CommentTargetCount.class);
		if(infos.size()==0) return null;
		return infos.get(0);
	}
	

	public static void main(String[] args){
		try {
			List<CommentTargetCount> list = CommentTargetCountDb.get2("14143");
			System.out.println(list.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
