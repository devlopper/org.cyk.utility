package org.cyk.utility.test.arquillian;

import java.io.Serializable;

import org.cyk.utility.__kernel__.system.action.SystemActor;
import org.cyk.utility.__kernel__.system.action.SystemActorClient;

public abstract class AbstractSystemClientArquillianIntegrationTestImpl<LAYER_ENTITY_INTERFACE> extends AbstractSystemLayerArquillianIntegrationTestImpl<LAYER_ENTITY_INTERFACE> implements SystemClientIntegrationTest<LAYER_ENTITY_INTERFACE>, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public SystemActor __getSystemActor__() {
		return __inject__(SystemActorClient.class);
	}
	
}
