package org.cyk.utility.__kernel__;

import org.cyk.utility.__kernel__.maven.pom.Pom;
import org.junit.Assert;
import org.junit.Test;

public class SystemPropertiesUnitTest {
	
	static {
		Assert.assertEquals("value01", Pom.INSTANCE.getMavenSurefirePluginConfigurationSystemProperty("unittest.property.01"));
	}
	
	@Test
	public void getUserDir(){
		Assert.assertEquals("value01", Pom.INSTANCE.getMavenSurefirePluginConfigurationSystemProperty("unittest.property.01"));
	}
}
