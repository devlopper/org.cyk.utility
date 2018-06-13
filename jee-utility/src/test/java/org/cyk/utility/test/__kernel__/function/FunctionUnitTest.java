package org.cyk.utility.test.__kernel__.function;

import org.cyk.utility.test.AbstractArquillianUnitTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;

public class FunctionUnitTest extends AbstractArquillianUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void injectNullIsNull(){
		assertionHelper.assertNull(__inject__(null));
	}
		
	/* Deployment*/
	
	@Deployment
	public static JavaArchive createDeployment() {
		return AbstractArquillianUnitTest.createJavaArchiveDeployment()
				.addPackage(FunctionUnitTest.class.getPackage());
	}
	
}
