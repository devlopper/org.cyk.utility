package org.cyk.utility.__kernel__.map;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.object.AbstractObject;

public class MapInstance<KEY,VALUE> extends AbstractObject implements Serializable {

	protected Map<KEY,VALUE> map;
	
	public VALUE read(KEY key) {
		if(map == null)
			return null;
		return map.get(key);
	}
	
	public MapInstance<KEY,VALUE> write(KEY key,VALUE value) {
		if(map == null)
			map = new HashMap<>();
		map.put(key, value);
		return this;
	}
}