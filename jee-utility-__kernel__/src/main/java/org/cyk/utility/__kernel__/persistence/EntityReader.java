package org.cyk.utility.__kernel__.persistence;

import java.util.Collection;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.persistence.query.Query;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutor;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutor.Arguments;
import org.cyk.utility.__kernel__.persistence.query.QueryHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface EntityReader {

	default <T> Collection<T> read(Class<T> klass,Arguments arguments) {
		if(klass == null)
			return null;
		if(arguments == null)
			arguments = new Arguments();
		if(arguments.getQuery() == null) {
			String queryIdentifier = QueryHelper.getIdentifierReadAll(klass);
			arguments.setQuery(QueryHelper.getQueries().getByIdentifier(queryIdentifier));
			if(arguments.getQuery() == null) {
				arguments.setQuery(Query.build(Query.FIELD_IDENTIFIER,queryIdentifier,Query.FIELD_TUPLE_CLASS,klass,Query.FIELD_VALUE
						,String.format("SELECT tuple FROM %s tuple", klass.getSimpleName())));
				QueryHelper.addQueries(arguments.getQuery());
			}			
		}
		/*
		if(arguments.getQuery().getIdentifier() == null)
			arguments.getQuery().setIdentifier(QueryHelper.getIdentifierReadAll(klass));
		
		if(query == null) {
			arguments.setQuery(new Query());
			
			String queryIdentifier = arguments == null || arguments.getQuery() == null ? null : StringHelper.get(arguments.getQuery().getIdentifier());
			if(StringHelper.isBlank(queryIdentifier) && arguments!=null && arguments.getQuery()!=null && StringHelper.isBlank(arguments.getQuery().getValue()))
				queryIdentifier = QueryHelper.getIdentifierReadAll(klass);
			Query registeredQuery = QueryHelper.getQueries().getByIdentifier(queryIdentifier);
			if(query == null) {
				//register this query for the next call
				query = Query.build(Query.FIELD_IDENTIFIER,queryIdentifier,Query.FIELD_TUPLE_CLASS,klass
						,Query.FIELD_VALUE,String.format("SELECT tuple FROM %s tuple", klass.getSimpleName()));
				
				QueryHelper.addQueries(query);
			}	
			
		}
		*/
		return DependencyInjection.inject(QueryExecutor.class).executeReadMany(klass, arguments);
	}
	
	default <T> Collection<T> read(Class<T> klass) {
		if(klass == null)
			return null;
		return read(klass,null);
	}
	
	/**/
	
	static EntityReader getInstance() {
		return Helper.getInstance(EntityReader.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
	
}
