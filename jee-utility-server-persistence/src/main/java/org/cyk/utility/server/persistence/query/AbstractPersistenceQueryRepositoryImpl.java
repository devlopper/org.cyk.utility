package org.cyk.utility.server.persistence.query;

import java.io.Serializable;

import org.cyk.utility.instance.AbstractInstanceRepositoryImpl;

public abstract class AbstractPersistenceQueryRepositoryImpl extends AbstractInstanceRepositoryImpl<PersistenceQuery> implements PersistenceQueryRepository, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setInstanceClass(PersistenceQuery.class);
	}
	
}
