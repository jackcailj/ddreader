package com.dangdang.readerV5.personal_center.bookshelf;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.authority.AuthorityDb;
import com.dangdang.authority.MediaAuthority;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.common.functional.login.ILogin;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.Util;
import com.dangdang.digital.BookStatus;
import com.dangdang.digital.BookType;
import com.dangdang.digital.MediaDb;
import com.dangdang.digital.meta.Media;
import com.dangdang.readerV5.reponse.GetUserBookListReponse;
import com.dangdang.readerV5.reponse.UserBookMedia;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
            List<MediaAuthority> mediaAuthorities= AuthorityDb.getUserEbook(login.getCustId());

            if(reponseResult.getData().getMediaList()!=null) {
                List<String> productIds = Util.getFields(mediaAuthorities, "productId");

                List<Media> medias = MediaDb.getMedias(productIds);

                //按照productIds列表顺序排序
                List<UserBookMedia> mediasSort = new ArrayList<UserBookMedia>();
                for (MediaAuthority mediaAuthority : mediaAuthorities) {
                    for (Media media : medias) {
                        if (mediaAuthority.getProductId() == media.getProductId()) {
                            UserBookMedia userBookMedia = JSONObject.parseObject(JSONObject.toJSONString(media), UserBookMedia.class);
                            userBookMedia.setIsHide(mediaAuthority.getIsHide());
                            userBookMedia.setCoverPic(null);
                            mediasSort.add(userBookMedia);
                        }
                    }
                }

                for (UserBookMedia media : reponseResult.getData().getMediaList()) {
                    media.setCoverPic(null);
                }

                dataVerifyManager.add(new ListVerify(reponseResult.getData().getMediaList(), mediasSort, true));
            }
            else{
                dataVerifyManager.add(new ValueVerify<Integer>(0,mediaAuthorities.size()));
            }

        }
        else{
            dataVerifyManager.add(new ValueVerify<Object>(null,reponseResult.getData().getMediaList()), VerifyResult.SUCCESS);
        }

        super.dataVerify();
    }
}
