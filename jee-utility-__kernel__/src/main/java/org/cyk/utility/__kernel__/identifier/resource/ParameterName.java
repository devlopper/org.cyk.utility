package org.cyk.utility.__kernel__.identifier.resource;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.string.RegularExpressionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.system.action.SystemAction;

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
	
	,SESSION_IDENTIFIER
	,MULTIPLE
	,LOGGABLE_AS_INFO
	,IS_STATIC
	,URL
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
	
	public static final Map<Object,String> MAP = new HashMap<>();
	public static final Set<Class<?>> CLASSES = new HashSet<>();
	
	/**/
	
	public static String stringify(Object value) {
		if(value == null)
			return null;
		if(MAP.containsKey(value))
			return MAP.get(value);
		String string = null;//MAP.get(value);
		/*if(string != null && !string.isBlank())
			return string;
		*/
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
		if(StringHelper.isBlank(string))
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
	
	public static void initialise() {
		MAP.clear();
		for(Class<?> index : CLASSES) {
			String value = null;
			if(ClassHelper.isInstanceOf(index, SystemAction.class))
				value = StringUtils.substringAfter(index.getSimpleName(),SystemAction.class.getSimpleName());
			else
				value = index.getSimpleName();
			MAP.put(index, value.toLowerCase());
		}
	}
	
	public static void addClasses(Collection<Class<?>> classes) {
		if(CollectionHelper.isEmpty(classes))
			return;
		CLASSES.addAll(classes);
		initialise();
	}
	
	public static void addClasses(Class<?>...classes) {
		if(ArrayHelper.isEmpty(classes))
			return;
		addClasses(CollectionHelper.listOf(classes));
	}
	
	static {
		addClasses(ClassHelper.filter(List.of(SystemAction.class.getPackage()),  RegularExpressionHelper.buildIsDoNotEndWith("Impl"),List.of(SystemAction.class),null,null));
	}
	
	
}