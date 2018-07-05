package org.cyk.utility.server.persistence;

import java.io.Serializable;

import org.cyk.utility.architecture.system.AbstractSystemServiceProviderImpl;
import org.cyk.utility.architecture.system.SystemActor;
import org.cyk.utility.architecture.system.SystemActorServer;
import org.cyk.utility.architecture.system.SystemLayer;
import org.cyk.utility.architecture.system.SystemLayerPersistence;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractPersistenceImpl extends AbstractSystemServiceProviderImpl implements Persistence,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected SystemActor getSystemActor() {
		return __inject__(SystemActorServer.class);
	}
	
	@Override
	protected SystemLayer getSystemLayer() {
		return __inject__(SystemLayerPersistence.class);
	}
	
	/**/
	
}
