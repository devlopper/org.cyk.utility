package org.cyk.utility.test.business.server;

import org.cyk.utility.business.server.EntityCreator;
import org.cyk.utility.business.server.EntityDeletor;
import org.cyk.utility.business.server.EntityReader;
import org.cyk.utility.business.server.EntityUpdater;

public abstract class AbstractUnitTest extends org.cyk.utility.test.persistence.server.AbstractUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		EntityCreator.INSTANCE.set(null);
		EntityReader.INSTANCE.set(null);
		EntityUpdater.INSTANCE.set(null);
		EntityDeletor.INSTANCE.set(null);
	}
	
}