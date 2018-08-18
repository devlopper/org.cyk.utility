package org.cyk.utility.field;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import org.cyk.utility.__kernel__.object.dynamic.AbstractSingleton;
import org.cyk.utility.value.ValueUsageType;

@Singleton
public class FieldNameValueUsageMapImpl extends AbstractSingleton implements FieldNameValueUsageMap,Serializable {
	private static final long serialVersionUID = 1L;

	private Map<Class<?>,Map<FieldName,Map<ValueUsageType,String>>> map;
	
	@Override
	public FieldNameValueUsageMap set(Class<?> aClass, FieldName fieldName, ValueUsageType valueUsageType,String value) {
		if(map == null)
			map = new HashMap<>();
		Map<FieldName,Map<ValueUsageType,String>> subMap = map.get(aClass);
		if(subMap == null)
			map.put(aClass, subMap = new HashMap<>());
		Map<ValueUsageType,String> subSubMap = subMap.get(fieldName);
		if(subSubMap == null)
			subMap.put(fieldName, subSubMap = new HashMap<>());
		subSubMap.put(valueUsageType, value);
		return this;
	}

	@Override
	public String get(Class<?> aClass, FieldName fieldName, ValueUsageType valueUsageType) {
		String value = null;
		if(map!=null) {
			Map<FieldName,Map<ValueUsageType,String>> subMap = map.get(aClass);
			if(subMap!=null) {
				Map<ValueUsageType,String> subSubMap = subMap.get(fieldName);
				if(subSubMap!=null) {
					value = subSubMap.get(valueUsageType);
				}
			}
		}
		return value;
	}
}
