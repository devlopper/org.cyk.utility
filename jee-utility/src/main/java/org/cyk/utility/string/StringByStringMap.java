package org.cyk.utility.string;

import org.cyk.utility.map.MapInstance;

public interface StringByStringMap extends MapInstance<String,String> {

	@Override StringByStringMap setIsOrdered(Boolean isOrdered);
	@Override StringByStringMap setIsSequential(Boolean isSequential);
	@Override StringByStringMap setEntrySeparator(Object entrySeparator);
	@Override StringByStringMap setKeyValueSeparator(Object keyValueSeparator);
}
