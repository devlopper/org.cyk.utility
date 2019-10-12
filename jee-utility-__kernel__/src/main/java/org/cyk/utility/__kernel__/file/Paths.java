package org.cyk.utility.__kernel__.file;

import java.nio.file.Path;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionInstance;
import org.cyk.utility.__kernel__.string.Strings;

public interface Paths extends CollectionInstance<Path> {

	Paths removeByUniformResourceIdentifiers(Collection<String> uniformResourceIdentifiers);
	Paths removeByUniformResourceIdentifiers(String...uniformResourceIdentifiers);
	Paths removeByUniformResourceIdentifiers(Strings uniformResourceIdentifiers);
	
}
