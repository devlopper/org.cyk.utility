package org.cyk.utility.map;

import java.util.Map;

import org.cyk.utility.helper.Helper;

public interface MapHelper extends Helper {

	Boolean isEmpty(Map<?, ?> map);
	Boolean isEmpty(MapInstance<?, ?> mapInstance);
	Boolean isNotEmpty(Map<?, ?> map);
	Boolean isNotEmpty(MapInstance<?, ?> mapInstance);
	@SuppressWarnings("rawtypes")
	void copy(Map source, Map destination, Object[] keys, Boolean removeNullValue);
	@SuppressWarnings("rawtypes")
	void copy(Map source, Map destination, Object[] keys);

	Map<Object,Object> instanciate(Object...objects);
	Map<String,String> instanciateKeyAsStringValueAsString(Object...objects);
	Map<String,Object> instanciateKeyAsStringValueAsObject(Object...objects);
	
	<T> T get(@SuppressWarnings("rawtypes") Map map,Class<T> klass,Object key);
}
