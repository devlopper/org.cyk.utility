package org.cyk.utility.test.__kernel__;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTest;
import org.junit.Test;

public abstract class AbstractDependencyInjectionUnitTesting extends AbstractArquillianUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void injectNullIsNull(){
		assertionHelper.assertNull(__inject__(null));
	}
	
	@Test
	public void injectMyClassIsNotNull(){
		assertionHelper.assertNotNull(__inject__(Class01.class));
	}
	
	@Test
	public void injectManySingletonInstanceAreSame(){
		for(Integer index = 0; index < 500; index++)
			assertionHelper.assertEquals(__inject__(MySingleton.class),__inject__(MySingleton.class));
	}
	
}
