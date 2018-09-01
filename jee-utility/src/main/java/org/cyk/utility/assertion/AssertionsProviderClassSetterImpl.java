package org.cyk.utility.assertion;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;

public class AssertionsProviderClassSetterImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements AssertionsProviderClassSetter, Serializable {
	private static final long serialVersionUID = 1L;

	private static final Map<Class<?>,Class<?>> MAP = new HashMap<>();
	
	private Class<?> domainClass;
	private Class<? extends AssertionsProvider> providerClass;
	
	@Override
	protected void ____execute____() throws Exception {
		MAP.put(getDomainClass(), getProviderClass());
	}
	
	@Override
	public AssertionsProviderClassSetter setDomainClass(Class<?> domainClass) {
		this.domainClass = domainClass;
		return this;
	}
	
	@Override
	public Class<?> getDomainClass() {
		return domainClass;
	}
	
	public AssertionsProviderClassSetter setProviderClass(Class<? extends AssertionsProvider> providerClass) {
		this.providerClass = providerClass;
		return this;
	}
	
	@Override
	public Class<? extends AssertionsProvider> getProviderClass() {
		return providerClass;
	}
}
