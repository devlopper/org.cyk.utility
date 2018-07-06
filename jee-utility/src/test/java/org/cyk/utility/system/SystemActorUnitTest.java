package org.cyk.utility.system;

import org.cyk.utility.system.action.SystemActorServer;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class SystemActorUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void getServerIdentifier() {
		assertionHelper.assertEquals("Server", __inject__(SystemActorServer.class).getIdentifier());
	}
	
	
}
