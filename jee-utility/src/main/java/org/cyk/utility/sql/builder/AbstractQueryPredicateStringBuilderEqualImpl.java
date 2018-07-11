package org.cyk.utility.sql.builder;

import java.io.Serializable;

public abstract class AbstractQueryPredicateStringBuilderEqualImpl extends AbstractQueryPredicateStringBuilderImpl implements QueryPredicateStringBuilderEqual,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setFormat("%s=%s");
	}
	
}
