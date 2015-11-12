package com.dangdang.db.comment;

import java.util.List;

import com.dangdang.BaseComment.meta.CommentTargetCount;
import com.dangdang.config.Config;
import com.dangdang.db.SqlUtil;
import com.dangdang.db.digital.MediaDigestDb;
import com.dangdang.ddframework.dbutil.DbUtil;

/**
 * 
 * @author guohaiying
 *
 */
public class CommentTargetCountDb {
	
	//获取阅读书 target：7000攻略  
	public static List<CommentTargetCount> get(String target, String num) throws Exception{
		int _target=Integer.valueOf(target);
		int _num=Integer.valueOf(num);
		String selectSQL = "SELECT * " +
				" FROM `comment_target_count` " +
				" WHERE target_source="+_target +
				" AND target_id IN"+SqlUtil.getListToString(MediaDigestDb.getDigestId("5"))+
				" ORDER BY browse_count DESC LIMIT "+num;
		List<CommentTargetCount> infos =  DbUtil.selectList(Config.BSAECOMMENT,selectSQL, CommentTargetCount.class);
		return infos;
	}

	public static void main(String[] args){
		try {
			List<CommentTargetCount> list = CommentTargetCountDb.get("7000","30");
			System.out.println(list.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
