package org.cyk.utility.log.slf4j;

import org.cyk.utility.log.message.LogMessageBuilder;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTest;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;

public class LogMessageBuilderSlf4jUnitTest extends AbstractArquillianUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void buildTemplateWithoutParameters(){
		assertionHelper.assertEquals("this a log without parameters", __inject__(LogMessageBuilder.class).setTemplateFormat("this a log without parameters")
				.execute().getOutput().getTemplate());
	}
	
	@Test
	public void buildWithOneParameter(){
		assertionHelper.assertEquals("p1", __inject__(LogMessageBuilder.class).addParameter("p1").execute().getOutput().getTemplate());
	}
	
	@Test
	public void buildWithOneParameterAndOnePairedParameter(){
		assertionHelper.assertEquals("p1 , p2={}", __inject__(LogMessageBuilder.class).addParameter("p1").addParameter("p2", "p2").execute().getOutput().getTemplate());
	}
	
	@Test
	public void buildTemplateWithOneParameter(){
		assertionHelper.assertEquals("p1={}", __inject__(LogMessageBuilder.class).addParameter("p1", "v1").execute().getOutput().getTemplate());
	}
	
	@Test
	public void buildTemplateWithTwoParameters(){
		assertionHelper.assertEquals("p1 {} , p2 {}", __inject__(LogMessageBuilder.class).setParameterFormat("%s {}").setParameterSeparator(" , ")
				.addParameter("p1", "v1").addParameter("p2", "v2").execute().getOutput().getTemplate());
	}
	
	/* Deployment */

	@Deployment
	public static JavaArchive createDeployment() {
		return AbstractArquillianUnitTestWithDefaultDeployment.createJavaArchiveDeployment("org/cyk/utility/log/slf4j/beans.xml");
	}

}
