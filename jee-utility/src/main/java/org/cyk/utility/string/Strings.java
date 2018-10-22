package org.cyk.utility.string;

import java.util.Collection;

import org.cyk.utility.collection.CollectionInstance;

public interface Strings extends CollectionInstance<String> {

	@Override Strings add(Collection<String> collection);
	@Override Strings add(String... elements);
}
