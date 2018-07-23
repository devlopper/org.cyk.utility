package org.cyk.utility.map;

import java.util.Map;

import org.cyk.utility.helper.Helper;

public interface MapHelper extends Helper {

	Boolean isEmpty(Map<?, ?> map);
	Boolean isNotEmpty(Map<?, ?> map);
	@SuppressWarnings("rawtypes")
	void copy(Map source, Map destination, Object[] keys, Boolean removeNullValue);
	@SuppressWarnings("rawtypes")
	void copy(Map source, Map destination, Object[] keys);

	Map<Object,Object> instanciate(Object...objects);
}
