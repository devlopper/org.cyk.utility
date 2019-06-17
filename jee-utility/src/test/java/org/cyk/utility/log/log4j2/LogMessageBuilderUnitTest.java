package org.cyk.utility.log.log4j2;

import org.cyk.utility.log.message.LogMessageBuilder;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class LogMessageBuilderUnitTest extends AbstractArquillianUnitTest {
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
	
}
