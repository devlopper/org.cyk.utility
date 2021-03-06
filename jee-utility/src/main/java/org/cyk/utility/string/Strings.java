package org.cyk.utility.string;

import java.util.Collection;

import org.cyk.utility.collection.CollectionInstance;

public interface Strings extends CollectionInstance<String> {

	@Override Strings add(Collection<String> elements);
	@Override Strings add(String... elements);
	
	Strings addWithPrefix(String prefix,Collection<String> elements);
	Strings addWithPrefix(String prefix,String...elements);
}
