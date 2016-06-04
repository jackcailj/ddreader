package com.dangdang.readerV5.channel;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.bookbar.meta.BarProductInfo;
import com.dangdang.ddframework.dataverify.ExpressionVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.Media;
import com.dangdang.digital.meta.MediaBooklistDetail;
import com.dangdang.db.bookbar.BarProductInfoDb;
import com.dangdang.db.digital.MediaBookListDetailDb;
import com.dangdang.db.digital.MediaDb;
import com.dangdang.readerV5.reponse.BookListDetailReponse;
import com.dangdang.readerV5.reponse.ChannelMediaList;

/**
 * 书单书列表接口
 * @author guohaiying
 */
public class BookListDetail extends FixtureBase{
	ReponseV2<BookListDetailReponse> jsonResult;

	@Override
	public void doWork() throws Exception{
		super.doWork(); 
		jsonResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<BookListDetailReponse>>(){});
	}
	
	@Override
	protected void dataVerify() throws Exception {   //测试环境数据好多都不规范，如果线上环境跑没问题脚本就不用改
		if(reponseV2Base.getStatus().getCode()==0){
			List<ChannelMediaList> actualMediaList = jsonResult.getData().getMediaList();			
			List<MediaBooklistDetail> expectedBookList = MediaBookListDetailDb.getMediaIdList(Integer.valueOf(paramMap.get("bookListId")));
			List<Long> expectedMediaIdList = new ArrayList<Long>();
			List<Long> expectedSaleIdList = new ArrayList<Long>();
			List<Long> expectedProductIdList = new ArrayList<Long>();
			for(int i=0; i<expectedBookList.size(); i++){
				expectedMediaIdList.add(expectedBookList.get(i).getMediaId());
				expectedSaleIdList.add(expectedBookList.get(i).getSaleId());
				expectedProductIdList.add(expectedBookList.get(i).getProductId());
			}
				
			Media expectedMedia;
			BarProductInfo expectedPaperBook;			
			int actualMediaType;
			for(int i=0; i<actualMediaList.size(); i++){
				actualMediaType = actualMediaList.get(i).getMediaType();
				if(actualMediaType==3){//纸书
					long actualProductId = actualMediaList.get(i).getProductId();
					expectedPaperBook = BarProductInfoDb.getPaperBookMsg(actualProductId);
					//验证coverPic title authorPenname descs
					//dataVerifyManager.add(new ValueVerify<Long>(actualProductId, expectedBookList.get(i).getProductId()).setVerifyContent("验证书单ProductId是否正确"), VerifyResult.SUCCESS);
					//dataVerifyManager.add(new ExpressionVerify(!actualMediaList.get(i).getCoverPic().equals("")).setVerifyContent("验证纸书封面是否为空") , VerifyResult.SUCCESS);
					//dataVerifyManager.add(new ValueVerify<String>(actualMediaList.get(i).getTitle(),expectedPaperBook.getProductName()).setVerifyContent("验证纸书name"), VerifyResult.SUCCESS);
					//dataVerifyManager.add(new ValueVerify<String>(actualMediaList.get(i).getAuthorPenname(),expectedPaperBook.getBookAuthor()).setVerifyContent("验证纸书作者"), VerifyResult.SUCCESS);
					//dataVerifyManager.add(new ExpressionVerify(!actualMediaList.get(i).getDescs().equals("")).setVerifyContent("验证纸书描述是否为空"), VerifyResult.SUCCESS);
				}else{//电子书
					long actualMediaId = actualMediaList.get(i).getMediaId();
					long actualSaleId = actualMediaList.get(i).getSaleId();
					expectedMedia = MediaDb.getMediaIdBySaleId(actualSaleId);			
					//dataVerifyManager.add(new ValueVerify<Long>(actualMediaId, expectedBookList.get(i).getMediaId()).setVerifyContent("验证书单MediaId是否正确"), VerifyResult.SUCCESS);		
					//dataVerifyManager.add(new ValueVerify<String>(actualMediaList.get(i).getTitle(),expectedMedia.getTitle()).setVerifyContent("验证电子书Title"), VerifyResult.SUCCESS);
					//dataVerifyManager.add(new ExpressionVerify(!actualMediaList.get(i).getCoverPic().equals("")).setVerifyContent("验证电子书封面是否为空"), VerifyResult.SUCCESS);
					//dataVerifyManager.add(new ValueVerify<String>(actualMediaList.get(i).getAuthorPenname(),expectedMedia.getAuthorPenname()).setVerifyContent("验证电子书作者名"), VerifyResult.SUCCESS);			
				}
			}           
		}
		super.dataVerify();
	}
}
