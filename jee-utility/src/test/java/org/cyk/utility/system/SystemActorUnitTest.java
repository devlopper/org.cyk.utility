package org.cyk.utility.system;

import org.cyk.utility.__kernel__.system.action.SystemActorServer;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class SystemActorUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void getServerIdentifier() {
		assertionHelper.assertEquals("Server", __inject__(SystemActorServer.class).getIdentifier());
	}
	
	
}
