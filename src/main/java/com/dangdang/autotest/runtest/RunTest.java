package com.dangdang.autotest.runtest;


import com.dangdang.ddframework.core.RunTestBase;
import com.dangdang.autotest.config.Config;

/*
 * 测试用例运行管理类，
 * 解析命令行参数，并运行测试用例
 * 命令行实例：
 * java Runtest env=TESTING device=ANDROID testng=E:\work\原创项目\yc_interface_tests\bin\testng
 */

public class RunTest extends RunTestBase{
	
	@Override
	public void Config() {
		Config.Init();
	};
	
	/*
	 * 命令行执行主函数
	 */
	public static void main(String[] args) throws Exception
	{
		RunTest runTest =new RunTest();
		runTest.run(args);
	}
}
