package org.cyk.utility.map;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

@Dependent
public class MapInstanceIntegerToStringImpl extends AbstractMapInstanceImpl<Integer, String> implements MapInstanceIntegerToString,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public MapInstanceIntegerToString set(Object... keyValues) {
		return (MapInstanceIntegerToString) super.set(keyValues);
	}
	
}
