package org.cyk.utility.sql.builder;

import java.io.Serializable;

public class QueryWherePredicateStringBuilderImpl extends AbstractQueryWherePredicateStringBuilderImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setFormat("%s=%s");
	}
	
}
