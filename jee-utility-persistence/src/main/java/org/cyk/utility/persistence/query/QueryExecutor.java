package org.cyk.utility.persistence.query;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.value.Value;

public interface QueryExecutor {

	<T> Collection<T> executeReadMany(Class<T> resultClass,QueryExecutorArguments arguments);
	
	default <T> Collection<T> executeReadMany(Class<T> resultClass) {
		return executeReadMany(resultClass, new QueryExecutorArguments().setQuery(QueryGetter.getInstance().getBySelect(resultClass)));
	}
	
	default <T> Collection<T> executeReadMany(Class<T> resultClass,String queryIdentifier,Object...filterFieldsValues) {
		if(resultClass == null)
			throw new RuntimeException("Result class is required");
		return executeReadMany(resultClass, new QueryExecutorArguments().setQueryFromIdentifier(queryIdentifier).addFilterFieldsValues(filterFieldsValues));
	}
	
	default <T> T executeReadOne(Class<T> resultClass,QueryExecutorArguments arguments) {
		Collection<T> collection = executeReadMany(resultClass, arguments);
		if(CollectionHelper.getSize(collection) > 1)
			throw new RuntimeException(String.format("read.one : too much result found after executing query %s with filter %s",arguments.getQuery(),arguments.get__parameters__()));
		return CollectionHelper.getFirst(collection);
	}
	
	Long executeCount(QueryExecutorArguments arguments);
	
	default Long executeCount(Class<?> tupleClass) {
		return executeCount(new QueryExecutorArguments().setQuery(QueryGetter.getInstance().getByCount(tupleClass)));
	}
	
	default Long executeCount(String queryIdentifier,Object...filterFieldsValues) {
		return executeCount(new QueryExecutorArguments().setQueryFromIdentifier(queryIdentifier).addFilterFieldsValues(filterFieldsValues));
	}
	
	Integer executeUpdateOrDelete(QueryExecutorArguments arguments);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements QueryExecutor,Serializable {
		
	}
	
	/**/
	
	static QueryExecutor getInstance() {
		return Helper.getInstance(QueryExecutor.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}