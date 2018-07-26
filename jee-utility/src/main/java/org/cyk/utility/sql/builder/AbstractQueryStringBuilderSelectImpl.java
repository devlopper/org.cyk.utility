package org.cyk.utility.sql.builder;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;

public abstract class AbstractQueryStringBuilderSelectImpl extends AbstractQueryStringBuilderImpl implements QueryStringBuilderSelect, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __execute__() throws Exception {
		QueryClauseStringBuilderSelect select = getSelectClauseBuilder();
		if(select!=null)
			addChild(select);
		
		QueryClauseStringBuilderFrom from = getFromClauseBuilder();
		if(from!=null)
			addChild(from);
		
		QueryClauseStringBuilderWhere where = getWhereClauseBuilder();
		if(where!=null && where.getPredicateBuilder()!=null)
			addChild(where);
		
		return super.__execute__();
	}
	/*
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		__listenPostConstructSetClausesBuilders__();
	}
	
	protected void __listenPostConstructSetClausesBuilders__() {
		setFromClauseBuilder(__inject__(QueryClauseStringBuilderFrom.class));
		setWhereClauseBuilder(__inject__(QueryClauseStringBuilderWhere.class));
		setSelectClauseBuilder(__inject__(QueryClauseStringBuilderSelect.class));
	}*/
	
	@Override
	public QueryClauseStringBuilderSelect getSelectClauseBuilder() {
		return (QueryClauseStringBuilderSelect) getProperties().getFromPath(Properties.BUILDER,Properties.SELECT);
	}
	
	@Override
	public QueryClauseStringBuilderSelect getSelectClauseBuilder(Boolean injectIfNull) {
		QueryClauseStringBuilderSelect clause = getSelectClauseBuilder();
		if(clause == null && Boolean.TRUE.equals(injectIfNull)){
			setSelectClauseBuilder(clause = ____inject____(QueryClauseStringBuilderSelect.class));
		}
		return clause;
	}

	@Override
	public QueryStringBuilderSelect setSelectClauseBuilder(QueryClauseStringBuilderSelect builder) {
		getProperties().setFromPath(new Object[]{Properties.BUILDER,Properties.SELECT}, builder);
		return this;
	}

	@Override
	public QueryStringBuilderSelect select(Tuple tuple) {
		getSelectClauseBuilder(Boolean.TRUE).addTuples(tuple);
		return this;
	}

	@Override
	public QueryStringBuilderSelect from(Tuple tuple) {
		getSelectClauseBuilder(Boolean.TRUE).addTuples(tuple);
		return (QueryStringBuilderSelect) super.from(tuple);
	}
	
	@Override
	public QueryStringBuilderSelect where(QueryWherePredicateStringBuilder predicateBuilder) {
		return (QueryStringBuilderSelect) super.where(predicateBuilder);
	}
	
}
