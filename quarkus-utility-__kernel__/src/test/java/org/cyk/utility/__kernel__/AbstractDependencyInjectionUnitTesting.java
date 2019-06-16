package org.cyk.utility.__kernel__;

import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractDependencyInjectionUnitTesting extends AbstractArquillianUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void injectNullIsNull(){
		Assert.assertNull(__inject__(null));
	}
	
	@Test
	public void injectMyClassIsNotNull(){
		Assert.assertNotNull(__inject__(Class01.class));
	}
	
	@Test
	public void injectManySingletonInstanceAreSame(){
		for(Integer index = 0; index < 500; index++)
			Assert.assertEquals(__inject__(MySingleton.class),__inject__(MySingleton.class));
	}
	
}
