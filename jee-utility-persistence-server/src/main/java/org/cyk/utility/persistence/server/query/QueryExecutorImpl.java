package org.cyk.utility.persistence.server.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.computation.SortOrder;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.time.TimeHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.persistence.EntityManagerGetter;
import org.cyk.utility.persistence.query.Language;
import org.cyk.utility.persistence.query.Query;
import org.cyk.utility.persistence.query.QueryExecutor;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.query.QueryGetter;
import org.cyk.utility.persistence.query.QueryResultMapper;
import org.cyk.utility.persistence.server.TransientFieldsProcessor;
import org.cyk.utility.persistence.server.query.string.OrderStringBuilder;
import org.cyk.utility.persistence.server.query.string.Replacer;

public class QueryExecutorImpl extends AbstractObject implements QueryExecutor,Serializable {

	public static Boolean LOGGABLE = Boolean.TRUE;
	public static Level LOG_LEVEL = Level.FINEST;
			
	@Override
	public <T> Collection<T> executeReadMany(Class<T> resultClass, QueryExecutorArguments arguments) {
		validatePreConditions(resultClass, arguments);
		arguments.prepare(resultClass);
		TypedQuery<?> typedQuery = __getTypedQuery__(arguments.get__resultClass__(), arguments.get__query__(),arguments.get__parameters__(),arguments.getSortOrders()
				,arguments.getFirstTupleIndex(),arguments.getNumberOfTuples(),arguments.get__hints__(),arguments.get__entityManager__(),LOGGABLE
				,ValueHelper.defaultToIfNull(arguments.getLoggingLevel(),LOG_LEVEL));
		Long t = System.currentTimeMillis();
		Collection<?> result = typedQuery.getResultList();
		Long duration = System.currentTimeMillis() - t;
		Collection<T> collection;
		if(CollectionHelper.isEmpty(result))
			collection = null;
		else {
			if(resultClass.equals(arguments.get__resultClass__()))
				collection = (Collection<T>) result;
			else {
				QueryResultMapper.Arguments resultMapperArguments = new QueryResultMapper.Arguments().setQuery(arguments.getQuery());
				if(Tuple.class.equals(arguments.getQuery().getResultClass()))
					resultMapperArguments.setTuples((Collection<Tuple>) result);
				else
					resultMapperArguments.setObjects((Collection<Object[]>) result);
				collection = QueryResultMapper.getInstance().map(resultClass, resultMapperArguments);				
			}
		}
		if(CollectionHelper.isNotEmpty(collection) && CollectionHelper.isNotEmpty(arguments.getProcessableTransientFieldsNames()))
			TransientFieldsProcessor.getInstance().process(collection,arguments.getProcessableTransientFieldsNames());
		arguments.set__objects__(collection);
		arguments.finalise();
		if(CollectionHelper.isNotEmpty(collection))
			collection = processResult(resultClass, arguments, collection);
		if(Boolean.TRUE.equals(LOGGABLE)) {
			LogHelper.log(String.format("collection size = %s , duration = %s", CollectionHelper.getSize(collection),TimeHelper.formatDuration(duration)), LOG_LEVEL,getClass());
		}
		return collection;
	}
	
	@Override
	public <T> Collection<T> executeReadMany(Class<T> resultClass) {
		return executeReadMany(resultClass, new QueryExecutorArguments().setQuery(QueryGetter.getInstance().getBySelect(resultClass)));
	}
	
	@Override
	public <T> Collection<T> executeReadMany(Class<T> resultClass,String queryIdentifier,Object...filterFieldsValues) {
		if(resultClass == null)
			throw new RuntimeException("Result class is required");
		return executeReadMany(resultClass, new QueryExecutorArguments().setQueryFromIdentifier(queryIdentifier).addFilterFieldsValues(filterFieldsValues));
	}
	
	@Override
	public <T> T executeReadOne(Class<T> resultClass,QueryExecutorArguments arguments) {
		Collection<T> collection = executeReadMany(resultClass, arguments);
		if(CollectionHelper.getSize(collection) > 1)
			throw new RuntimeException(String.format("read.one : too much result found after executing query %s with filter %s",arguments.getQuery(),arguments.get__parameters__()));
		return CollectionHelper.getFirst(collection);
	}
	
	protected <T> Collection<T> processResult(Class<T> resultClass, QueryExecutorArguments arguments,Collection<T> collection) {
		return collection;
	}
	
	@Override
	public Long executeCount(QueryExecutorArguments arguments) {
		validatePreConditions(Long.class, arguments);
		//we do not need pagination for count query
		if(arguments != null) {
			arguments.setFirstTupleIndex(null);
			arguments.setNumberOfTuples(null);
		}
		Long count = executeReadOne(Long.class, arguments);
		return count;
	}
	
	@Override
	public Integer executeUpdateOrDelete(QueryExecutorArguments arguments) {
		validatePreConditions(Void.class, arguments);
		javax.persistence.Query query = __getQuery__(arguments.getQuery().getIdentifier(),arguments.getQuery().getValue(), arguments.getEntityManager());
		return query.executeUpdate();
	}

	public Collection<Object[]> readByValues(Collection<String> values) {
		if(CollectionHelper.isEmpty(values))
			return null;
		Collection<Object[]> collection = null;
		for(List<String> list : CollectionHelper.getBatches((List<String>) values, 1000)) {
			Collection<Object[]> result = EntityManagerGetter.getInstance().get().createNativeQuery("SELECT email,message FROM V_APP_EX_COMPTE_ERREUR WHERE email IN :emails")
					.setParameter("emails", list).getResultList();
			if(CollectionHelper.isNotEmpty(result)) {
				if(collection == null)
					collection = new ArrayList<>();
				collection.addAll(result);
			}
		}
		return collection;
	}
	
	/**/
	
	protected void validatePreConditions(Class<?> resultClass, QueryExecutorArguments arguments) {
		if(resultClass == null)
			throw new RuntimeException("result class is required");
		if(arguments == null)
			throw new RuntimeException("arguments are required");
		if(arguments.getQuery() == null)
			throw new RuntimeException("query is required");
	}
	
	protected <T> TypedQuery<T> __getTypedQuery__(Class<T> resultClass, Query query,Map<Object,Object> parameters,Map<String,SortOrder> sortOrders,Integer firstTupleIndex,Integer numberOfTuples,Map<String,Object> hints,EntityManager entityManager,Boolean loggable,java.util.logging.Level loggingLevel) {
		if(Boolean.TRUE.equals(loggable)) {
			Collection<String> strings = new ArrayList<>();
			if(StringHelper.isNotBlank(query.getIdentifier()))
				strings.add(query.getIdentifier());
			if(resultClass != null)
				strings.add(resultClass.getSimpleName());
			if(query != null)
				strings.add(query.getValue());
			if(MapHelper.isNotEmpty(parameters))
				strings.add(parameters.toString());
			if(firstTupleIndex != null)
				strings.add("Page("+firstTupleIndex+","+numberOfTuples+")");
			if(MapHelper.isNotEmpty(hints))
				strings.add(hints.toString());
			LogHelper.log(StringHelper.concatenate(strings,">"),loggingLevel,getClass());
		}
		if(resultClass == null)
			throw new IllegalArgumentException("result class is required");
		if(query == null)
			throw new IllegalArgumentException("query is required");
		if(StringHelper.isBlank(query.getIdentifier()) && StringHelper.isBlank(query.getValue()))
			throw new IllegalArgumentException("query identifier or query value is required");
		if(entityManager == null)
			entityManager = EntityManagerGetter.getInstance().get();
		//Query query = QueryGetter.getInstance().get(resultClass, queryIdentifier, queryValue);
		
		TypedQuery<T> typedQuery = null;
		if(StringHelper.isBlank(query.getIdentifier())) {
			String jpql = query.getValue();
			if(MapHelper.isNotEmpty(sortOrders)) {
				String order = StringUtils.substringAfter(jpql.toLowerCase(), " order by ");
				if(StringHelper.isBlank(order))
					jpql = Language.jpql(jpql ,OrderStringBuilder.getInstance().build(sortOrders));
				else
					LogHelper.logWarning(String.format("Sort orders <<%s>> will be ignored because query already contains order by clause",sortOrders), getClass());					
			}
			typedQuery = entityManager.createQuery(jpql, resultClass);
		}else {
			if(MapHelper.isEmpty(sortOrders)) {
				typedQuery = entityManager.createNamedQuery(query.getIdentifier(), resultClass);
			}else {
				String jpql = Replacer.getInstance().replace(new Replacer.Arguments().setQuery(query).setClause(Clause.ORDER_BY)
						.setReplacement(OrderStringBuilder.getInstance().build(new OrderStringBuilder.Order().add(sortOrders).setClauseNameAppendable(Boolean.FALSE))));
				typedQuery = entityManager.createQuery(jpql, resultClass);
			}
		}
		if(firstTupleIndex != null && firstTupleIndex >= 0)
			typedQuery.setFirstResult(firstTupleIndex);
		if(numberOfTuples != null && numberOfTuples >= 1)
			typedQuery.setMaxResults(numberOfTuples);
		
		if(MapHelper.isNotEmpty(parameters))
			for(Map.Entry<Object,Object> entry : parameters.entrySet()) {
				if(entry.getKey() instanceof String) {
					typedQuery.setParameter((String) entry.getKey(), entry.getValue());
				}
			}
		if(MapHelper.isNotEmpty(hints)) {
			for(Map.Entry<String,Object> entry : hints.entrySet())			
				typedQuery.setHint(entry.getKey(), entry.getValue());
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