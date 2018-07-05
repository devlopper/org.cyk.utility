package org.cyk.utility.architecture.system;

import org.cyk.utility.test.AbstractArquillianUnitTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;

public class SystemLayerUnitTest extends AbstractArquillianUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void getPersistenceIdentifier() {
		assertionHelper.assertEquals("Persistence", __inject__(SystemLayerPersistence.class).getIdentifier());
	}
	
	/* Deployment */

	@Deployment
	public static JavaArchive createDeployment() {
		return AbstractArquillianUnitTest.createJavaArchiveDeployment().addPackage(SystemLayerUnitTest.class.getPackage());
	}
}
