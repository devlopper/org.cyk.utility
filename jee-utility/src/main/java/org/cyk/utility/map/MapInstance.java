package org.cyk.utility.map;

import java.util.Collection;
import java.util.Map;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface MapInstance<KEY,VALUE> extends Objectable {

	Map<KEY,VALUE> getMap();
	
	MapInstance<KEY,VALUE> set(Object...keyValues);
	
	VALUE get(KEY key);
	VALUE get(KEY key,Boolean injectOrCreateIfNull);
	Collection<KEY> getKeys(Collection<VALUE> values);
	Collection<KEY> getKeys(VALUE...values);
	
	Collection<Map.Entry<KEY,VALUE>> getEntries();
	
	Boolean getIsOrdered();
	MapInstance<KEY,VALUE> setIsOrdered(Boolean isOrdered);
	
	Boolean getIsSequential();
	MapInstance<KEY,VALUE> setIsSequential(Boolean isSequential);
	
	Object getKeyValueSeparator();
	MapInstance<KEY,VALUE> setKeyValueSeparator(Object keyValueSeparator);
	
	Object getEntrySeparator();
	MapInstance<KEY,VALUE> setEntrySeparator(Object entrySeparator);
}
