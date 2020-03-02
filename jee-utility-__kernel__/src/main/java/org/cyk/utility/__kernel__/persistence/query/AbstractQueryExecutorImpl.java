package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.persistence.EntityManagerGetter;
import org.cyk.utility.__kernel__.persistence.query.filter.Filter;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.jboss.weld.exceptions.IllegalArgumentException;

public abstract class AbstractQueryExecutorImpl extends AbstractObject implements QueryExecutor,Serializable {

	@Override
	public <T> Collection<T> executeReadMany(Class<T> resultClass, Arguments arguments) {
		validatePreConditions(resultClass, arguments);
		TypedQuery<T> typedQuery = __getTypedQuery__(resultClass, arguments.getQuery().getIdentifier(),arguments.getQuery().getValue()
				,arguments.getParameters(),arguments.getFilter(),arguments.getFirstTupleIndex(),arguments.getNumberOfTuples(), arguments.getEntityManager());
		Collection<T> collection = typedQuery.getResultList();
		return CollectionHelper.isEmpty(collection) ? null : collection;
	}
	
	@Override
	public Long executeCount(Arguments arguments) {
		validatePreConditions(Long.class, arguments);
		Long count = executeReadOne(Long.class, arguments);
		return count;
	}
	
	@Override
	public Integer executeUpdateOrDelete(Arguments arguments) {
		validatePreConditions(Void.class, arguments);
		javax.persistence.Query query = __getQuery__(arguments.getQuery().getIdentifier(),arguments.getQuery().getValue(), arguments.getEntityManager());
		return query.executeUpdate();
	}

	/**/
	
	protected void validatePreConditions(Class<?> resultClass, Arguments arguments) {
		if(resultClass == null)
			throw new RuntimeException("result class is required");
		if(arguments == null)
			throw new RuntimeException("arguments are required");
		if(arguments.getQuery() == null)
			throw new RuntimeException("query is required");
	}
	
	protected <T> TypedQuery<T> __getTypedQuery__(Class<T> resultClass, String queryIdentifier,String queryValue,Map<Object,Object> parameters,Filter filter,Integer firstTupleIndex,Integer numberOfTuples,EntityManager entityManager) {
		if(resultClass == null)
			throw new IllegalArgumentException("result class is required");
		if(StringHelper.isBlank(queryIdentifier) && StringHelper.isBlank(queryValue))
			throw new IllegalArgumentException("query identifier or query value is required");
		if(entityManager == null)
			entityManager = DependencyInjection.inject(EntityManagerGetter.class).get();
		Query query = QueryGetter.getInstance().get(resultClass, queryIdentifier, queryValue);
		TypedQuery<T> typedQuery = (TypedQuery<T>) (StringHelper.isBlank(query.getIdentifier()) ? entityManager.createQuery(query.getValue(), resultClass) 
				: entityManager.createNamedQuery(query.getIdentifier(), resultClass));
		if(firstTupleIndex != null && firstTupleIndex >= 0)
			typedQuery.setFirstResult(firstTupleIndex);
		if(numberOfTuples != null && numberOfTuples >= 1)
			typedQuery.setMaxResults(numberOfTuples);
		
		if(filter != null) {
			Map<Object,Object> map = filter.generateMap();
			if(MapHelper.isNotEmpty(map)) {
				for(Map.Entry<Object,Object> entry : map.entrySet()) {
					if(parameters == null || !parameters.containsKey(entry.getKey())) {
						if(parameters == null)
							parameters = new HashMap<>();
						parameters.put(entry.getKey(), entry.getValue());
					}
				}
			}
		}
		
		if(MapHelper.isNotEmpty(parameters))
			for(Map.Entry<Object,Object> entry : parameters.entrySet()) {
				if(entry.getKey() instanceof String) {
					typedQuery.setParameter((String) entry.getKey(), entry.getValue());
				}
			}
		return typedQuery;
	}
	
	protected javax.persistence.Query __getQuery__(String queryIdentifier,String queryValue,EntityManager entityManager) {
		if(StringHelper.isBlank(queryIdentifier) && StringHelper.isBlank(queryValue))
			throw new IllegalArgumentException("query identifier or query value is required");
		if(entityManager == null)
			entityManager = DependencyInjection.inject(EntityManagerGetter.class).get();
		Query query = QueryGetter.getInstance().get(Void.class, queryIdentifier, queryValue);
		return (javax.persistence.Query) (StringHelper.isBlank(queryIdentifier) ? entityManager.createQuery(query.getValue()) 
				: entityManager.createNamedQuery(queryIdentifier));
	}
	
}
