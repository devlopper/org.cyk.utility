package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

import org.cyk.utility.instance.InstanceRepository;
import org.cyk.utility.server.persistence.query.AbstractPersistenceQueryRepositoryImpl;
import org.cyk.utility.server.persistence.query.PersistenceQuery;

@Singleton
public class PersistenceQueryRepositoryJpaImpl extends AbstractPersistenceQueryRepositoryImpl implements PersistenceQueryRepositoryJpa, Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private EntityManager entityManager;
	
	@Override
	public InstanceRepository<PersistenceQuery> add(PersistenceQuery query) {
		super.add(query);
		entityManager.getEntityManagerFactory().addNamedQuery(query.getIdentifier(), entityManager.createQuery(query.getValue(), query.getResultClass()));	
		return this;
	}
	
}
