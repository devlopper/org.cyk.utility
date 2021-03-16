package org.cyk.utility.persistence.server.query;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.persistence.query.Query;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.query.QueryIdentifierGetter;
import org.cyk.utility.persistence.query.QueryName;
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
			if(!Boolean.TRUE.equals(isBuildable(arguments)))
				return arguments.getQuery();
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
			
			setValue(query, RuntimeQueryStringBuilder.getInstance().build(arguments));
			
			query.setIdentifier(null);//Disable named query mapping			
			arguments.setRuntimeQuery(query);
			
			return query;
		}
		
		protected void setValue(Query query,String value) {
			query.setValue(value);
		}
		
		protected Boolean isBuildable(QueryExecutorArguments arguments) {
			if(BUILDABLES.contains(arguments.getQuery().getIdentifier()))
				return Boolean.TRUE;
			if(arguments.getQuery().getIdentifier().equals(QueryIdentifierGetter.getInstance().get(arguments.getQuery().getTupleClass(), QueryName.READ_DYNAMIC)))
				return Boolean.TRUE;
			if(arguments.getQuery().getIdentifier().equals(QueryIdentifierGetter.getInstance().get(arguments.getQuery().getTupleClass(), QueryName.COUNT_DYNAMIC)))
				return Boolean.TRUE;
			return Boolean.FALSE;
		}
	}
	
	/**/
	
	/**/
	
	static RuntimeQueryBuilder getInstance() {
		return Helper.getInstance(RuntimeQueryBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
	
	Collection<String> BUILDABLES = new HashSet<>();
}