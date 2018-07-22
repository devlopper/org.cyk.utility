package org.cyk.utility.__kernel__.maven.pom;

import org.junit.Assert;
import org.junit.Test;

public class PomUnitTest {

	@Test
	public void build(){
		Pom pom = PomBuilderImpl.__execute__(System.getProperty("user.dir")+"/src/test/java/org/cyk/utility/__kernel__/maven/pom/pom.xml");
		Assert.assertNotNull(pom.getParent());
		Assert.assertEquals("mypgid", pom.getParent().getGroupId());
		Assert.assertEquals("mypaid", pom.getParent().getArtifactId());
		Assert.assertEquals("mypversion", pom.getParent().getVersion());
		
		Assert.assertEquals("mygid", pom.getGroupId());
		Assert.assertEquals("myaid", pom.getArtifactId());
		Assert.assertEquals("myversion", pom.getVersion());
		
		Assert.assertNotNull(pom.getBuild());
		Assert.assertNotNull(pom.getBuild().getPlugins());
		Assert.assertNotNull(pom.getBuild().getPlugins().getCollection());
		Assert.assertEquals(1, pom.getBuild().getPlugins().getCollection().size());
		Assert.assertNotNull(pom.getBuild().getPlugins().getCollection().iterator().next().getConfiguration());
		Assert.assertNotNull(pom.getBuild().getPlugins().getCollection().iterator().next().getConfiguration().getSystemPropertyVariables());
		Assert.assertEquals(1, pom.getBuild().getPlugins().getCollection().iterator().next().getConfiguration()
				.getSystemPropertyVariables().size());
		
		Assert.assertEquals("key01", pom.getBuild().getPlugins().getCollection().iterator().next().getConfiguration()
				.getSystemPropertyVariables().entrySet().iterator().next().getKey());
		Assert.assertEquals("value01", pom.getBuild().getPlugins().getCollection().iterator().next().getConfiguration()
				.getSystemPropertyVariables().entrySet().iterator().next().getValue());
		
	}
	
}
