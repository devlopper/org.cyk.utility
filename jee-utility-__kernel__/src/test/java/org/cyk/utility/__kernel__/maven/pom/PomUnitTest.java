package org.cyk.utility.__kernel__.maven.pom;

import static org.assertj.core.api.Assertions.assertThat;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class PomUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void build(){
		Pom pom = PomBuilderImpl.__execute__(System.getProperty("user.dir")+"/src/test/java/org/cyk/utility/__kernel__/maven/pom/pom.xml");
		assertThat(pom.getParent()).isNotNull();
		Assert.assertEquals("mypgid", pom.getParent().getGroupId());
		Assert.assertEquals("mypaid", pom.getParent().getArtifactId());
		Assert.assertEquals("mypversion", pom.getParent().getVersion());
		
		Assert.assertEquals("mygid", pom.getGroupId());
		Assert.assertEquals("myaid", pom.getArtifactId());
		Assert.assertEquals("myversion", pom.getVersion());
		
		assertThat(pom.getBuild()).isNotNull();
		assertThat(pom.getBuild().getPlugins()).isNotNull();
		assertThat(pom.getBuild().getPlugins().getCollection()).isNotNull();
		Assert.assertEquals(1, pom.getBuild().getPlugins().getCollection().size());
		assertThat(pom.getBuild().getPlugins().getCollection().iterator().next().getConfiguration()).isNotNull();
		assertThat(pom.getBuild().getPlugins().getCollection().iterator().next().getConfiguration().getSystemPropertyVariables()).isNotNull();
		Assert.assertEquals(1, pom.getBuild().getPlugins().getCollection().iterator().next().getConfiguration()
				.getSystemPropertyVariables().size());
		
		Assert.assertEquals("key01", pom.getBuild().getPlugins().getCollection().iterator().next().getConfiguration()
				.getSystemPropertyVariables().entrySet().iterator().next().getKey());
		Assert.assertEquals("value01", pom.getBuild().getPlugins().getCollection().iterator().next().getConfiguration()
				.getSystemPropertyVariables().entrySet().iterator().next().getValue());		
	}
	
}
