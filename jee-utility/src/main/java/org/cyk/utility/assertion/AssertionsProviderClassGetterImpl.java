package org.cyk.utility.assertion;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public class AssertionsProviderClassGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Class<AssertionsProvider>> implements AssertionsProviderClassGetter, Serializable {
	private static final long serialVersionUID = 1L;

	private Class<?> domainClass;
	
	
	
	@Override
	public AssertionsProviderClassGetter setDomainClass(Class<?> domainClass) {
		this.domainClass = domainClass;
		return this;
	}
	
	@Override
	public Class<?> getDomainClass() {
		return domainClass;
	}
}
