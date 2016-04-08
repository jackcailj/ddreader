package com.dangdang.db.bookbar;

import com.dangdang.bookbar.meta.BarModule;
import com.dangdang.bookbar.meta.BarModuleContent;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;
import com.dangdang.readerV5.reponse.BarContent;

import java.util.List;

/**
 * Created by cailianjie on 2016-3-25.
 */
public class BarModuleContentDb {

    /*
        根据吧模块ID获取首页吧模块内容
    */
    public  static List<BarContent> getBarModule(String barModuleId) throws Exception {

        BarModule barModule = BarModuleDb.getBarModuleById(barModuleId);

        String selectString="select bar.bar_name as barName,bar.article_num as articleNum,(case when bar.bar_desc='' then '在人生的道路上，当你的希望一个个落空的时候，你也要坚定，要沉着。' else bar.bar_desc end) as barDesc,bar.isActivity as  isActivity,bar.member_num as memberNum,bar.bar_id as barId from bar_module_content bmc \n" +
                "left join bar on bmc.content_id=bar.bar_id\n" +
                " where bmc.module_tag_id="+barModuleId+" and bar.bar_status!=4  order by content_order desc,bar_module_content_id limit "+barModule.getShowNum();

        List<BarContent> barModuleContents = DbUtil.selectList(Config.BOOKBARDBConfig,selectString,BarContent.class);

        return barModuleContents;
    }




    /*
     根据帖子模块获取配置的帖子内容
 */
    public  static List<BarModuleContent> getPostInfoByLocation(String location) throws Exception {

        BarModule barModule = BarModuleDb.getHomePageBarModule(location);

        String selectString="select bmc.* from bar_module_content bmc\n" +
                "left join bar_module  bm on bmc.module_tag_id=bm.bar_module_id\n" +
                " where module_location='"+location+"' and bmc.`status`=1 and NOW() BETWEEN begin_date and end_date order by content_order desc,bar_module_content_id limit "+barModule.getShowNum();

        List<BarModuleContent> barModuleContents = DbUtil.selectList(Config.BOOKBARDBConfig,selectString,BarModuleContent.class);

        return barModuleContents;
    }
}
