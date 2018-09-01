package org.cyk.utility.assertion;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public class AssertionsProviderClassGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Class<AssertionsProvider>> implements AssertionsProviderClassGetter, Serializable {
	private static final long serialVersionUID = 1L;

	private Class<?> domainClass;
	
	@Override
	protected Class<AssertionsProvider> __execute__() throws Exception {
		Class<?> domainClass = getDomainClass();
		//Class<AssertionsProvider> result = __inj
		return super.__execute__();
	}
	
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
