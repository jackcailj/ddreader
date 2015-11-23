package com.dangdang.readerV5.channel;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.bookbar.meta.BarProductInfo;
import com.dangdang.db.SqlUtil;
import com.dangdang.db.bookbar.BarProductInfoDb;
import com.dangdang.db.digital.MediaBookListDetailDb;
import com.dangdang.db.digital.MediaDb;
import com.dangdang.ddframework.dataverify.ExpressionVerify;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.digital.meta.Media;
import com.dangdang.digital.meta.MediaBooklistDetail;
import com.dangdang.enumeration.BookStatus;
import com.dangdang.enumeration.BookType;
import com.dangdang.readerV5.reponse.BookListBookUpdateResponse;

/**
 * @author guohaiying
 */
public class BookListBookUpdate extends FixtureBase{
	ReponseV2<BookListBookUpdateResponse> jsonResult;
	
	String bId;
	String type;
	String relateBooks;
	List<MediaBooklistDetail> userList;	
	
	@Override
	protected void parseParam() throws Exception {
		String relateBooksValue = "";
		relateBooks = paramMap.get("relateBooks");
		System.out.println("cccccc " + relateBooks +relateBooks.equals("Ebook"));
		bId = paramMap.get("bId");
		type = paramMap.get("type");
		List<Media> mediaList;		
		List<BarProductInfo> productList;
		try{
			List<String> mediaIdList = new ArrayList<String>();
			List<String> productIdList = new ArrayList<String>();
			userList = MediaBookListDetailDb.getMediaIdList(Integer.valueOf(bId));			
			for(int i=0;i<userList.size(); i++){
				if(userList.get(i).getMediaId()!=null)
					mediaIdList.add(String.valueOf(userList.get(i).getMediaId()));
				if(userList.get(i).getProductId()!=null)
					System.out.println("cccccc " + String.valueOf(userList.get(i).getProductId()));
					productIdList.add(String.valueOf(userList.get(i).getProductId()));
			}
			if(relateBooks.equals("Ebook")){ //添加原创和出版物电子书	
				System.out.println("cccccc inEbook");
				mediaList = MediaDb.getMedias2(BookType.EBOOK, BookStatus.VALID,100);
				for(int i=0; i<mediaList.size(); i++){
					if(!mediaIdList.contains(String.valueOf(mediaList.get(i).getMediaId()))){
						relateBooksValue+="2,"+mediaList.get(i).getMediaId()+","+mediaList.get(i).getSaleId();
						break;
					}
				}
				mediaList = MediaDb.getMedias2(BookType.YUANCHUANG, BookStatus.VALID,100);
				for(int i=0; i<mediaList.size(); i++){
					if(!mediaIdList.contains(String.valueOf(mediaList.get(i).getMediaId()))){
						relateBooksValue+=";2,"+mediaList.get(i).getMediaId()+","+mediaList.get(i).getSaleId();
						break;
					}
				}
			}else if(relateBooks.equals("Userebook")){ //用户书单中已有的电子书（上架状态）
				userList = MediaBookListDetailDb.getMediaIdList(bId, 2, 1);
				if(userList.size()==0)
					userList = MediaBookListDetailDb.getMediaIdList(bId, 1, 1);
				int n=SqlUtil.getRandNum(userList);
				relateBooksValue+="2,"+userList.get(n).getMediaId()+","+userList.get(n).getSaleId();
			}else if(relateBooks.equals("Ebookshelf0")){ //添加下架的电子书
				mediaList = MediaDb.getMedias2(BookType.EBOOK, BookStatus.XIAJIA,20);
				int n=SqlUtil.getRandNum(mediaList);
				relateBooksValue+="2,"+mediaList.get(n).getMediaId()+","+mediaList.get(n).getSaleId();
			}else if(relateBooks.equals("Paperbook")){ //添加纸书
				productList = BarProductInfoDb.getPaperBookList(0);
				System.out.println("cccccc cc" + productList.size());
				for(int i=0;i<productList.size(); i++){
					if(!productIdList.contains(String.valueOf(productList.get(i).getProductId()))){
						relateBooksValue+="3,"+productList.get(i).getProductId();
						break;
					}
					System.out.println("cccccc cc" + relateBooksValue);
				}				
			}else if(relateBooks.equals("Userpaperbook")){ //用户书单中已有的纸书（上架状态）
				userList = MediaBookListDetailDb.getMediaIdList(bId, 3, 1);
				int n=SqlUtil.getRandNum(userList);
				relateBooksValue+="3,"+userList.get(n).getProductId();
			}else if(relateBooks.equals("Paperbookshelf0")){ //添加下架的纸书
				productList = BarProductInfoDb.getPaperBookList(6);
				relateBooksValue+="3,"+productList.get(SqlUtil.getRandNum(productList)).getProductId();
			}
			}catch(Exception e){
				System.out.println("cccccc error");
		}
		
		paramMap.put("relateBooks", relateBooksValue);
	}
	
    @Override
    public void doWork() throws Exception {
        super.doWork();
        jsonResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<BookListBookUpdateResponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){ 
        	String relateBooksValue = paramMap.get("relateBooks");
        	List<String> relateBookList = new ArrayList<String>();        
			List<String> mediaIdList = new ArrayList<String>();
			List<String> productIdList = new ArrayList<String>();
			userList = MediaBookListDetailDb.getMediaIdList(Integer.valueOf(bId));			
			for(int i=0;i<userList.size(); i++){
				if(userList.get(i).getMediaId()!=null)
					mediaIdList.add(String.valueOf(userList.get(i).getMediaId()));
				if(userList.get(i).getProductId()!=null)
					productIdList.add(String.valueOf(userList.get(i).getProductId()));
			}	
			for(String s: relateBooksValue.replace(";",",").split(",")){
				relateBookList.add(s);
			}			
			if(type.equals("1")){ //添加书
				if(relateBooks.equals("Ebook")){
					dataVerifyManager.add(new ExpressionVerify(mediaIdList.contains(relateBookList.get(1))).setVerifyContent("验证书单添加电子书是否成功"));
					dataVerifyManager.add(new ExpressionVerify(mediaIdList.contains(relateBookList.get(4))).setVerifyContent("验证书单添加电子书是否成功"));
				}
				if(relateBooks.equals("Paperbook"))
					dataVerifyManager.add(new ExpressionVerify(productIdList.contains(relateBookList.get(1))).setVerifyContent("验证书单添加纸书是否成功"));
			}else if(type.equals("0")){ //删除书
				if(relateBooks.equals("Userebook"))
					dataVerifyManager.add(new ExpressionVerify(!mediaIdList.contains(relateBookList.get(1))).setVerifyContent("验证书单删除电子书是否成功"));
				if(relateBooks.equals("Userpaperbook"))
					dataVerifyManager.add(new ExpressionVerify(!productIdList.contains(relateBookList.get(1))).setVerifyContent("验证书单删除纸书是否成功"));
			}     
        }
        super.dataVerify();
    }
}
