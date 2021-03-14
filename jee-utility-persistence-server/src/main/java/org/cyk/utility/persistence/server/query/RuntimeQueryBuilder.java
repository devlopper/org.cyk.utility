package org.cyk.utility.persistence.server.query;

import java.io.Serializable;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.persistence.query.Query;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.server.query.string.RuntimeQueryStringBuilder;

public interface RuntimeQueryBuilder {
	
	Query build(QueryExecutorArguments arguments);	
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements RuntimeQueryBuilder,Serializable {
		private static final long serialVersionUID = 1L;		
		
		@Override
		public Query build(QueryExecutorArguments arguments) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("arguments", arguments);
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("query", arguments.getQuery());
			
			return __build__(arguments);
		}
		
		protected Query __build__(QueryExecutorArguments arguments) {
			Query query = new Query();
			//InstanceCopier.getInstance().copy(arguments.getQuery(), query, List.of(Query.FIELD_IDENTIFIER,Query.FIELD_RESULT_CLASS,Query.FIELD_TUPLE_CLASS
			//		,Query.FIELD_TYPE,Query.FIELD_VALUE,Query.FIELD_INTERMEDIATE_RESULT_CLASS,Query.FIELD_TUPLE_FIELDS_NAMES_INDEXES));
			
			query.setIdentifier(arguments.getQuery().getIdentifier());
			query.setIntermediateResultClass(arguments.getQuery().getIntermediateResultClass());
			query.setQueryDerivedFromQuery(arguments.getQuery().getQueryDerivedFromQuery());
			query.setResultClass(arguments.getQuery().getResultClass());
			query.setTupleClass(arguments.getQuery().getTupleClass());
			query.setTupleFieldsNamesIndexes(arguments.getQuery().getTupleFieldsNamesIndexes());
			query.setType(arguments.getQuery().getType());
			query.setValue(arguments.getQuery().getValue());
			
			query.setValue(RuntimeQueryStringBuilder.getInstance().build(arguments));
			query.setIdentifier(null);//Disable named query mapping
			
			arguments.setRuntimeQuery(query);
			
			return query;
		}
	}
	
	/**/
	
	/**/
	
	static RuntimeQueryBuilder getInstance() {
		return Helper.getInstance(RuntimeQueryBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}