package com.dangdang.param.parse;

import com.dangdang.ddframework.core.VariableStore;
import com.dangdang.ddframework.fitnesse.ParamParse;
import com.dangdang.enumeration.BookStatus;
import com.dangdang.enumeration.BookType;
import com.dangdang.db.digital.MediaDb;

import com.dangdang.digital.meta.Media;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



/**
 * Created by cailianjie on 2015-7-13.
 * 获取书籍ID
 * 格式：#GetProductIds#BookType，BookStatus，数量 and 123
 */
public class GetProductIdsParse implements IParamParse{

    public  static String MEDIA_VAR="medias";

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
            String[] params = ParamParse.parseParam(param);;
            BookType bookType = BookType.valueOf(params[0]);
            BookStatus bookStatus= BookStatus.valueOf(params[1]);

            //用来处理混合情况，比如有效和无效的productId一起
            String[] numberSplit =ParamParse.parseParam(params[2], ParamParse.AND);
            Integer number=Integer.parseInt(numberSplit[0].trim());

            List<Media> medias=MediaDb.getMedias(bookType, bookStatus, number);
            List<String> mediaIds = new ArrayList<String>();
            for(Media media : medias){
                mediaIds.add(media.getMediaId().toString());
            }

            if(numberSplit.length>1){
                mediaIds.add(numberSplit[1].trim());
            }

            paramMap.put(key, StringUtils.join(mediaIds,","));
            VariableStore.add(MEDIA_VAR,medias);

        }
        else{
            throw new Exception("GetProductIdsParse参数为空");
        }

    }
}
