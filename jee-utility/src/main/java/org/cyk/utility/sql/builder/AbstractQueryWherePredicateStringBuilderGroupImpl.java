package org.cyk.utility.sql.builder;

import java.io.Serializable;

public abstract class AbstractQueryWherePredicateStringBuilderGroupImpl extends AbstractQueryWherePredicateStringBuilderImpl implements QueryWherePredicateStringBuilderGroup,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public QueryWherePredicateStringBuilderGroup addOperandBuilderByAttribute(String attributeName, Tuple tuple) {
		addChild(____inject____(QueryWherePredicateStringBuilderEqual.class).addOperandBuilderByAttribute(attributeName, tuple));
		return this;
	}
	
	@Override
	public QueryWherePredicateStringBuilderGroup addChild(Object... child) {
		return (QueryWherePredicateStringBuilderGroup) super.addChild(child);
	}
	
}
