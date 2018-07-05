package org.cyk.utility.architecture.system;

import org.cyk.utility.test.AbstractArquillianUnitTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;

public class SystemActorUnitTest extends AbstractArquillianUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void getServerIdentifier() {
		assertionHelper.assertEquals("Server", __inject__(SystemActorServer.class).getIdentifier());
	}
	
	/* Deployment */

	@Deployment
	public static JavaArchive createDeployment() {
		return AbstractArquillianUnitTest.createJavaArchiveDeployment().addPackage(SystemActorUnitTest.class.getPackage());
	}
}
