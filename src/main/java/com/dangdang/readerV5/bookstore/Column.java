package com.dangdang.readerV5.bookstore;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.bookbar.meta.BarProductInfo;
import com.dangdang.ddframework.dataverify.ExpressionVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.Media;
import com.dangdang.digital.meta.MediaColumn;
import com.dangdang.digital.meta.MediaSale;
import com.dangdang.db.bookbar.BarProductInfoDb;
import com.dangdang.db.digital.MediaColumnContentDb;
import com.dangdang.db.digital.MediaColumnDb;
import com.dangdang.db.digital.MediaDb;
import com.dangdang.db.digital.MediaSaleDb;
import com.dangdang.readerV5.reponse.ColumnReponse;
import com.dangdang.readerV5.reponse.MediaList;
import com.dangdang.readerV5.reponse.SaleList;

/**
 * 书城栏目接口
 * @author guohaiying
 */
public class Column extends FixtureBase{
	ReponseV2<ColumnReponse> jsonResult;
	
    @Override
    public void doWork() throws Exception {
        super.doWork();
        jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<ColumnReponse>>(){});
    }
	
	//验证结果
    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){	
        	String columnType = paramMap.get("columnType");
			List<String> actualSaleIds = new ArrayList<String>();
			List<String> expectedSaleIds = MediaColumnContentDb.getColumnContent(columnType);
        	//验证media_column_content表
        	//name
			MediaColumn actualMediaColumn = MediaColumnDb.getMediaColumn(columnType);
			dataVerifyManager.add(new ValueVerify<String>(jsonResult.getData().getName().replace("•",""),actualMediaColumn.getName().replace("?","")).setVerifyContent("验证栏目name"));
			
        	List<SaleList> actualSaleList = jsonResult.getData().getSaleList();
//        	for(int i=0; i<actualSaleList.size(); i++){
//        		System.out.println("jsonList: "+actualSaleList.get(i).getSaleId());
//        	}
        	for(int i=0; i<actualSaleList.size(); i++){
        		String actualSaleId = actualSaleList.get(i).getSaleId();
        		List<MediaList> actualMediaList = actualSaleList.get(i).getMediaList();
        		actualSaleIds.add(actualSaleId);

        		//验证meidia表
        		//mediaId
        		//coverPic
        		//title
        		//评星数
        		//评论数
        		//price
        		//原始价格
        		//descs  
        		List<Media> expectedMediaList = MediaDb.getMediaBySaleId(actualSaleId);
        		for(int j=0; j<expectedMediaList.size(); j++){	
        			int mediaType = actualMediaList.get(j).getMediaType();
        			Media expectedMedia = expectedMediaList.get(j);
        			
        			if(mediaType==1){//验证原创
        				//验证media_sale表  
        				//isSupportFullBuy 是否支持全本购买 
        				//type 0：单本，1：多本
        				MediaSale expectedMediaSale = MediaSaleDb.getMediaSale(actualSaleId);
        				dataVerifyManager.add(new ValueVerify<String>(actualSaleList.get(i).getIsSupportFullBuy(),String.valueOf(expectedMediaSale.getIsSupportFullBuy())).setVerifyContent("验证是否支持全本购买 "));
        				dataVerifyManager.add(new ValueVerify<String>(actualSaleList.get(i).getType(),String.valueOf(expectedMediaSale.getType())).setVerifyContent("验证type==0？单本:多本 "));        			
        		
        				dataVerifyManager.add(new ValueVerify<String>(actualMediaList.get(j).getMediaId(),String.valueOf(expectedMedia.getMediaId())).setVerifyContent("验证MediaId"));
        				//dataVerifyManager.add(new ValueVerify<String>(actualMediaList.get(j).getCoverPic(),String.valueOf(expectedMedia.getCoverPic())).setVerifyContent("验证CoverPic"));
        				dataVerifyManager.add(new ValueVerify<String>(actualMediaList.get(j).getTitle(),expectedMedia.getTitle()).setVerifyContent("验证Title"));        		   
        				if(!"null".equals(actualMediaList.get(0).getAvgStarLevel()+""))
        					dataVerifyManager.add(new ValueVerify<Float>(actualMediaList.get(j).getAvgStarLevel(),expectedMedia.getAvgStarLevel()).setVerifyContent("验证AvgStarLevel"));
        				if(!"null".equals(actualMediaList.get(j).getCommentNumber()+""))
        					dataVerifyManager.add(new ValueVerify<Integer>(actualMediaList.get(j).getCommentNumber(),expectedMedia.getCommentNumber()).setVerifyContent("验证CommentNumber"));
        				dataVerifyManager.add(new ValueVerify<String>(actualMediaList.get(j).getPrice(),String.valueOf(expectedMedia.getPrice())).setVerifyContent("验证Price"));        				
        				//dataVerifyManager.add(new ExpressionVerify(expectedMedia.getDescs().contains(actualMediaList.get(j).getDescs())).setVerifyContent("验证Descs"));
        				dataVerifyManager.add(new ValueVerify<Integer>(actualMediaList.get(j).getShelfStatus(),1).setVerifyContent("验证shelfStatus"));
        				
        				dataVerifyManager.add(new ExpressionVerify(expectedSaleIds.containsAll(actualSaleIds)).setVerifyContent("验证电子书saleIds"));
        			}       		
        			if(mediaType==2){//验证出版物
        				//验证media_sale表  
        				//isSupportFullBuy 是否支持全本购买 
        				//type 0：单本，1：多本
        				MediaSale expectedMediaSale = MediaSaleDb.getMediaSale(actualSaleId);
        				dataVerifyManager.add(new ValueVerify<String>(actualSaleList.get(i).getIsSupportFullBuy(),String.valueOf(expectedMediaSale.getIsSupportFullBuy())).setVerifyContent("验证是否支持全本购买 "));
        				dataVerifyManager.add(new ValueVerify<String>(actualSaleList.get(i).getType(),String.valueOf(expectedMediaSale.getType())).setVerifyContent("验证type==0？单本:多本 "));        			
        		
        				dataVerifyManager.add(new ValueVerify<String>(actualMediaList.get(j).getMediaId(),String.valueOf(expectedMedia.getMediaId())).setVerifyContent("验证MediaId"));
        				//dataVerifyManager.add(new ValueVerify<String>(actualMediaList.get(j).getCoverPic(),String.valueOf(expectedMedia.getCoverPic())).setVerifyContent("验证CoverPic"));
        				dataVerifyManager.add(new ValueVerify<String>(actualMediaList.get(j).getTitle(),String.valueOf(expectedMedia.getTitle())).setVerifyContent("验证Title"));        		   
        				if(!"null".equals(actualMediaList.get(0).getAvgStarLevel()+""))
        					dataVerifyManager.add(new ValueVerify<Float>(actualMediaList.get(j).getAvgStarLevel(),expectedMedia.getAvgStarLevel()).setVerifyContent("验证AvgStarLevel"));
        				if(!"null".equals(actualMediaList.get(j).getCommentNumber()+""))
        					dataVerifyManager.add(new ValueVerify<Integer>(actualMediaList.get(j).getCommentNumber(),expectedMedia.getCommentNumber()).setVerifyContent("验证CommentNumber"));
        				dataVerifyManager.add(new ValueVerify<String>(actualMediaList.get(j).getPrice(),String.valueOf(expectedMedia.getPrice())).setVerifyContent("验证Price"));
        				dataVerifyManager.add(new ValueVerify<Integer>((int)(actualMediaList.get(j).getOriginalPrice()*100),expectedMedia.getPaperBookPrice()).setVerifyContent("验证OriginalPrice"));
        				//dataVerifyManager.add(new ExpressionVerify(expectedMedia.getDescs().contains(actualMediaList.get(j).getDescs())).setVerifyContent("验证Descs"));
        				dataVerifyManager.add(new ValueVerify<Integer>(actualMediaList.get(j).getShelfStatus(),1).setVerifyContent("验证shelfStatus"));
        			       				
        				dataVerifyManager.add(new ExpressionVerify(expectedSaleIds.containsAll(actualSaleIds)).setVerifyContent("验证电子书saleIds"));
        			}
        			if(mediaType==3){//验证纸书
        				List<String> actualProductIds = new ArrayList<String>();
        				String actualProductId = actualMediaList.get(j).getProductId();
        				actualProductIds.add(actualProductId);
        				BarProductInfo expectedProductInfo = BarProductInfoDb.getPaperBookMsg(Long.valueOf(actualProductId));
        				dataVerifyManager.add(new ValueVerify<String>(actualMediaList.get(j).getTitle(),String.valueOf(expectedProductInfo.getProductName())).setVerifyContent("验证纸书name"));
        				dataVerifyManager.add(new ValueVerify<String>(actualMediaList.get(j).getPrice(),String.valueOf(expectedProductInfo.getBookPrice())).setVerifyContent("验证纸书Price"));
        				dataVerifyManager.add(new ExpressionVerify(expectedSaleIds.containsAll(actualProductIds)).setVerifyContent("验证纸书productIds"));
        			}
        		}
        	}
        }
        super.dataVerify();    	
	}
}
