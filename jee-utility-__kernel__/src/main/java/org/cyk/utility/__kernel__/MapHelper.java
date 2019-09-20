package org.cyk.utility.__kernel__;

import java.util.Map;
import java.util.Map.Entry;

public interface MapHelper {

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
