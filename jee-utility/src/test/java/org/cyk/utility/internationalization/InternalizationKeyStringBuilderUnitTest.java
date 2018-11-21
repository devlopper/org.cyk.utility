package org.cyk.utility.internationalization;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class InternalizationKeyStringBuilderUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void none(){
		assertionHelper.assertEquals(null, __inject__(InternalizationKeyStringBuilder.class).execute().getOutput());
	}
	
	@Test
	public void empty(){
		assertionHelper.assertEquals(null, __inject__(InternalizationKeyStringBuilder.class).setValue("").execute().getOutput());
	}
	
	@Test
	public void hi(){
		assertionHelper.assertEquals("hi", __inject__(InternalizationKeyStringBuilder.class).setValue("hi").execute().getOutput());
	}
	
	@Test
	public void _hi(){
		assertionHelper.assertEquals("_.hi", __inject__(InternalizationKeyStringBuilder.class).setValue("_hi").execute().getOutput());
	}
	
	@Test
	public void hi_(){
		assertionHelper.assertEquals("hi._", __inject__(InternalizationKeyStringBuilder.class).setValue("hi_").execute().getOutput());
	}
	
	@Test
	public void camelCase(){
		assertionHelper.assertEquals("camel.case", __inject__(InternalizationKeyStringBuilder.class).setValue("camelCase").execute().getOutput());
	}
	
	@Test
	public void camelCase01(){
		assertionHelper.assertEquals("camel.case.01", __inject__(InternalizationKeyStringBuilder.class).setValue("camelCase01").execute().getOutput());
	}
	
	@Test
	public void camelCase01ABC(){
		assertionHelper.assertEquals("camel.case.01.abc", __inject__(InternalizationKeyStringBuilder.class).setValue("camelCase01ABC").execute().getOutput());
	}
	
	@Test
	public void camelCase01ABCOk(){
		assertionHelper.assertEquals("camel.case.01.abc.ok", __inject__(InternalizationKeyStringBuilder.class).setValue("camelCase01ABCOk").execute().getOutput());
	}
}
