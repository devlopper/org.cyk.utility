package org.cyk.utility.__kernel__;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.cyk.utility.__kernel__.klass.ClassHelper;

public interface MapHelper {

	static <K,V> Collection<K> getKeys(Map<K,V> map,Boolean keysAreClasses,Collection<Class<?>> keysInstanceOfClasses,Collection<V> values) {
		if(map == null || map.isEmpty())
			return null;
		if(values != null && values.isEmpty())
			values = null;
		Collection<K> keys = null;
		for(Map.Entry<K, V> entry : map.entrySet()) {
			if(keysAreClasses!=null) {
				if(keysAreClasses ^ (entry.getKey() instanceof Class))
					continue;					
				if(keysAreClasses) {
					Class<?> klass = (Class<?>) entry.getKey();
					if(!ClassHelper.isInstanceOfOne(klass, keysInstanceOfClasses))
						continue;
				}
			}
			if(values!=null && !values.contains(entry.getValue()))
				continue;
			if(keys == null)
				keys = new ArrayList<>();
			keys.add(entry.getKey());
		}
		return keys;
	}
	
	static <K,V> Collection<K> getKeysWhereKeysAreClasses(Map<K,V> map,Collection<Class<?>> keysInstanceOfClasses,Collection<V> values) {
		if(map == null || map.isEmpty())
			return null;
		return getKeys(map, Boolean.TRUE, keysInstanceOfClasses, values);
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
	
}
