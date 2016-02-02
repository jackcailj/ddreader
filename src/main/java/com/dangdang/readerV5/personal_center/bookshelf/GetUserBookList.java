package com.dangdang.readerV5.personal_center.bookshelf;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.config.Config;
import com.dangdang.db.authority.AuthorityDb;
import com.dangdang.authority.meta.MediaAuthority;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.common.functional.login.ILogin;
import com.dangdang.db.digital.MediaDb;
import com.dangdang.db.digital.MediaResfileDb;
import com.dangdang.ddframework.dataverify.ListVerify;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.ddframework.util.Util;
import com.dangdang.digital.meta.Media;
import com.dangdang.digital.meta.MediaResfile;
import com.dangdang.readerV5.reponse.GetUserBookListReponse;
import com.dangdang.readerV5.reponse.UserBookMedia;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

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
        paramMap.put("pageSize","1000");
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(), new TypeReference<ReponseV2<GetUserBookListReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseResult.getStatus().getCode()==0){
            List<MediaAuthority> mediaAuthorities= AuthorityDb.getUserEbook(login.getCustId(), StringUtils.isBlank(paramMap.get("pageSize"))?100:Integer.parseInt(paramMap.get("pageSize")));

            if(reponseResult.getData().getMediaList()!=null) {
                List<String> productIds = Util.getFields(mediaAuthorities, "productId");
               /* if(CollectionUtils.isNotEmpty(productIds)){
                    List<MediaResfile> resfiles = MediaResfileDb.getMediaByDevice(productIds, Config.getDevice());
                    productIds = Util.getFields(resfiles,"media_id");
                }



                List<String> returnProductIds =Util.getFields(reponseResult.getData().getMediaList(), "mediaId");

                dataVerifyManager.add(new ListVerify(returnProductIds,productIds,false));*/

                List<Media> medias = MediaDb.getMedias(productIds);
                List<MediaResfile> resfiles = MediaResfileDb.getMediaByDevice(productIds, Config.getDevice());
                List<String> resfileProductIds = Util.getFields(resfiles,"mediaId");
                //按照productIds列表顺序排序
               // StringBuilder builder=new StringBuilder();
                List<UserBookMedia> mediasSort = new ArrayList<UserBookMedia>();

                for (MediaAuthority mediaAuthority : mediaAuthorities) {
                    for (Media media : medias) {
                        if (mediaAuthority.getProductId() == media.getMediaId()) {//过滤不支持当前设备的书籍
                            if(media.getDocType().toLowerCase().equals("drebook") && !resfileProductIds.contains(media.getMediaId().toString())){
                                continue;
                            }

                            UserBookMedia userBookMedia = new UserBookMedia();//JSONObject.parseObject(JSONObject.toJSONString(media), UserBookMedia.class);
                            userBookMedia.setMediaId(media.getMediaId());
                            userBookMedia.setAuthorityType(mediaAuthority.getAuthorityType());
                            userBookMedia.setRelationType(mediaAuthority.getRelationType());
                            userBookMedia.setIsHide(mediaAuthority.getIsHide());


                            mediasSort.add(userBookMedia);

                        }
                    }
                   // builder.append(Util.getJsonRegexString("mediaId",""+mediaAuthority.getProductId()));
                    //builder.append(".*?");
                }

               /* for (UserBookMedia media : reponseResult.getData().getMediaList()) {
                    media.setCoverPic(null);
                }*/

                dataVerifyManager.add(new ListVerify(reponseResult.getData().getMediaList(), mediasSort, true));
                //dataVerifyManager.add(new RegexVerify(builder.toString(),result.toString()));
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
