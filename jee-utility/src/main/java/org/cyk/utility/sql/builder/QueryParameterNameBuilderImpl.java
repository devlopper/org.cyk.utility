package org.cyk.utility.sql.builder;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

@Dependent
public class QueryParameterNameBuilderImpl extends AbstractQueryParameterNameBuilderImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setFormat("@%s");
	}
	
}
