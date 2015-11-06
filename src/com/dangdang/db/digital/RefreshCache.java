package com.dangdang.db.digital;

import com.dangdang.ddframework.util.TelnetUtil;

/**
 * 刷新后台的缓存
 * @author guohaiying
 *
 */
public class RefreshCache {
	
	public static void refresh() throws Exception{
		TelnetUtil telnet = new TelnetUtil();
		//telnet.sendCommands("select 10", "flushdb");
		telnet.sendCommands("select 10", "flushall");
		Thread.sleep(2000);
	}

}
