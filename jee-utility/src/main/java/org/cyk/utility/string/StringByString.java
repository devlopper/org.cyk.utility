package org.cyk.utility.string;

import org.cyk.utility.map.MapInstance;

public interface StringByString extends MapInstance<String,String> {

	@Override StringByString setEntrySeparator(Object entrySeparator);
	@Override StringByString setKeyValueSeparator(Object keyValueSeparator);
}
