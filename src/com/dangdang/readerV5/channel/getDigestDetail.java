package com.dangdang.readerV5.channel;

import com.dangdang.autotest.common.FixtureBase;
import com.dangdang.digital.ChannelSQL;

import fitnesse.slim.SystemUnderTest;

/**
 * 文章详情页接口
 * @author guohaiying
 *
 */
public class getDigestDetail extends FixtureBase{

	@SystemUnderTest
	ChannelSQL sql = new ChannelSQL();
}
