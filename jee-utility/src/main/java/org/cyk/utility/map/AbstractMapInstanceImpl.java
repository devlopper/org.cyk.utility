package org.cyk.utility.map;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.array.ArrayHelper;

public class AbstractMapInstanceImpl<KEY,VALUE> extends AbstractObject implements MapInstance<KEY,VALUE>,Serializable {
	private static final long serialVersionUID = 1L;

	private Map<KEY,VALUE> __map__;
	
	@Override
	public MapInstance<KEY,VALUE> set(Object... keyValues) {
		if(__inject__(ArrayHelper.class).isNotEmpty(keyValues)) {
			__map__ = new HashMap<>();
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
	public String toString() {
		return __map__ == null ? super.toString() : __map__.toString();
	}
}
