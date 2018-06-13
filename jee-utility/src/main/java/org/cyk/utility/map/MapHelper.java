package org.cyk.utility.map;

import java.util.Map;

public interface MapHelper {

	Boolean isEmpty(Map<?, ?> map);
	Boolean isNotEmpty(Map<?, ?> map);
	@SuppressWarnings("rawtypes")
	void copy(Map source, Map destination, Object[] keys, Boolean removeNullValue);
	@SuppressWarnings("rawtypes")
	void copy(Map source, Map destination, Object[] keys);

}
