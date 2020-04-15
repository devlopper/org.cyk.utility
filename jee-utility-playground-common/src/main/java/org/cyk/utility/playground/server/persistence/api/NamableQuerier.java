package org.cyk.utility.playground.server.persistence.api;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.computation.LogicalOperator;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.persistence.query.Querier;
import org.cyk.utility.__kernel__.persistence.query.QueryArgumentHelper;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutor;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutorArguments;
import org.cyk.utility.__kernel__.persistence.query.QueryIdentifierBuilder;
import org.cyk.utility.__kernel__.persistence.query.QueryStringHelper;
import org.cyk.utility.__kernel__.persistence.query.filter.Filter;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.playground.server.persistence.entities.Namable;

public interface NamableQuerier extends Querier {

	Collection<Namable> readMany(QueryExecutorArguments arguments);
	Long count(QueryExecutorArguments arguments);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements NamableQuerier,Serializable {
		@Override
		public Collection<Namable> readMany(QueryExecutorArguments arguments) {
			if(arguments != null && arguments.getQuery() != null && QUERY_IDENTIFIER_READ_VIEW_01.equals(arguments.getQuery().getIdentifier())) {
				Filter filter = arguments.getFilter();
				if(filter == null)
					filter = new Filter();
				List<String> tokens = QueryArgumentHelper.getLikes(filter.getFieldValue(Namable.FIELD_NAME), 5);
				
				Filter newFilter = new Filter();
				newFilter.addField("code", QueryArgumentHelper.getLike(filter.getFieldValue("code")));
				newFilter.addField(Namable.FIELD_NAME, tokens.get(0));
				newFilter.addField("name1", tokens.get(0));
				newFilter.addField("name2", tokens.get(1));
				newFilter.addField("name3", tokens.get(2));
				newFilter.addField("name4", tokens.get(3));
				
				arguments.setFilter(newFilter);					
			}
			return QueryExecutor.getInstance().executeReadMany(Namable.class, arguments);
		}
		
		@Override
		public Long count(QueryExecutorArguments arguments) {
			if(arguments != null && arguments.getQuery() != null && QUERY_IDENTIFIER_COUNT_VIEW_01.equals(arguments.getQuery().getIdentifier())) {
				Filter filter = arguments.getFilter();
				if(filter == null)
					filter = new Filter();
				List<String> tokens = QueryArgumentHelper.getLikes(filter.getFieldValue(Namable.FIELD_NAME), 5);
				Filter newFilter = new Filter();
				newFilter.addField("code", QueryArgumentHelper.getLike(filter.getFieldValue("code")));
				newFilter.addField(Namable.FIELD_NAME, tokens.get(0));
				newFilter.addField("name1", tokens.get(0));
				newFilter.addField("name2", tokens.get(1));
				newFilter.addField("name3", tokens.get(2));
				newFilter.addField("name4", tokens.get(3));
				
				arguments.setFilter(newFilter);	
			}
			return QueryExecutor.getInstance().executeCount(arguments);
		}
	}
	
	/**/
	
	String QUERY_NAME_READ_VIEW_01 = "read.view.01";
	String QUERY_NAME_COUNT_VIEW_01 = "count.view.01";
	
	String QUERY_IDENTIFIER_READ_VIEW_01 = QueryIdentifierBuilder.getInstance().build(Namable.class, QUERY_NAME_READ_VIEW_01);
	String QUERY_IDENTIFIER_COUNT_VIEW_01 = QueryIdentifierBuilder.getInstance().build(Namable.class, QUERY_NAME_COUNT_VIEW_01);
	
	String QUERY_VALUE_READ_VIEW_01 = "SELECT namable FROM Namable namable "
			+ "WHERE "
			+ QueryStringHelper.formatTupleFieldLike("namable", "code") + " AND (" + QueryStringHelper.formatTupleFieldLikeOrTokens("namable", "name","name",4,LogicalOperator.AND)+")"
			+ "ORDER BY namable.code ASC";
	
	/**/
	
	static NamableQuerier getInstance() {
		return Helper.getInstance(NamableQuerier.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}