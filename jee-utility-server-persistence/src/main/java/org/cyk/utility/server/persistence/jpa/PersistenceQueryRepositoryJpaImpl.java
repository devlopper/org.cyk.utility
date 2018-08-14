package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.cyk.utility.instance.InstanceRepository;
import org.cyk.utility.server.persistence.query.AbstractPersistenceQueryRepositoryImpl;
import org.cyk.utility.server.persistence.query.PersistenceQuery;

@Singleton
public class PersistenceQueryRepositoryJpaImpl extends AbstractPersistenceQueryRepositoryImpl implements PersistenceQueryRepositoryJpa, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public InstanceRepository<PersistenceQuery> add(Collection<PersistenceQuery> instances) {
		super.add(instances);
		EntityManager entityManager = __inject__(EntityManager.class);
		for(PersistenceQuery index : instances){
			Class<?> resultClass = index.getResultClass();
			Query query = resultClass == null ? entityManager.createQuery(index.getValue()) : entityManager.createQuery(index.getValue(), resultClass);
			entityManager.getEntityManagerFactory().addNamedQuery((String) index.getIdentifier(), query);
			//__inject__(Log.class).executeInfo("named query added to entity manager factory. name="+index.getIdentifier()+" , value="+index.getValue()
			//	+" , class="+index.getResultClass()).execute();
		}
		return this;
	}

}
