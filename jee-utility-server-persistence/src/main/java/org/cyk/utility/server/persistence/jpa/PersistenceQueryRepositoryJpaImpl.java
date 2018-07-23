package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;
import java.util.Collection;

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
	public InstanceRepository<PersistenceQuery> add(Collection<PersistenceQuery> instances) {
		super.add(instances);
		for(PersistenceQuery index : instances){
			entityManager.getEntityManagerFactory().addNamedQuery(index.getIdentifier(), entityManager.createQuery(index.getValue(), index.getResultClass()));
			//__inject__(Log.class).executeInfo("named query added to entity manager factory. name="+index.getIdentifier()+" , value="+index.getValue()
			//	+" , class="+index.getResultClass()).execute();
		}
		return this;
	}

}
