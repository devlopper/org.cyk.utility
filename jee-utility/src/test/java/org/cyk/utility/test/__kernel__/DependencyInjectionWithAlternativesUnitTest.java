package org.cyk.utility.test.__kernel__;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;

public class DependencyInjectionWithAlternativesUnitTest extends AbstractDependencyInjectionUnitTesting {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void isClass01SecondVersionWhenClass01FirstVersionInjected() {
		assertionHelper.assertEquals(Class01SecondVersion.class, __inject__(Class01.class).getClass());
	}
	
	@Test
	public void isClass02FirstVersionWhenClass02FirstVersionInjected() {
		assertionHelper.assertEquals(Class02FirstVersion.class, __inject__(Class02.class).getClass());
	}
	
	/* Deployment*/
	
	@Deployment
	public static JavaArchive createDeploymentWithClass01SecondVersion() {
		return AbstractArquillianUnitTest.createJavaArchiveDeployment("org/cyk/utility/test/__kernel__/beans-with-alternatices.xml");
	}
	
}
