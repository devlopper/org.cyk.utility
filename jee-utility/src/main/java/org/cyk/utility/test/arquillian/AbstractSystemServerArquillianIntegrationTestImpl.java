package org.cyk.utility.test.arquillian;

import java.io.Serializable;

import org.cyk.utility.__kernel__.system.action.SystemActor;
import org.cyk.utility.__kernel__.system.action.SystemActorServer;

public abstract class AbstractSystemServerArquillianIntegrationTestImpl<LAYER_ENTITY_INTERFACE> extends AbstractSystemLayerArquillianIntegrationTestImpl<LAYER_ENTITY_INTERFACE> implements SystemServerIntegrationTest<LAYER_ENTITY_INTERFACE>, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public SystemActor __getSystemActor__() {
		return __inject__(SystemActorServer.class);
	}
	
}
