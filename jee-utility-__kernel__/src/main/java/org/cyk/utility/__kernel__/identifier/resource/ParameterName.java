package org.cyk.utility.__kernel__.identifier.resource;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.MapHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;

import lombok.Getter;
import lombok.Setter;

public enum ParameterName {
	ENTITY_CLASS
	,ENTITY_IDENTIFIER
	,ACTION_CLASS
	,ACTION_IDENTIFIER
	
	,NEXT_ACTION_CLASS
	,NEXT_ACTION_IDENTIFIER
	
	,WINDOW_RENDER_TYPE_CLASS
	
	;
	
	@Getter @Setter private String value;
	@Getter @Setter private Class<?> type;
	
	private ParameterName(String value,Class<?> type) {
		if(value == null || value.isBlank())
			value = normalize(name().toLowerCase());
		this.value = value;
		if(type == null)
			type = String.class;
		this.type = type;
	}
	
	private ParameterName() {
		this(null,null);
	}
	
	/**/
	
	public static String stringify(Object value) {
		if(value == null)
			return null;
		String string = MAP.get(value);
		if(string != null && !string.isBlank())
			return string;
		if(value instanceof Class)
			string = ((Class<?>)value).getSimpleName();
		else if(value instanceof ParameterName)
			return ((ParameterName) value).getValue();
		else if(value instanceof Enum)
			string = ((Enum<?>) value).name();
		else if(value instanceof String) {
			if(((String)value).isBlank())
				return null;
			string = (String) value;
		}		
		string = normalize(string);
		if(string == null || string.isBlank())
			throw new RuntimeException("Parameter name <<"+value+">> cannot be stringify");
		MAP.put(value, string);
		return string;
	}
	
	public static String normalize(String string) {
		if(string == null || string.isBlank())
			return null;
		string = StringUtils.remove(string.toLowerCase(), "_");
		return string;
	}
	
	public static Collection<Object> getKeys(Boolean keysAreClasses,Collection<Class<?>> keysInstanceOfClasses,Collection<String> values) {
		return MapHelper.getKeys(ParameterName.MAP, keysAreClasses,keysInstanceOfClasses, values);
	}
	
	public static Collection<Object> getKeysWhereKeysAreClasses(Collection<Class<?>> keysInstanceOfClasses,Collection<String> values) {
		if(keysInstanceOfClasses == null || keysInstanceOfClasses.isEmpty())
			return null;
		return MapHelper.getKeysWhereKeysAreClasses(ParameterName.MAP, keysInstanceOfClasses, values);
	}
	
	public static Collection<Object> getKeysWhereKeysAreClasses(Collection<Class<?>> keysInstanceOfClasses,String value) {
		if(keysInstanceOfClasses == null || keysInstanceOfClasses.isEmpty())
			return null;
		return MapHelper.getKeysWhereKeysAreClasses(ParameterName.MAP, keysInstanceOfClasses, value == null || value.isBlank() ? null : CollectionHelper.listOf(value));
	}
	
	public static final Map<Object,String> MAP = new HashMap<>();
}