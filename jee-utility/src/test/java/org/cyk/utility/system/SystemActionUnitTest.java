package org.cyk.utility.system;

import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class SystemActionUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void getCreateIdentifier() {
		assertionHelper.assertEquals("Create", __inject__(SystemActionCreate.class).getIdentifier());
	}
		
}
