package org.cyk.utility.sql.jpql.builder;

import java.io.Serializable;

import org.cyk.utility.sql.builder.AbstractQueryParameterStringBuilderImpl;
import org.cyk.utility.sql.jpql.Jpql;

@Jpql
public class QueryParameterStringBuilderJpqlImpl extends AbstractQueryParameterStringBuilderImpl
		implements QueryParameterStringBuilderJpql, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setFormat(":%s");
	}
	
}
