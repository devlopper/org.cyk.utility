package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.value.Value;

public interface EntityCounter {

	<T> Long count(Class<T> tupleClass,QueryExecutorArguments arguments);
	
	default <T> Long count(Class<T> tupleClass) {
		if(tupleClass == null)
			throw new RuntimeException("Tuple class is required");
		return count(tupleClass,null);
	}
	
	default <T> Long count(Class<T> tupleClass,String queryIdentifier,Object...filterFieldsValues) {
		if(tupleClass == null)
			throw new RuntimeException("Tuple class is required");
		return count(tupleClass,new QueryExecutorArguments().setQueryFromIdentifier(queryIdentifier).addFilterFieldsValues(filterFieldsValues));
	}
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements EntityCounter,Serializable {
		private static final long serialVersionUID = 1L;		
		
		@Override
		public <T> Long count(Class<T> tupleClass,QueryExecutorArguments arguments) {
			if(tupleClass == null)
				throw new RuntimeException("Tuple class is required");
			if(arguments == null)
				arguments = new QueryExecutorArguments();
			if(arguments.getQuery() == null) {
				String queryIdentifier = QueryHelper.getIdentifierCountAll(tupleClass);
				arguments.setQuery(QueryHelper.getQueries().getByIdentifier(queryIdentifier));
				if(arguments.getQuery() == null)
					arguments.setQuery(QueryGetter.getInstance().getByCount(tupleClass, QueryName.COUNT.getValue(),String.format("SELECT COUNT(tuple) FROM %s tuple",tupleClass.getSimpleName())));
			}
			return QueryExecutor.getInstance().executeCount(arguments);
		}
	}
	
	/**/
	
	static EntityCounter getInstance() {
		return Helper.getInstance(EntityCounter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}