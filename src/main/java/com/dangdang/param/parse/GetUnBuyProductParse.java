package com.dangdang.param.parse;

import com.dangdang.db.authority.AuthorityDb;
import com.dangdang.authority.meta.MediaAuthority;
import com.dangdang.common.functional.login.ILogin;
import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.ddframework.fitnesse.ParamParse;
import com.dangdang.enumeration.BookStatus;
import com.dangdang.enumeration.BookType;
import com.dangdang.db.digital.MediaDb;
import com.dangdang.digital.meta.Media;
import com.dangdang.enumeration.VarKey;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cailianjie on 2015-7-14.
 * 获取没有购买的商品id
 *
 * #GetUnBuyProduct#BookType，BookStatus，数量 and 123
 */
public class GetUnBuyProductParse implements IParamParse{


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
            BookType bookType = BookType.valueOf(params[0]);
            BookStatus bookStatus= BookStatus.valueOf(params[1]);


            //用来处理混合情况，比如有效和无效的productId一起
            String[] numberSplit =ParamParse.parseParam(params[2],ParamParse.AND);
            Integer number=Integer.parseInt(numberSplit[0].trim());

            //获取购物车中的商品
            //GetMyBoughtList myBoughtList = new GetMyBoughtList((ILogin) VariableStore.get(VarKey.LOGIN));
            //myBoughtList.doWork();
            List<MediaAuthority> mediaAuthorities = AuthorityDb.getMediaAuthority(((ILogin) VariableStore.get(VarKey.LOGIN)).getCustId());

            List<String> productIds = new ArrayList<String>();

            for (MediaAuthority products : mediaAuthorities) {
                productIds.add(""+products.getProductId());
            }

            //查找不在购物车中的prouctid，用来往购物车中添加
            List<Media> medias=MediaDb.getMedias(bookType, bookStatus, number,productIds);
            List mediaIds = new ArrayList();
            for(Media media : medias){
                mediaIds.add(media.getMediaId().toString());
            }

            if(numberSplit.length>1) {
                mediaIds.add(numberSplit[1].trim());
            }

            paramMap.put(key, StringUtils.join(mediaIds,","));

        }
        else{
            throw new Exception("GetUnBuyProductParse");
        }

    }
}
