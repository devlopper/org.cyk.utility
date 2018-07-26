package org.cyk.utility.sql.builder;

import java.io.Serializable;

public abstract class AbstractQueryWherePredicateStringBuilderImpl extends AbstractQueryPredicateStringBuilderImpl implements QueryWherePredicateStringBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public QueryWherePredicateStringBuilder setParent(Object parent) {
		return (QueryWherePredicateStringBuilder) super.setParent(parent);
	}
	
	@Override
	public QueryClauseStringBuilderWhere getParentAsWhereClause() {
		return (QueryClauseStringBuilderWhere) getParent();
	}
	
}
