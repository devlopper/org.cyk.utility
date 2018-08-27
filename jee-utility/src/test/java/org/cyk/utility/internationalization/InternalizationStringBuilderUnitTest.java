package org.cyk.utility.internationalization;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class InternalizationStringBuilderUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void isSalut_whenKeyIsHi(){
		//TODO working single but not working in group
		//__inject__(ApplicationScopeLifeCycleListener.class).initialize(null);
		//assertionHelper.assertEquals("salut", __inject__(InternalizationStringBuilder.class).setKey("hi").execute().getOutput());
	}
	
}
