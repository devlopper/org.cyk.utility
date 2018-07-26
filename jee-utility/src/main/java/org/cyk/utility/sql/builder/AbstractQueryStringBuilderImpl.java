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
	public QueryClauseStringBuilderFrom getFromClauseBuilder(Boolean injectIfNull) {
		QueryClauseStringBuilderFrom clause = getFromClauseBuilder();
		if(clause == null && Boolean.TRUE.equals(injectIfNull))
			setFromClauseBuilder(clause = ____inject____(QueryClauseStringBuilderFrom.class));
		return clause;
	}

	@Override
	public QueryStringBuilder setFromClauseBuilder(QueryClauseStringBuilderFrom builder) {
		getProperties().setFromPath(new Object[]{Properties.BUILDER,Properties.FROM}, builder);
		return this;
	}
	
	@Override
	public QueryStringBuilder from(Tuple tuple) {
		getFromClauseBuilder(Boolean.TRUE).addTuples(tuple);
		return this;
	}

	@Override
	public QueryClauseStringBuilderWhere getWhereClauseBuilder() {
		return (QueryClauseStringBuilderWhere) getProperties().getFromPath(Properties.BUILDER,Properties.WHERE);
	}
	
	@Override
	public QueryClauseStringBuilderWhere getWhereClauseBuilder(Boolean injectIfNull) {
		QueryClauseStringBuilderWhere clause = getWhereClauseBuilder();
		if(clause == null && Boolean.TRUE.equals(injectIfNull)){
			setWhereClauseBuilder(clause = ____inject____(QueryClauseStringBuilderWhere.class));
			clause.setParent(this);
		}
		return clause;
	}

	@Override
	public QueryStringBuilder setWhereClauseBuilder(QueryClauseStringBuilderWhere builder) {
		getProperties().setFromPath(new Object[]{Properties.BUILDER,Properties.WHERE}, builder);
		return this;
	}
	
	@Override
	public QueryStringBuilder where(QueryWherePredicateStringBuilder predicateBuilder) {
		getWhereClauseBuilder(Boolean.TRUE).setPredicateBuilder(predicateBuilder);
		return this;
	}
	
	@Override
	public <I extends QueryWherePredicateStringBuilder> I getWherePredicateBuilderAs(Class<I> aClass) {
		QueryClauseStringBuilderWhere where = getWhereClauseBuilder(Boolean.TRUE);
		QueryWherePredicateStringBuilder builder = where.getPredicateBuilder();
		if(builder == null)
			where.setPredicateBuilder(builder = ____inject____(aClass).setParent(where));
		return (I) builder;
	}
	
	@Override
	public QueryWherePredicateStringBuilderEqual getWherePredicateBuilderAsEqual() {
		return getWherePredicateBuilderAs(QueryWherePredicateStringBuilderEqual.class);
	}
	
}
