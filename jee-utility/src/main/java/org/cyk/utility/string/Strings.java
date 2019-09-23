package org.cyk.utility.string;

import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionInstance;

public interface Strings extends CollectionInstance<String> {

	@Override Strings add(Collection<String> elements);
	@Override Strings add(String... elements);
	
	Strings addWithPrefix(String prefix,Collection<String> elements);
	Strings addWithPrefix(String prefix,String...elements);
	
	String concatenate(Object separator);
	String concatenate();
}
