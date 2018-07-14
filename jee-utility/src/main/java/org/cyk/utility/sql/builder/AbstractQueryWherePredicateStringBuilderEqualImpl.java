package org.cyk.utility.sql.builder;

import java.io.Serializable;

public abstract class AbstractQueryWherePredicateStringBuilderEqualImpl extends AbstractQueryWherePredicateStringBuilderImpl implements QueryWherePredicateStringBuilderEqual,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setOperator("=");
	}
	
}
