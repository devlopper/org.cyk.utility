package org.cyk.utility.server.persistence;

import javax.persistence.EntityManager;

public class PersistenceFunctionCreatorImpl extends AbstractPersistenceFunctionCreatorImpl implements PersistenceFunctionCreator {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __act__(Object entity) {
		__inject__(EntityManager.class).persist(entity);
	}
	
}
