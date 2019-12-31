package org.cyk.utility.__kernel__.map;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.json.bind.JsonbBuilder;

import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.string.StringHelper;

public interface MapHelper {

	static Boolean isEmpty(Map<?,?> map) {
		if(map == null)
			return Boolean.TRUE;
		return map.isEmpty();
	}
	
	static Boolean isNotEmpty(Map<?,?> map) {
		if(map == null)
			return Boolean.FALSE;
		return !isEmpty(map);
	}
	
	static <K,V> Collection<K> getKeys(Map<K,V> map,Collection<Class<?>> keysTypesInstanceOfClasses,Collection<Class<?>> keysInstanceOfClasses,Collection<V> values) {
		if(map == null || map.isEmpty())
			return null;
		if(keysTypesInstanceOfClasses != null && keysTypesInstanceOfClasses.isEmpty())
			keysTypesInstanceOfClasses = null;
		if(keysInstanceOfClasses != null && keysInstanceOfClasses.isEmpty())
			keysInstanceOfClasses = null;
		if(values != null && values.isEmpty())
			values = null;
		Collection<K> keys = null;
		for(Map.Entry<K, V> entry : map.entrySet()) {
			if(entry.getKey() != null) {
				if(keysTypesInstanceOfClasses != null && !ClassHelper.isInstanceOfOne(entry.getKey().getClass(), keysTypesInstanceOfClasses))
					continue;
				if(keysInstanceOfClasses != null && (!(entry.getKey() instanceof Class<?>) || entry.getKey() instanceof Class<?> && !ClassHelper.isInstanceOfOne((Class<?>) entry.getKey(), keysInstanceOfClasses)))
					continue;
			}
			if(values!=null && !values.contains(entry.getValue()))
				continue;	
			if(keys == null)
				keys = new ArrayList<>();
			keys.add(entry.getKey());
		}
		return keys;
	}
	
	static <K,V> Collection<K> getKeysWhereKeysAreClasses(Map<K,V> map,Collection<Class<?>> keysTypesInstanceOfClasses,Collection<V> values) {
		if(map == null || map.isEmpty())
			return null;
		return getKeys(map, keysTypesInstanceOfClasses, List.of(Class.class), values);
	}
	
	static <K,V> Collection<K> getKeysWhereKeysAreClasses(Map<K,V> map,Collection<Class<?>> keysTypesInstanceOfClasses,V value) {
		if(map == null || map.isEmpty())
			return null;
		return getKeys(map, keysTypesInstanceOfClasses, List.of(Class.class), value == null ? null : List.of(value));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	static void copyMap(Map source, Map destination, Object[] keys, Boolean removeNullValue) {
		if(source!=null && destination!=null && keys!=null && keys.length>0)
			for(Object key : keys){
				Object value = source.get(key);
				if(value == null && Boolean.TRUE.equals(removeNullValue))
					destination.remove(key);
				else
					destination.put(key, value);
			}
	}

	@SuppressWarnings({ "rawtypes" })
	static void copyMap(Map source, Map destination, Object[] keys) {
		copyMap(source, destination, keys, Boolean.TRUE);
	}
	
	@SuppressWarnings("unchecked")
	static void copyMapNonNullKeys(@SuppressWarnings("rawtypes") Map source, @SuppressWarnings("rawtypes") Map destination) {
		if(source!=null && destination!=null) {
			for(Object index : source.entrySet()) {
				@SuppressWarnings("rawtypes")
				Map.Entry entry = (Entry) index;
				if(entry.getKey()!=null)
					destination.put(entry.getKey(), entry.getValue());
			}
		}
	}

	@SuppressWarnings("unchecked")
	static Map<String,?> instantiateFromJson(String json,Class<?> mapClass) {
		if(StringHelper.isBlank(json))
			return null;
		Type mapType = null;
		if(mapClass == null) {
			mapType = new LinkedHashMap<String,Object>() {private static final long serialVersionUID = 1L;}.getClass().getGenericSuperclass();
		}
		return (Map<String,?>) JsonbBuilder.create().fromJson(json, mapType);
	}
	
	static Map<String,?> instantiateFromJson(String json) {
		return instantiateFromJson(json, null);
	}

	static <KEY,VALUE> VALUE readByKey(Map<KEY,VALUE> map,KEY key) {
		if(map == null)
			return null;
		return map.get(key);
	}
}
