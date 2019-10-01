package org.cyk.utility.__kernel__.identifier.resource;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.system.action.SystemActionCreate;
import org.cyk.utility.__kernel__.system.action.SystemActionDelete;
import org.cyk.utility.__kernel__.system.action.SystemActionList;
import org.cyk.utility.__kernel__.system.action.SystemActionRead;
import org.cyk.utility.__kernel__.system.action.SystemActionUpdate;

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
	
	public static final Map<Object,String> MAP = new HashMap<>();
	
	public static void initialise() {
		MAP.clear();
		Collection<Class<?>> classes = List.of(SystemActionCreate.class,SystemActionRead.class,SystemActionUpdate.class,SystemActionDelete.class,SystemActionList.class);
		if(classes != null && !classes.isEmpty())
			for(Class<?> index : classes)
				MAP.put(index, StringUtils.substringAfter(index.getSimpleName(),SystemAction.class.getSimpleName()).toLowerCase());
	}
	
	static {
		initialise();
	}
}