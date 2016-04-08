package com.dangdang.db.bookbar;

import com.dangdang.bookbar.meta.BarModule;
import com.dangdang.config.Config;
import com.dangdang.ddframework.dbutil.DbUtil;

/**
 * Created by cailianjie on 2016-3-25.
 */
public class BarModuleDb {

    /*
    获取首页吧列表模块
     */
    public  static BarModule getHomePageBarModule(String  location) throws Exception {
        String selectString="select * from bar_module where module_location='"+location+"' and status =1 order by module_order desc limit 1";

        BarModule barModule = DbUtil.selectOne(Config.BOOKBARDBConfig,selectString,BarModule.class);

        return barModule;
    }

    /*
    获取首页吧列表模块
     */
    public  static BarModule getBarModuleById(String  barModuleId) throws Exception {
        String selectString="select * from bar_module where bar_module_id="+barModuleId+" and status =1 order by module_order desc limit 1";

        BarModule barModule = DbUtil.selectOne(Config.BOOKBARDBConfig,selectString,BarModule.class);

        return barModule;
    }

}
