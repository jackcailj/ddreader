package com.dangdang.readerV5.bookbar;

import com.dangdang.autotest.common.FixtureBase;

public class TopAndWonderful extends FixtureBase{
	
	
	
	//SELECT bm.bar_id, bm.member_status, a.media_digest_id from bar_member as bm left join article as a on bm.bar_id=a.bar_id 
	//where bm.member_status=3 and bm.cust_id=50232713 and a.is_show=1 and a.is_del=0 ORDER BY RAND() limit 1

}
