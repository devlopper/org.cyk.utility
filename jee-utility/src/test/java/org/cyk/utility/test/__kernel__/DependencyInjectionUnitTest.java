package org.cyk.utility.test.__kernel__;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;

public class DependencyInjectionUnitTest extends AbstractDependencyInjectionUnitTesting {
	private static final long serialVersionUID = 1L;

	@Test
	public void isClass01FirstVersionWhenClass01FirstVersionInjected() {
		assertionHelper.assertEquals(Class01FirstVersion.class, __inject__(Class01.class).getClass());
	}
		
	/* Deployment*/
	
	@Deployment
	public static JavaArchive createDeployment() {
		return AbstractArquillianUnitTest.createJavaArchiveDeployment();
	}
	
}
