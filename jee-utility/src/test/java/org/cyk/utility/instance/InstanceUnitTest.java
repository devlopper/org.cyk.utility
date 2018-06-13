package org.cyk.utility.instance;

import org.cyk.utility.test.AbstractArquillianUnitTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

public class InstanceUnitTest extends AbstractArquillianUnitTest {
	private static final long serialVersionUID = 1L;

	
	
	/* Deployment */
	
	@Deployment
	public static JavaArchive createDeployment01() {
		return AbstractArquillianUnitTest.createDeployment().addPackage(InstanceUnitTest.class.getPackage());
	}
	
}
