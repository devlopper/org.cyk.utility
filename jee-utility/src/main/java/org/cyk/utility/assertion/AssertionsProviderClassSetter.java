package org.cyk.utility.assertion;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndVoidAsOutput;

public interface AssertionsProviderClassSetter extends FunctionWithPropertiesAsInputAndVoidAsOutput {

	Class<?> getDomainClass();
	AssertionsProviderClassSetter setDomainClass(Class<?> aClass);
	
	Class<? extends AssertionsProvider> getProviderClass();
	AssertionsProviderClassSetter setProviderClass(Class<? extends AssertionsProvider> aClass);
}
