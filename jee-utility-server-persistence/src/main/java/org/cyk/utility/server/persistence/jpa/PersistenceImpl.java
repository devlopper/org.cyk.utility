package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;

import org.cyk.utility.server.persistence.AbstractPersistenceImpl;
import org.cyk.utility.server.persistence.PersistenceServiceProvider;

@ApplicationScoped
public class PersistenceImpl extends AbstractPersistenceImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public PersistenceServiceProvider<Object> clear() {
		__inject__(EntityManager.class).clear();
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<Object> flush() {
		__inject__(EntityManager.class).flush();
		return this;
	}

}
