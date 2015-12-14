package com.dangdang.db.digital;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.digital.meta.Media;
import com.dangdang.enumeration.BookStatus;
import com.dangdang.enumeration.BookType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cailianjie on 2015-7-7.
 */
public class MediaDb {
	
	public static List<Media> getMediaBySaleId(String saleId) throws Exception{
		String selectSQL = "SELECT media_id,cover_pic,title,avg_star_level,comment_number,price,paper_book_price,descs  " +
				"FROM `media` " +
				"WHERE sale_id="+saleId;
		List<Media> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL, Media.class);
		return infos;		
	}

    /*
       获取一个mediaId（有效或者下架）
       参数：
            bookStatus：有效书籍|下架书籍|
    */
    public  static Media getMedia(BookType bookType,BookStatus bookStatus) throws Exception {
       return getMedias(bookType,bookStatus,1).get(0);
    }

    /*
       获取一个mediaId（有效或者下架）
       参数：
            bookStatus：有效书籍|下架书籍|
    */
    public  static List<Media> getMedias(BookType bookType,BookStatus bookStatus,int number) throws Exception {
        String shelfStatus=bookStatus==BookStatus.VALID?"1":"0";
        String selectString="select m.* from media m left join media_sale ms on m.sale_id=ms.sale_id left join media_resfile mr on m.media_id=mr.MEDIA_ID  where ms.shelf_status="+shelfStatus+" and m.shelf_status="+shelfStatus+" and "+bookType.getMediaSqlFilter()+"   limit "+number;
        List<Media> medias = DbUtil.selectList(com.dangdang.config.Config.YCDBConfig, selectString, Media.class);
        return medias;
    }


    /*
       获取mediaId（有效或者下架），对productId进行过滤
       参数：
            bookStatus：有效书籍|下架书籍|
            notInProductIds：要排除的productId
    */
    public  static List<Media> getMedias(BookType bookType,BookStatus bookStatus,int number,List<String> notInProductIds) throws Exception {
        String shelfStatus= bookStatus==BookStatus.VALID?"1":"0";
        String selectString="select m.* from media m left join media_sale ms on m.sale_id=ms.sale_id left join media_resfile mr on m.media_id=mr.MEDIA_ID  where ms.shelf_status="+bookStatus.getShelfStatus()+" and m.shelf_status="+bookStatus.getShelfStatus()+" and "+bookType.getMediaSqlFilter()+" and m.is_full_book="+bookStatus.getIsFull()+(CollectionUtils.isEmpty(notInProductIds)?"": " and m.media_id not in("+StringUtils.join(notInProductIds,",")+")")+" limit "+number;
        List<Media> medias = DbUtil.selectList(com.dangdang.config.Config.YCDBConfig, selectString, Media.class);
        return medias;
    }

    /*
      获取mediaId（有效或者下架），对productId进行过滤
      参数：
           bookStatus：有效书籍|下架书籍|
           notInProductIds：要排除的productId
   */
    public  static List<Media> getMedias(BookType bookType,BookStatus bookStatus,List<String> notInProductIds) throws Exception {

        return getMedias(bookType, bookStatus, Integer.MAX_VALUE, notInProductIds);
    }

    /*
    通过productid获取mediaids
     */
    public  static List<Media> getMedias(List<String> ProductIds) throws Exception {
        if(ProductIds==null || ProductIds.size()==0){
            return new ArrayList<Media>();
        }

        String selectString="SELECT * from media where product_id in ("+StringUtils.join(ProductIds,",")+") GROUP BY product_id";
        List<Media> medias = DbUtil.selectList(com.dangdang.config.Config.YCDBConfig, selectString, Media.class);
        return medias;
    }

    /*
    获取免费书籍Id
    参数：
            bookStatus：有效书籍|下架书籍|
     */
    public  static String getFreeMediaId(BookType bookType,BookStatus bookStatus) throws Exception {
        String shelfStatus=bookStatus==BookStatus.VALID?"1":"0";
        //String selectString="select m.* from media m left join media_sale ms on m.sale_id=ms.sale_id where ms.price=0 and ms.shelf_status="+shelfStatus+" and m.shelf_status="+shelfStatus+" and "+(bookType==BookType.EBOOK?"doc_type='ebook'":"doc_type is null")+" limit 1";
        String selectString="select * from media_activity_sale mas left join media m on m.sale_id=mas.sale_id left join media_resfile mr on m.media_id=mr.MEDIA_ID  where m.shelf_status="+bookStatus+" and  m.promotion_id=3 " +" and "+bookType.getMediaSqlFilter()+"  limit 1";
        Media media = DbUtil.selectOne(com.dangdang.config.Config.YCDBConfig, selectString, Media.class);
        return media.getMediaId().toString();
    }


    /*
      获取一个未购买有效的mediaId
   */
    public  static String getMediaId(String custid,BookType bookType,BookStatus bookStatus) throws Exception {
        String shelfStatus=bookStatus==BookStatus.VALID?"1":"0";
        String selectString="select m.* from media m left join media_sale ms on m.sale_id=ms.sale_id left join media_resfile mr on m.media_id=mr.MEDIA_ID where ms.shelf_status="+shelfStatus+" and m.shelf_status="+shelfStatus+" and "+bookType.getMediaSqlFilter()+"   limit 1";
        Media media = DbUtil.selectOne(com.dangdang.config.Config.YCDBConfig, selectString, Media.class);
        return media.getMediaId().toString();
    }


    public static Media getMedia(String productId) throws Exception {
        String selectString="SELECT * from media where product_id="+productId+" limit 1";
        List<Media> media = DbUtil.selectList(com.dangdang.config.Config.YCDBConfig, selectString, Media.class);
        if(media.size()==0) {
            return null;
        }

        return media.get(0);
    }

    /*
    获取可以借阅的数据
     */
    public static List<Media> getCanBorrowMedia(BookType bookType,BookStatus bookStatus,int number) throws Exception {
        String shelfStatus=bookStatus==BookStatus.VALID?"1":"0";
        //String selectString="select m.* from media m left join media_sale ms on m.sale_id=ms.sale_id where ms.price=0 and ms.shelf_status="+shelfStatus+" and m.shelf_status="+shelfStatus+" and "+(bookType==BookType.EBOOK?"doc_type='ebook'":"doc_type is null")+" limit 1";
        String selectString="select m.* from media m left join media_sale ms on m.sale_id=ms.sale_id left join media_resfile mr on m.media_id=mr.MEDIA_ID  where ms.shelf_status="+shelfStatus+" and m.shelf_status="+shelfStatus+" and "+bookType.getMediaSqlFilter()+"  and borrow_duration is not null and borrow_duration!=0  limit "+number;

        List<Media> media = DbUtil.selectList(com.dangdang.config.Config.YCDBConfig, selectString, Media.class);
        return media;
    }


    /*
       获取可以借阅的数据
        */
    public static List<Media> getCanBorrowMedia(BookType bookType,BookStatus bookStatus,int number,List<String> notIn) throws Exception {
        String shelfStatus=bookStatus==BookStatus.VALID?"1":"0";
        //String selectString="select m.* from media m left join media_sale ms on m.sale_id=ms.sale_id where ms.price=0 and ms.shelf_status="+shelfStatus+" and m.shelf_status="+shelfStatus+" and "+(bookType==BookType.EBOOK?"doc_type='ebook'":"doc_type is null")+" limit 1";
        String selectString="select m.* from media m left join media_sale ms on m.sale_id=ms.sale_id left join media_resfile mr on m.media_id=mr.MEDIA_ID  where ms.shelf_status="+shelfStatus+" and m.shelf_status="+shelfStatus+" and "+bookType.getMediaSqlFilter()+"  and borrow_duration is not null and borrow_duration!=0 "+(CollectionUtils.isEmpty(notIn)?"":" and m.media_id not in("+StringUtils.join(notIn,",")+")")+" limit "+number;

        List<Media> media = DbUtil.selectList(com.dangdang.config.Config.YCDBConfig, selectString, Media.class);
        return media;
    }

	//根据sale_id获取单品页信息
    //BookListDetail.java used
    public static Media getMediaIdBySaleId(long saleId) throws Exception{
    	String selectSQL = "SELECT * FROM media WHERE sale_id="+saleId;
    	List<Media> infos = DbUtil.selectList(Config.YCDBConfig, selectSQL, Media.class);
    	return infos.get(0);   	
    }   
    
  //BookListDetail.java used
    public static List<Media> getMedias2(BookType bookType,BookStatus bookStatus,int number) throws Exception {
        String shelfStatus=bookStatus==BookStatus.VALID?"1":"0";
        String selectString="select m.media_id,m.sale_id,m.product_id from media m left join media_sale ms on m.sale_id=ms.sale_id left join media_resfile mr on m.media_id=mr.MEDIA_ID  where ms.shelf_status="+shelfStatus+" and m.shelf_status="+shelfStatus+" and "+bookType.getMediaSqlFilter()+"   limit "+number;
        List<Media> medias = DbUtil.selectList(com.dangdang.config.Config.YCDBConfig, selectString, Media.class);
        return medias;
    }

    public static void main(String[] args){
    	try {
			MediaDb.getMedia(BookType.EBOOK, BookStatus.VALID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
