package test;

import com.dangdang.ddframework.dataverify.verify_annotation.NotNull;
import fitnesse.plugins.PluginFeatureFactoryBase;

public class user extends PluginFeatureFactoryBase {

	@NotNull
	String userName="test";

	
}
