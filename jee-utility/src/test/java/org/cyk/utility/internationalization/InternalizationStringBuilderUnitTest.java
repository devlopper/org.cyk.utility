package org.cyk.utility.internationalization;

import org.cyk.utility.ApplicationScopeLifeCycleListener;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class InternalizationStringBuilderUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	static {
		//DependencyInjection.inject(ApplicationScopeLifeCycleListener.class).initialize(null);
	}
	
	@Override
	protected void __listenBeforeCallCountIsZero__() throws Exception {
		super.__listenBeforeCallCountIsZero__();
		//__inject__(ApplicationScopeLifeCycleListener.class).initialize(null);
		//__inject__(ApplicationScopeLifeCycleListener.class).__initialize__(null);//FIXME To solve different object reference used , second one not initialzed
	}
	
	@Test
	public void isSalut_whenKeyIsHi(){
		__inject__(ApplicationScopeLifeCycleListener.class).__initialize__(null);
		assertionHelper.assertEquals("salut", __inject__(InternalizationStringBuilder.class).setKey("hi").execute().getOutput());	
		assertionHelper.assertEquals("salut", __inject__(InternalizationStringBuilder.class).setKeyValue("hi").execute().getOutput());		
	}
	
	@Test
	public void isH1H2_whenKeyIsH1H2(){
		__inject__(ApplicationScopeLifeCycleListener.class).__initialize__(null);
		assertionHelper.assertEquals("##??h1H2??##", __inject__(InternalizationStringBuilder.class).setKey("h1H2").execute().getOutput());	
		assertionHelper.assertEquals("##??h.1.h.2??##", __inject__(InternalizationStringBuilder.class).setKeyValue("h1H2").execute().getOutput());		
	}
	
	@Test
	public void isNom_whenKeyIsFirstName(){
		__inject__(ApplicationScopeLifeCycleListener.class).__initialize__(null);
		assertionHelper.assertEquals("nom", __inject__(InternalizationStringBuilder.class).setKeyValue("firstName").execute().getOutput());		
	}
	
	@Test
	public void isPrnoms_whenKeyIsLastName(){
		__inject__(ApplicationScopeLifeCycleListener.class).__initialize__(null);
		assertionHelper.assertEquals("prénoms", __inject__(InternalizationStringBuilder.class).setKeyValue("lastNames").execute().getOutput());		
	}
}
