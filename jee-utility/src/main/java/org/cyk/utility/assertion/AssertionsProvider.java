package org.cyk.utility.assertion;

import java.util.Collection;

import org.cyk.utility.__kernel__.assertion.Assertion;
import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface AssertionsProvider extends FunctionWithPropertiesAsInput<Collection<Assertion>> {

	Object getFilter();
	AssertionsProvider setFilter(Object filter);
	
}
