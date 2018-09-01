package org.cyk.utility.assertion;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface AssertionsProviderClassGetter extends FunctionWithPropertiesAsInput<Class<AssertionsProvider>> {

	Class<?> getDomainClass();
	AssertionsProviderClassGetter setDomainClass(Class<?> aClass);
	
}
