package com.dangdang.ucenter;

import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.ecms.meta.Thirdparty;
import com.dangdang.ecms.meta.ThirdpartyCustId;
import com.dangdang.readerV5.personal_center.bookfriend.BookFriendRelation;
import com.dangdang.ucenter.meta.BookFirend;
import com.dangdang.ucenter.meta.LoginRecord;

import java.util.List;
import java.util.Map;

/**
 * Created by cailianjie on 2015-6-18.
 */
public class UserInfoSql {

    public  static  String getPubIdByName(String userName) throws Exception {
        String selectSql="select t.ID from thirdparty_cust_id t\n" +
                "left join user_device u on t.CUST_ID=u.CUST_ID \n" +
                "where u.USERNAME='"+userName+"' limit 1";
        Map<String,Object> result = DbUtil.selectOne(Config.ECMSDBConfig,selectSql);
        return ""+result.get("ID");
    }



    public  static LoginRecord getUserInfoByName(String userName) throws Exception {
        String selectSql="SELECT lr.* from login_record lr\n" +
                "left join user_device ud on lr.cust_id=ud.CUST_ID\n" +
                "where ud.USERNAME='"+userName+"' limit 1";
        LoginRecord result = DbUtil.selectOne(Config.ECMSDBConfig,selectSql,LoginRecord.class);
        return result;
    }

    public  static String getCustIdByPubId(String pubId) throws Exception {
        String selectSql="SELECT CUST_ID from thirdparty_cust_id where id="+pubId;
        List<ThirdpartyCustId> result = DbUtil.selectList(Config.UCENTERDBConfig, selectSql, ThirdpartyCustId.class);
        if(result.size()==0){
            return null;
        }
        return ""+result.get(0).getCustId();
    }

    public  static String getCustIdByName(String userName) throws Exception {
        String selectSql="SELECT CUST_ID from user_device where username='"+userName+"' limit 1";
        Map<String,Object> result = DbUtil.selectOne(Config.UCENTERDBConfig, selectSql);
        return ""+result.get("CUST_ID");
    }

    public  static LoginRecord getUserInfoByCustId(String custid) throws Exception {
        String selectSql="select lr_id,cust_id,cust_nickname,cust_img,device_no,device_type,login_token,create_date,introduct,login_platform,login_client,gender*1 as gender,bind_phone_num, display_id from login_record where cust_id="+custid;
        LoginRecord result = DbUtil.selectOne(Config.UCENTERDBConfig,selectSql,LoginRecord.class);
        return result;
    }


    /*
    获取我的书友列表
     */
    public static List<BookFirend> getMyBookFriends(String custId) throws Exception {
        String selectSql="select * from book_firend where  (active_user_id="+custId+" or passive_user_id="+custId+") order by create_date desc limit 10";
        List<BookFirend> bookFirends = DbUtil.selectList(Config.UCENTERDBConfig, selectSql, BookFirend.class);
        return bookFirends;
    }

    /*
    获取两个custId是否存在书友关系，没有关系返回null，有关系返回数据

     */
    public static List<BookFirend> getBookFriendByRelationCustId(String loginCustId,String hisCustId) throws Exception {
        String selectSql="select * from book_firend where  active_user_id="+loginCustId+" and passive_user_id="+hisCustId;
        List<BookFirend> bookFirends = DbUtil.selectList(Config.UCENTERDBConfig,selectSql,BookFirend.class);
        if(bookFirends.size()==0){
            return null;
        }
        return bookFirends;
    }


    public static BookFriendRelation getRelation(String custId,String friendCustId) throws Exception {

        if(custId==null || friendCustId==null){
            return null;
        }

        BookFriendRelation bookFriendRelation =null;
        //String hisCustId=UserInfoSql.getCustIdByPubId(friendCustId);
        List<BookFirend> bookFirends= UserInfoSql.getBookFriendByRelationCustId(custId, friendCustId);
        if(bookFirends!=null){
            //原来存在关系，主动关注
            bookFriendRelation=  BookFriendRelation.valueOf(bookFirends.get(0).getRelationShip());
            bookFriendRelation.setIsActive(true);
        }
        else{
            bookFirends= UserInfoSql.getBookFriendByRelationCustId(friendCustId,custId);
            if(bookFirends==null){
                //两个人没有书友关系，新增
                bookFriendRelation= BookFriendRelation.NO_RELATION;

            }
            else{
                //原来存在关系，被动关注
                bookFriendRelation= BookFriendRelation.valueOf(bookFirends.get(0).getRelationShip());
            }

            bookFriendRelation.setIsActive(false);
        }

        return bookFriendRelation;
    }

}
