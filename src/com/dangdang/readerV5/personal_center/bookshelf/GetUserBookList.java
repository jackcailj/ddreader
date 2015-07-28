package com.dangdang.readerV5.personal_center.bookshelf;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.authority.AuthorityDb;
import com.dangdang.authority.MediaAuthority;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.common.functional.login.ILogin;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.Util;
import com.dangdang.digital.BookStatus;
import com.dangdang.digital.BookType;
import com.dangdang.digital.MediaDb;
import com.dangdang.digital.meta.Media;
import com.dangdang.readerV5.reponse.GetUserBookListReponse;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by cailianjie on 2015-7-15.
 */
public class GetUserBookList extends FixtureBase{

    ReponseV2<GetUserBookListReponse> reponseResult;

    public GetUserBookList(){}

    public ReponseV2<GetUserBookListReponse> getReponseResult() {
        return reponseResult;
    }

    public GetUserBookList(ILogin login){
        setLogin(login);
        paramMap.put("token",login.getToken());
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<GetUserBookListReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            /*List<MediaAuthority> mediaAuthorities= AuthorityDb.getUserEbook(login.getCustId());

            List<String> productIds = Util.getFields(mediaAuthorities,"mediaId");
            List<Media> medias = MediaDb.getMedias(BookType.EBOOK, BookStatus.VALID, productIds);*/

        }
        super.dataVerify();
    }
}
