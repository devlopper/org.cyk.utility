package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.persistence.EntityManagerGetter;
import org.cyk.utility.__kernel__.string.StringHelper;

public class QueryExecutorImpl extends AbstractObject implements QueryExecutor,Serializable {

	@Override
	public <T> Collection<T> executeReadMany(Class<T> resultClass, Arguments arguments) {
		if(resultClass == null || arguments == null || arguments.getQuery() == null)
			return null;
		TypedQuery<T> typedQuery = __getTypedQuery__(resultClass, arguments.getQuery(), arguments.getEntityManager());
		Collection<T> collection = typedQuery.getResultList();
		return CollectionHelper.isEmpty(collection) ? null : collection;
	}

	@Override
	public <T> T executeReadOne(Class<T> resultClass, Arguments arguments) {
		if(resultClass == null || arguments == null || arguments.getQuery() == null)
			return null;
		TypedQuery<T> typedQuery = __getTypedQuery__(resultClass, arguments.getQuery(), arguments.getEntityManager());
		try {
			return typedQuery.getSingleResult();
		} catch (NoResultException exception) {
			return null;
		}
	}

	@Override
	public Integer executeUpdateOrDelete(Arguments arguments) {
		if(arguments == null || arguments.getQuery() == null)
			return null;
		javax.persistence.Query query = __getQuery__(arguments.getQuery(), arguments.getEntityManager());
		return query.executeUpdate();
	}

	/**/
	
	protected <T> TypedQuery<T> __getTypedQuery__(Class<T> resultClass, Query query,EntityManager entityManager) {
		if(resultClass == null || query == null)
			return null;
		if(entityManager == null)
			entityManager = DependencyInjection.inject(EntityManagerGetter.class).get();
		String queryIdentifier = StringHelper.get(query.getIdentifier());
		return (TypedQuery<T>) (StringHelper.isBlank(queryIdentifier) ? entityManager.createQuery(query.getValue(), resultClass) 
				: entityManager.createNamedQuery(queryIdentifier, resultClass));
	}
	
	protected javax.persistence.Query __getQuery__(Query query,EntityManager entityManager) {
		if(query == null)
			return null;
		if(entityManager == null)
			entityManager = DependencyInjection.inject(EntityManagerGetter.class).get();
		String queryIdentifier = StringHelper.get(query.getIdentifier());
		return (javax.persistence.Query) (StringHelper.isBlank(queryIdentifier) ? entityManager.createQuery(query.getValue()) 
				: entityManager.createNamedQuery(queryIdentifier));
	}
}
