package org.cyk.utility.enumeration;

import java.util.Collection;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

@SuppressWarnings("rawtypes")
public interface EnumCollectionGetter extends FunctionWithPropertiesAsInput<Collection> {

	EnumCollectionGetter setGetter(EnumGetter getter);
	EnumGetter getGetter();
	
	EnumCollectionGetter setNames(Collection<String> names);
	EnumCollectionGetter addNames(Collection<String> names);
	EnumCollectionGetter addNames(String...names);
	Collection<String> getNames();
	
}
