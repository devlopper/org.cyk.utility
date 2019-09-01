package org.cyk.utility.string;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.object.ObjectByIntegerMap;

@Dependent
public class StringFormatImpl extends AbstractObject implements StringFormat,Serializable {
	private static final long serialVersionUID = 1L;

	public static Boolean CACHABLE = Boolean.TRUE;
	private static final Map<String,String> CACHE = new HashMap<>();
	
	private String value;
	@Inject private ObjectByIntegerMap argumentMap;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		argumentMap.setIsOrdered(Boolean.TRUE);
	}
	
	@Override
	public String getValue() {
		return value;
	}

	@Override
	public StringFormat setValue(String format) {
		this.value = format;
		return this;
	}

	@Override
	public ObjectByIntegerMap getArgumentMap() {
		return argumentMap;
	}

	@Override
	public StringFormat setArgumentMap(ObjectByIntegerMap argumentMap) {
		this.argumentMap = argumentMap;
		return this;
	}
	
	@Override
	public StringFormat setArguments(Object... arguments) {
		getArgumentMap().set(arguments);
		return this;
	}
	
	@Override
	public Object getArgument(Integer index) {
		ObjectByIntegerMap map = getArgumentMap();
		return map == null ? null : map.get(index);
	}

	@Override
	public String evaluate() {
		String format = getValue();
		if(format == null)
			throw new RuntimeException("Format value is required");
		ObjectByIntegerMap argumentMap = getArgumentMap();
		if(argumentMap.getMap() == null || argumentMap.getMap().isEmpty())
			throw new RuntimeException("Format argument are required");
		String cacheKey = buildCacheKey(this);
		if(isCached(cacheKey))
			return CACHE.get(cacheKey);
		String result = String.format(format, argumentMap.getMap().values().toArray());
		cache(cacheKey, result);
		return result;
	}
	
	/**/
	
	public static String buildCacheKey(StringFormat format) {
		return format.getValue()+"_"+format.getArgumentMap().getMap().toString();
	}
	
	public static void cache(String key,String value) {
		if(Boolean.TRUE.equals(CACHABLE)) {
			CACHE.put(key, value);
		}
	}
	
	public static void cache(StringFormat format,String value) {
		cache(buildCacheKey(format), value);
	}
	
	public static Boolean isCached(String key) {
		if(Boolean.TRUE.equals(CACHABLE))
			return CACHE.containsKey(key);
		return Boolean.FALSE;
	}
	
	public static Boolean isCached(StringFormat format) {
		return isCached(buildCacheKey(format));
	}
}
