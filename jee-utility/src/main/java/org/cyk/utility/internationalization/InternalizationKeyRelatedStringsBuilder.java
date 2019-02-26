package org.cyk.utility.internationalization;

import java.util.Collection;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.string.Strings;

public interface InternalizationKeyRelatedStringsBuilder extends FunctionWithPropertiesAsInput<Collection<Strings>> {

	Object getValue();
	InternalizationKeyRelatedStringsBuilder setValue(Object value);
	
}
