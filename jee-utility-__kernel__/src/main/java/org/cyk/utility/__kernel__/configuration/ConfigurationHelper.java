package org.cyk.utility.__kernel__.configuration;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.annotation.Configuration;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Checker;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.__kernel__.variable.Variable;
import org.cyk.utility.__kernel__.variable.VariableHelper;
import org.cyk.utility.__kernel__.variable.VariableName;

public interface ConfigurationHelper {
	
	static Object getValue(String name,Object context,Object request,Object nullValue,Checker valueChecker) {
		if(StringHelper.isBlank(name))
			return null;
		Variable variable = VariableHelper.get(name, context,request);
		if(variable == null)
			return null;
		Object value = variable.getValue();
		if(valueChecker == null)
			valueChecker = DependencyInjection.injectByQualifiersClasses(Checker.class,Configuration.Class.class);
		if(ValueHelper.isNull(value, valueChecker))
			value = nullValue;
		GOT.put(name, value);
		return value;
	}
	
	static Object getValue(String name,Object context,Object request,Object nullValue) {
		return getValue(name, context,request, nullValue,null);
	}
	
	static Object getValue(String name,Object context,Object request) {
		return getValue(name, context,request, null,null);
	}
	
	static Object getValue(String name,Object context) {
		return getValue(name, context,null, null,null);
	}
	
	static Object getValue(String name) {
		return getValue(name, null,null, null,null);
	}
	
	static String getValueAsString(String name,Object context,Object request,Object nullValue) {
		return (String) getValue(name, context, request, nullValue,null);
	}
	
	static String getValueAsString(String name,Object context,Object request) {
		return (String) getValue(name, context, request, null,null);
	}
	
	static String getValueAsString(String name,Object context) {
		return (String) getValue(name, context, null, null,null);
	}
	
	static String getValueAsString(String name) {
		return (String) getValue(name, null, null, null,null);
	}
	
	static Boolean getValueAsBoolean(String name,Object context,Object request) {
		return ValueHelper.convertToBoolean(getValue(name, context, request, Boolean.FALSE.toString()));
	}
	
	static Boolean getValueAsBoolean(String name,Object context) {
		return getValueAsBoolean(name,context, null);
	}
	
	static Boolean getValueAsBoolean(String name) {
		return getValueAsBoolean(name,null,null);
	}
	
	static Boolean is(String name,Object context,Object request) {
		return Boolean.TRUE.equals(getValueAsBoolean(name, context, request));
	}
	
	static Boolean is(String name,Object context) {
		return Boolean.TRUE.equals(getValueAsBoolean(name, context, null));
	}
	
	static Boolean is(String name) {
		return Boolean.TRUE.equals(getValueAsBoolean(name, null, null));
	}
	
	static Boolean isNot(String name) {
		return !is(name);
	}
	
	static Integer getValueAsInteger(String name,Object context,Object request) {
		return ValueHelper.convertToInteger(getValue(name, context, request, Boolean.FALSE.toString()));
	}
	
	static Integer getValueAsInteger(String name,Object context) {
		return getValueAsInteger(name,context, null);
	}
	
	static Integer getValueAsInteger(String name) {
		return getValueAsInteger(name,null,null);
	}
	
	static Long getValueAsLong(String name,Object context,Object request) {
		return ValueHelper.convertToLong(getValue(name, context, request, Boolean.FALSE.toString()));
	}
	
	static Long getValueAsLong(String name,Object context) {
		return getValueAsLong(name,context, null);
	}
	
	static Long getValueAsLong(String name) {
		return getValueAsLong(name,null,null);
	}
	
	/**/
	
	static String getClassUniformResourceIdentifier(Class<?> klass,Object classifier) {
		if(klass == null)
			return null;
		return getValueAsString(VariableName.buildClassUniformResourceIdentifier(klass,classifier));
	}
	
	static String getClassUniformResourceIdentifier(Class<?> klass) {
		return getClassUniformResourceIdentifier(klass,null);
	}
	
	static String getFieldName(Class<?> klass,Object classifier,Collection<String> paths) {
		if(klass == null || CollectionHelper.isEmpty(paths))
			return null;
		String fieldName = FieldHelper.join(paths);
		if(StringHelper.isBlank(fieldName))
			return null;
		return getValueAsString(VariableName.buildFieldName(klass,classifier, paths));
	}
	
	static String getFieldName(Class<?> klass,Collection<String> paths) {
		return getFieldName(klass,null,paths);
	}
	
	static String getFieldName(Class<?> klass,Object classifier,String...paths) {
		if(klass == null || ArrayHelper.isEmpty(paths))
			return null;
		return getFieldName(klass,classifier, CollectionHelper.listOf(paths));
	}
	
	static String getFieldName(Class<?> klass,String...paths) {
		return getFieldName(klass,null,paths);
	}
	
	static void clear() {
		VariableHelper.clear();
	}

	Map<String,Object> GOT = new HashMap<>();
}
