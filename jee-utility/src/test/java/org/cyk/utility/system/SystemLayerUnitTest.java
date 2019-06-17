package org.cyk.utility.system;

import org.cyk.utility.system.layer.SystemLayerPersistence;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class SystemLayerUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void getPersistenceIdentifier() {
		assertionHelper.assertEquals("Persistence", __inject__(SystemLayerPersistence.class).getIdentifier());
	}
	
}
