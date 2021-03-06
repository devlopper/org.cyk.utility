package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.cyk.utility.server.persistence.PersistenceServiceProvider;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractPersistenceImpl extends org.cyk.utility.server.persistence.AbstractPersistenceImpl implements Persistence,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public PersistenceServiceProvider<Object> clear() {
		__inject__(EntityManager.class).clear();
		return this;
	}
	
}
