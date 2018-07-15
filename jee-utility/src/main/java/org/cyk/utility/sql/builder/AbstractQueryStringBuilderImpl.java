package org.cyk.utility.sql.builder;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl;

public abstract class AbstractQueryStringBuilderImpl extends AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl implements QueryStringBuilder, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public QueryClauseStringBuilderFrom getFromClauseBuilder() {
		return (QueryClauseStringBuilderFrom) getProperties().getFromPath(Properties.BUILDER,Properties.FROM);
	}

	@Override
	public QueryStringBuilder setFromClauseBuilder(QueryClauseStringBuilderFrom builder) {
		getProperties().setFromPath(new Object[]{Properties.BUILDER,Properties.FROM}, builder);
		return this;
	}
	
	@Override
	public QueryStringBuilder from(Tuple tuple) {
		getFromClauseBuilder().addTuples(tuple);
		return this;
	}

	@Override
	public QueryClauseStringBuilderWhere getWhereClauseBuilder() {
		return (QueryClauseStringBuilderWhere) getProperties().getFromPath(Properties.BUILDER,Properties.WHERE);
	}

	@Override
	public QueryStringBuilder setWhereClauseBuilder(QueryClauseStringBuilderWhere builder) {
		getProperties().setFromPath(new Object[]{Properties.BUILDER,Properties.WHERE}, builder);
		return this;
	}
	
	@Override
	public QueryStringBuilder where(QueryWherePredicateStringBuilder predicateBuilder) {
		getWhereClauseBuilder().setPredicateBuilder(predicateBuilder);
		return this;
	}
	
}
