package org.cyk.utility.server.persistence.query.builder;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

@Dependent @Deprecated
public class PersistenceQueryStringBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<String>implements PersistenceQueryStringBuilder, Serializable {
	private static final long serialVersionUID = 1L;

}
