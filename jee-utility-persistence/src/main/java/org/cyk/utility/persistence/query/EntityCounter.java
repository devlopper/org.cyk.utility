package org.cyk.utility.persistence.query;

import java.io.Serializable;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.value.Value;

public interface EntityCounter {

	Long count(Class<?> tupleClass,QueryExecutorArguments arguments);	
	Long count(Class<?> tupleClass,String queryIdentifier,Object...filterFieldsValues);
	Long count(Class<?> tupleClass);		
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements EntityCounter,Serializable {
		private static final long serialVersionUID = 1L;		
		
		@Override
		public Long count(Class<?> tupleClass,QueryExecutorArguments arguments) {
			validatePreconditions(tupleClass);
			if(arguments == null)
				arguments = new QueryExecutorArguments();
			if(arguments.getQuery() == null) {
				String queryIdentifier = QueryHelper.getIdentifierCountAll(tupleClass);
				arguments.setQuery(QueryGetter.getInstance().get(queryIdentifier));
				if(arguments.getQuery() == null)
					arguments.setQuery(QueryGetter.getInstance().getByCount(tupleClass, QueryName.COUNT.getValue(),String.format("SELECT COUNT(tuple) FROM %s tuple",tupleClass.getSimpleName())));
			}
			return QueryExecutor.getInstance().executeCount(arguments);
		}
		
		@Override
		public Long count(Class<?> tupleClass,String queryIdentifier,Object...filterFieldsValues) {
			validatePreconditions(tupleClass);
			return count(tupleClass,new QueryExecutorArguments().setQueryFromIdentifier(queryIdentifier).addFilterFieldsValues(filterFieldsValues));
		}
		
		@Override
		public Long count(Class<?> tupleClass) {
			validatePreconditions(tupleClass);
			return count(tupleClass,null);
		}
		
		/**/
		
		protected static void validatePreconditions(Class<?> tupleClass) {
			if(tupleClass == null)
				throw new RuntimeException("Tuple class to be count is required");
		}
	}
	
	/**/
	
	static EntityCounter getInstance() {
		return Helper.getInstance(EntityCounter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}