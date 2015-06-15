package com.dangdang.reader.functional.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FunctionalBaseEx;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.reponse.ReponseV2Base;
import com.dangdang.ecms.meta.BookNote;
import com.dangdang.reader.functional.param.model.ParseResult;
import com.dangdang.reader.functional.param.parse.ParseParamUtil;
import com.dangdang.reader.functional.personcenter.GetBuyBookList;
import com.dangdang.reader.functional.reponse.BuyBookListResponse;

/*
 * author:cailj
 * desc:同步阅读信息，笔记\书签
 * 
 * 
 * 请求参数：
 * &markInfo=&noteInfo=[{"characterEndIndex":693,"custId":"29563817","status":"1","chaptersIndex":2,"noteInfo":"很好","characterStartIndex":644,"modifyTime":0,"clientOperateTime":1430889924,"callOutInfo":"我们提供知识，以应对变化的世界”，我们非常欣慰地看到不仅是众多商业领军人物正在阅读我们的出版物，更重","productId":"1900339498"}]&versionTime=1430884675&token=f51280c2915da444e27d22087914e11e&returnType=json&deviceType=Android&channelId=30085&clientVersionNo=4.8.1&serverVersionNo=1.2.1&activityId=0&permanentId=20150430035722290671726176105455764&deviceSerialNo=863151026834264&macAddr=38%3Abc%3A1a%3Aa0%3Ab4%3A74&resolution=1080*1800&clientOs=4.4.4&type=1&model=M355&producer=Meizu
 * &markInfo=[{"characterIndex":766,"custId":"29563817","status":"1","chaptersIndex":2,"markInfo":"信，如此庞大的智力资源才是中国企业，乃至中国经济最强劲的引擎。编者2013年5月我把本书献给那些热爱商业生活、渴望把事情做好的人，献给那些每天一醒来就期盼在事业和生活中取得成功的人。","modifyTime":0,"clientOperateTime":1430890067,"productId":"1900339498"}]&noteInfo=&versionTime=1430889935&token=f51280c2915da444e27d22087914e11e&returnType=json&deviceType=Android&channelId=30085&clientVersionNo=4.8.1&serverVersionNo=1.2.1&activityId=0&permanentId=20150430035722290671726176105455764&deviceSerialNo=863151026834264&macAddr=38%3Abc%3A1a%3Aa0%3Ab4%3A74&resolution=1080*1800&clientOs=4.4.4&type=1&model=M355&producer=Meizu
 */
public class UpdateBookCloudSyncReadInfo extends FunctionalBaseEx{
	
	ReponseV2Base reponseResult;
	
	public ReponseV2Base getReponseResult() {
		return reponseResult;
	}


	public UpdateBookCloudSyncReadInfo(Map<String,String> param) {
		// TODO Auto-generated constructor stub
		super(param);
		addAction("updateBookCloudSyncReadInfo");
	}
	
	
	@Override
	public void doWork() throws Exception {
		// TODO Auto-generated method stub
		super.doWork();
		reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2Base>(){});
	}
	
	@Override
	protected void parseParam() throws Exception {
		// TODO Auto-generated method stub
		ParseResult parseResult = ParseParamUtil.parseParam(paramMap);
		setLogin(parseResult.getLogin());
		
		//解析noteInfo
		if(paramMap.get("noteInfo")!=null){
			String noteInfo=paramMap.get("noteInfo").toString();
			GetBuyBookList getBuyBookList = new GetBuyBookList(login);
			getBuyBookList.doWorkAndVerify();
			ReponseV2<BuyBookListResponse> result= getBuyBookList.getReponseResult();
			
			List<BookNote> notes=new ArrayList<BookNote>();
			BookNote bookNote=new BookNote();
			bookNote.setProductId(Long.parseLong(result.getData().getEbookList().get(0).getProductId()));
			bookNote.setCallOutInfo("测试文字");
			bookNote.setNoteInfo("写的很好，值得思考");
			bookNote.setChaptersIndex(2);
			bookNote.setCharacterStartIndex(new Long(100));
			bookNote.setCharacterEndIndex(new Long(200));
			bookNote.setStatus((short)1);
			bookNote.setCustId(Long.parseLong(login.getCustId()));
			bookNote.setModifyTime((long)0);
			bookNote.setClientOperateTime(new Date().getTime());
			notes.add(bookNote);
			paramMap.put("noteInfo", JSONObject.toJSONString(notes));
			paramMap.put("markInfo", "");
		}
	}
	
	
}
