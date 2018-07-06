package org.cyk.utility.system;

import org.cyk.utility.system.layer.SystemLayerPersistence;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class SystemLayerUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void getPersistenceIdentifier() {
		assertionHelper.assertEquals("Persistence", __inject__(SystemLayerPersistence.class).getIdentifier());
	}
	
}
