package org.cyk.utility.server.persistence.query;

import java.io.Serializable;

import org.cyk.utility.instance.AbstractInstanceRepositoryImpl;

public abstract class AbstractPersistenceQueryRepositoryImpl extends AbstractInstanceRepositoryImpl<PersistenceQuery> implements PersistenceQueryRepository, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Object getSystemIdentifier(PersistenceQuery instance) {
		return instance.getIdentifier();
	}
	
}
