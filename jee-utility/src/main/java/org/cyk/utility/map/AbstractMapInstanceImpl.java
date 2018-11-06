package org.cyk.utility.map;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.array.ArrayHelper;

public class AbstractMapInstanceImpl<KEY,VALUE> extends AbstractObject implements MapInstance<KEY,VALUE>,Serializable {
	private static final long serialVersionUID = 1L;

	private Map<KEY,VALUE> __map__;
	private Boolean isOrdered;
	
	@Override
	public Map<KEY, VALUE> getMap() {
		return __map__;
	}
	
	@Override
	public MapInstance<KEY,VALUE> set(Object... keyValues) {
		if(__inject__(ArrayHelper.class).isNotEmpty(keyValues)) {
			if(__map__ == null)
				__map__ = Boolean.TRUE.equals(getIsOrdered()) ? new TreeMap<>() : new HashMap<>();
			for(Integer index = 0 ; index < keyValues.length ; index = index + 2) {
				__map__.put((KEY)keyValues[index], (VALUE)keyValues[index+1]);
			}
		}
		return this;
	}

	@Override
	public VALUE get(KEY key) {
		return __map__ == null ? null : __map__.get(key);
	}
	
	@Override
	public Collection<Entry<KEY, VALUE>> getEntries() {
		return __map__ == null ? null : __map__.entrySet();
	}
	
	@Override
	public Boolean getIsOrdered() {
		return isOrdered;
	}
	@Override
	public MapInstance<KEY, VALUE> setIsOrdered(Boolean isOrdered) {
		this.isOrdered = isOrdered;
		return this;
	}
	
	@Override
	public String toString() {
		return __map__ == null ? super.toString() : __map__.toString();
	}
}
