package org.cyk.utility.internationalization;

import java.util.Collection;

import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.function.FunctionWithPropertiesAsInput;

@Deprecated
public interface InternalizationKeyRelatedStringsBuilder extends FunctionWithPropertiesAsInput<Collection<Strings>> {

	Object getKey();
	InternalizationKeyRelatedStringsBuilder setKey(Object key);
	
}
