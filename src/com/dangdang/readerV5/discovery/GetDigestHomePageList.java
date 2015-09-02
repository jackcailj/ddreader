package com.dangdang.readerV5.discovery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.BaseComment.meta.CommentTargetCount;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.MediaDigest;
import com.dangdang.readerV5.reponse.DigestList;
import com.dangdang.readerV5.reponse.DigestListData;

/**
 * 精品首页接口-v2    action：getDigestHomePageList
 * @author wuhaiyan 
 */
public class GetDigestHomePageList extends FixtureBase {
	
	public ReponseV2<DigestListData> getResult(){
		return JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<DigestListData>>(){});
	}

	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public void setParameters(Map<String, String> params) throws Exception {
		super.setParameters(params);				
		if(paramMap.get("sortPage")!=null){
			String time = paramMap.get("sortPage");
			if(time.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}")){
			    time = time.replace("T", " ");
		        paramMap.put("sortPage",Long.toString(df.parse(time).getTime()));
	        }	
		}
		String value = paramMap.get("acter");
		paramMap.remove("acter");
		paramMap.put("act", value);
	}
	
	@Override
	public void dataVerify(String expectedCode) throws Exception {
		ReponseV2<DigestListData> reponseResult = getResult();
		if(reponseResult.getStatus().getCode() == 0){	
			List<DigestList> digestList = reponseResult.getData().getDigestList();
			List<String> list1 = new ArrayList<String>();
			List<String> list2 = new ArrayList<String>();
			String sql = "SELECT id,card_title,card_remark,review_cnt,top_cnt FROM `media_digest` where is_show=1 and is_del=0 and "
					+ "type="+paramMap.get("type")+" and day_or_night="+(paramMap.get("dayOrNight").equals("day")?"0":"1")+
					" and sort_page"+(paramMap.get("act").equals("new")?">":"<")+"'"+paramMap.get("sortPage")
					+"' and show_start_date<'"+df.format(new Date())
					+"' ORDER BY sort_page DESC limit "+(paramMap.get("pageSize").isEmpty()?"10":paramMap.get("pageSize"));
		    List<Map<String,Object>> digest = DbUtil.selectList(Config.YCDBConfig, sql);		
			dataVerifyManager.add(new ValueVerify<Integer>(digest.size(), 
					                         reponseResult.getData().getDigestList().size(), false));
			for(int i=0; i<digest.size(); i++){
				list1.add(digest.get(i).get("card_title").toString());
				list1.add(digest.get(i).get("card_remark").toString());
				list1.add(digest.get(i).get("id").toString());
				try{
					CommentTargetCount count = DbUtil.selectOne(Config.BSAECOMMENT, 
							"SELECT * FROM `comment_target_count` where target_id="+digest.get(i).get("id").toString()
							+ " and target_source="+(paramMap.get("type").equals("1")?"2000":"3000"),
							CommentTargetCount.class);
					list1.add(Integer.toString(count.getCommentCount()));
					list1.add(Integer.toString(count.getPraiseCount()));
				}
				catch(Exception e){
					list1.add("0");
					list1.add("0");					
				}
				
				list2.add(digestList.get(i).getCardTitle());
				list2.add(digestList.get(i).getCardRemark());
				list2.add(digestList.get(i).getId().toString());
				list2.add(Integer.toString(digestList.get(i).getReplyCnt()));
				list2.add(Integer.toString(digestList.get(i).getTopCnt()));
				dataVerifyManager.add(new ListVerify(list1,list2, false));
				logger.info("list1 is "+list1.toString());
				logger.info("list2 is "+list2.toString());
			}
			super.dataVerify();			
		}	
		else{
			dataVerifyResult = false;
			//TODO will add verify
		}
		verifyResult(expectedCode);
	}

}
