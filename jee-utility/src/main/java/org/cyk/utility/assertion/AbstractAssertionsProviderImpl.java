package org.cyk.utility.assertion;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.assertion.Assertion;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public abstract class AbstractAssertionsProviderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Collection<Assertion>> implements AssertionsProvider, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Object getFilter() {
		return getProperties().getFilter();
	}
	
	@Override
	public AssertionsProvider setFilter(Object filter) {
		getProperties().setFilter(filter);
		return this;
	}
	
}
