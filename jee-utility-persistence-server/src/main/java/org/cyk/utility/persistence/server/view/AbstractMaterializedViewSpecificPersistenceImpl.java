package org.cyk.utility.persistence.server.view;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.utility.persistence.MaterializedViewSpecificPersistence;
import org.cyk.utility.persistence.server.AbstractSpecificPersistenceImpl;

public abstract class AbstractMaterializedViewSpecificPersistenceImpl<ENTITY> extends AbstractSpecificPersistenceImpl<ENTITY> implements MaterializedViewSpecificPersistence<ENTITY>,Serializable {

	@Inject MaterializedViewCreator materializedViewCreator;
	@Inject MaterializedViewUpdator materializedViewUpdator;
	@Inject MaterializedViewActualizer materializedViewActualizer;
	@Inject MaterializedViewDeletor materializedViewDeletor;
	
	@Override
	public void create() {
		materializedViewCreator.execute(null, entityImplClass);
	}
	
	@Override
	public void update() {
		materializedViewUpdator.execute(null, entityImplClass);
	}
	
	@Override
	public void actualize() {
		materializedViewActualizer.execute(null, entityImplClass);
	}
	
	@Override
	public void delete() {
		materializedViewDeletor.execute(null, entityImplClass);
	}
}