package org.cyk.utility.__kernel__.assertion;

import java.util.Collection;

import org.cyk.utility.__kernel__.function.Function;
import org.cyk.utility.__kernel__.properties.Properties;

public interface AssertionsProvider extends Function<Properties,Collection<Assertion>> {

	Object getFilter();
	AssertionsProvider setFilter(Object filter);
	
}
