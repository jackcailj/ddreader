package com.dangdang.readerV5.read_activitiy;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.BaseComment.meta.Comment;
import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.db.comment.CommentDb;
import com.dangdang.ddframework.dataverify.ValueVerify;
import com.dangdang.ddframework.dataverify.VerifyResult;
import com.dangdang.ddframework.reponse.ReponseV2;
import com.dangdang.readerV5.reponse.TopAndWonderfulCommentReponse;

/**
 * Created by cailianjie on 2016-4-28.
 */
public class TopAndWonderfulComment extends FixtureBase{

    ReponseV2<TopAndWonderfulCommentReponse> reponseResult;

    public TopAndWonderfulComment(){}

    public ReponseV2<TopAndWonderfulCommentReponse> getReponseResult() {
        return reponseResult;
    }

    @Override
    public void doWork() throws Exception {
        super.doWork();

        reponseResult = JSONObject.parseObject(result.toString(),new TypeReference<ReponseV2<TopAndWonderfulCommentReponse>>(){});
    }

    @Override
    protected void dataVerify() throws Exception {
        if(reponseV2Base.getStatus().getCode()==0){
            Comment comment  = CommentDb.getCommentById(paramMap.get("commentId"));

            switch (paramMap.get("actionType")){
                case "top":{
                    dataVerifyManager.add(new ValueVerify<Byte>(comment.getIsTop(), (byte) 1));
                }
                break;
                case "cancelTop":{
                    dataVerifyManager.add(new ValueVerify<Byte>(comment.getIsTop(),(byte)0));
                }
                break;
                case "wonderful":{
                    dataVerifyManager.add(new ValueVerify<Byte>(comment.getIsWonderful(),(byte)1));
                }
                break;
                case "cancelWonderful":{
                    dataVerifyManager.add(new ValueVerify<Byte>(comment.getIsWonderful(),(byte)0));
                }
                break;
            }
        }
        else {
            dataVerifyManager.add(new ValueVerify<Object>(reponseResult.getData().getCommentVo(),null), VerifyResult.SUCCESS);
        }
        super.dataVerify();
    }
}
