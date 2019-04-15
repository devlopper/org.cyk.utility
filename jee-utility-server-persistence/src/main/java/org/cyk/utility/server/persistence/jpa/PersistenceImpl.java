package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;

import javax.inject.Singleton;
import javax.persistence.EntityManager;

import org.cyk.utility.server.persistence.AbstractPersistenceImpl;
import org.cyk.utility.server.persistence.PersistenceServiceProvider;

@Singleton
public class PersistenceImpl extends AbstractPersistenceImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public PersistenceServiceProvider<Object> clear() {
		__inject__(EntityManager.class).clear();
		return this;
	}

}
