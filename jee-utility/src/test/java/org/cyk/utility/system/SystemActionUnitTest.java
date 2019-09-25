package org.cyk.utility.system;

import org.cyk.utility.__kernel__.system.action.SystemActionCreate;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class SystemActionUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void getCreateIdentifier() {
		assertionHelper.assertEquals("Create", __inject__(SystemActionCreate.class).getIdentifier());
	}
		
}
