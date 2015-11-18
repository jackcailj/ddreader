package com.dangdang.param.parse;

import com.dangdang.common.functional.login.ILogin;
import com.dangdang.db.digital.ChannelDb;
import com.dangdang.db.digital.MediaDigestDb;
import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.enumeration.BookStatus;
import com.dangdang.enumeration.BookType;
import com.dangdang.db.digital.MediaDb;
import com.dangdang.digital.meta.Media;
import com.dangdang.digital.meta.MediaDigest;
import com.dangdang.enumeration.VarKey;
import com.dangdang.readerV5.personal_center.DDReaderStoreUpList;
import com.dangdang.enumeration.StoreUpType;
import com.dangdang.readerV5.reponse.DDReaderStoreUpArticle;
import com.dangdang.readerV5.reponse.DDReaderStoreUpMedia;
import com.dangdang.readerV5.reponse.DDReaderStoreUpPost;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cailianjie on 2015-7-14.
 */
public class GetUnStoreIdParse implements IParamParse{


    @Override
    public Object parse(Map<String, String> param) throws Exception {
        return null;
    }


    /*
    获取产品productid
    参数：
        param：用，分割多个参数，格式BookType,ProductIdsEnum,数量
     */
    @Override
    public void parse(Map<String, String> paramMap, String key, String param) throws Exception {
        if(StringUtils.isNotBlank(param)){
            String[] params = ParamParse.parseParam(param);
            StoreUpType storeUpType = StoreUpType.valueOf(paramMap.get("type").toUpperCase());

            //用来处理混合情况，比如有效和无效的productId一起
            //String[] numberSplit =params.split("and");


            //获取购物车中的商品
            //GetMyBoughtList myBoughtList = new GetMyBoughtList((ILogin) VariableStore.get(VarKey.LOGIN));
            //myBoughtList.doWork();
            //List<MediaAuthority> mediaAuthorities = AuthorityDb.getMediaAuthority(((ILogin) VariableStore.get(VarKey.LOGIN)).getCustId());
            DDReaderStoreUpList storeUpList = new DDReaderStoreUpList((ILogin) VariableStore.get(VarKey.LOGIN),storeUpType );
            storeUpList.doWork();

            List<String> productIds = new ArrayList<String>();


            List mediaIds = new ArrayList();
            if(storeUpType==StoreUpType.MEDIA) {

                for (DDReaderStoreUpMedia products : storeUpList.getReponseResultMedia().getData().getStoreUpList()) {
                    productIds.add("" + products.getProductId());
                }


                BookType bookType = BookType.valueOf(params[0]);
                BookStatus bookStatus= BookStatus.valueOf(params[1]);
                //用来处理混合情况，比如有效和无效的productId一起
                String[] numberSplit =ParamParse.parseParam(params[2], ParamParse.AND);
                Integer number=Integer.parseInt(numberSplit[0].trim());

                //查找不在购物车中的prouctid，用来往购物车中添加
                List<Media> medias=MediaDb.getMedias(bookType, bookStatus, number,productIds);

                for(Media media : medias){
                    //Map<String,String> mediaMap=new HashMap<String, String>();
                    //mediaMap.put("productId",media.getMediaId().toString());
                    //mediaMap.put("saleId", media.getSaleId().toString());
                    mediaIds.add(media.getSaleId());
                }


                VariableStore.add(VarKey.MEDIAS,medias);

            }
            else {

                BookStatus bookStatus= BookStatus.valueOf(params[0]);
                if(storeUpType==StoreUpType.ARTICLE) {
                    for (DDReaderStoreUpArticle products : storeUpList.getReponseResultArticle().getData().getStoreUpList()) {

                        productIds.add("" + products.getArticleId());
                    }
                }
                else if(storeUpType==StoreUpType.POST) {
                    for (DDReaderStoreUpPost products : storeUpList.getReponseResultPost().getData().getStoreUpList()) {
                        productIds.add("" + products.getPostId());
                    }
                }

                Integer number=Integer.parseInt(params[1]);
                List<MediaDigest> mediaDigests = MediaDigestDb.getMediaDigest(storeUpType,bookStatus,productIds,false,number);
                for(MediaDigest mediaDigest:mediaDigests){
                    mediaIds.add(mediaDigest.getId());
                }
            }


            paramMap.put(key, StringUtils.join(mediaIds,","));

        }
        else{
            throw new Exception("GetProductAndSaleIdParse参数为空");
        }

    }
}
