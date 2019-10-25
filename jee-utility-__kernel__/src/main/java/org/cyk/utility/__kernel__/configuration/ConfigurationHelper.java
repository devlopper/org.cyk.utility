package org.cyk.utility.__kernel__.configuration;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.annotation.Configuration;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.context.ContextHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.identifier.resource.RequestHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.system.OperatingSystemHelper;
import org.cyk.utility.__kernel__.system.SystemHelper;
import org.cyk.utility.__kernel__.value.Checker;
import org.cyk.utility.__kernel__.value.ValueHelper;

public interface ConfigurationHelper {

	static Variable mapVariable(String name,Object value,VariableLocation location) {
		Variable variable = VARIABLES.get(name);
		if(variable == null) {
			variable = new Variable().setName(name).setValue(value).setLocation(location);
			VARIABLES.put(name, variable);
			LogHelper.logInfo("configuration variable mapped : "+variable, ConfigurationHelper.class);
		}else {
			variable.setValue(value).setLocation(location);
		}
		return variable;
	}
	
	static Variable getVariable(String name,Object context,Object request) {
		if(StringHelper.isBlank(name) || VARIABLES_NAMES_NOT_BOUND.contains(name))
			return null;
		Variable variable = VARIABLES.get(name);
		if(variable != null)
			return variable;
		Object value;
		new Variable().setName(name);
		
		if((value = OperatingSystemHelper.getProperty(name)) != null)
			return mapVariable(name,value,VariableLocation.ENVIRONMENT);
		
		if((value = SystemHelper.getProperty(name)) != null)
			return mapVariable(name,value,VariableLocation.SYSTEM);
		
		if(context == null)
			context = ContextHelper.get();
		if(context!=null && (value = ContextHelper.getParameter(name,context)) != null)
			return mapVariable(name,value,VariableLocation.CONTEXT);
		
		if(request != null && (value = RequestHelper.getParameter(name, request)) != null)
			return mapVariable(name,value,VariableLocation.REQUEST);
		
		LogHelper.log("configuration parameter "+name+" not found");
		VARIABLES_NAMES_NOT_BOUND.add(name);
		return null;
	}
	
	static Variable getVariable(String name) {
		return getVariable(name, null, null);
	}
	
	static Object getValue(String name,Object context,Object request,Object nullValue,Checker valueChecker) {
		if(StringHelper.isBlank(name))
			return null;
		Variable variable = getVariable(name, context,request);
		if(variable == null)
			return null;
		Object value = variable.getValue();
		if(valueChecker == null)
			valueChecker = DependencyInjection.injectByQualifiersClasses(Checker.class,Configuration.Class.class);
		if(ValueHelper.isNull(value, valueChecker))
			value = nullValue;
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
	
	static Integer getValueAsInteger(String name,Object context,Object request) {
		return ValueHelper.convertToInteger(getValue(name, context, request, Boolean.FALSE.toString()));
	}
	
	static Integer getValueAsInteger(String name,Object context) {
		return getValueAsInteger(name,context, null);
	}
	
	static Integer getValueAsInteger(String name) {
		return getValueAsInteger(name,null,null);
	}
	
	/**/
	
	static void setClassUniformResourceIdentifier(Class<?> klass,Object classifier,String uniformResourceIdentifier) {
		if(klass == null)
			return;
		mapVariable(VariableName.buildClassUniformResourceIdentifier(klass,classifier), uniformResourceIdentifier, VariableLocation.SYSTEM);
	}
	
	static void setClassUniformResourceIdentifier(Class<?> klass,String uniformResourceIdentifier) {
		setClassUniformResourceIdentifier(klass, null,uniformResourceIdentifier);
	}
	
	static void setClassUniformResourceIdentifier(Class<?> klass,Object classifier,URI uri) {
		if(klass == null)
			return;
		setClassUniformResourceIdentifier(klass,classifier, uri == null ? null : uri.toString());
	}
	
	static void setClassUniformResourceIdentifier(Class<?> klass,URI uri) {
		setClassUniformResourceIdentifier(klass, null, uri);
	}
	
	static void setClassUniformResourceIdentifier(Class<?> klass,Object classifier,URL url) {
		if(klass == null)
			return;
		try {
			setClassUniformResourceIdentifier(klass,classifier, url == null ? null : url.toURI());
		} catch (URISyntaxException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	static void setClassUniformResourceIdentifier(Class<?> klass,URL url) {
		setClassUniformResourceIdentifier(klass, null, url);
	}
	
	static String getClassUniformResourceIdentifier(Class<?> klass,Object classifier) {
		if(klass == null)
			return null;
		return getValueAsString(VariableName.buildClassUniformResourceIdentifier(klass,classifier));
	}
	
	static String getClassUniformResourceIdentifier(Class<?> klass) {
		return getClassUniformResourceIdentifier(klass,null);
	}
	
	static void setFieldName(Class<?> klass,Object classifier,Collection<String> paths,String value) {
		if(klass == null || CollectionHelper.isEmpty(paths))
			return;
		mapVariable(VariableName.buildFieldName(klass,classifier, paths), value, VariableLocation.SYSTEM);
	}
	
	static void setFieldName(Class<?> klass,Collection<String> paths,String value) {
		setFieldName(klass,null,paths,value);
	}
	
	static void setFieldName(Class<?> klass,Object classifier,String path,String value) {
		if(klass == null || StringHelper.isBlank(path))
			return;
		setFieldName(klass,classifier,List.of(path),value);
	}
	
	static void setFieldName(Class<?> klass,String path,String value) {
		setFieldName(klass,null,path,value);
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
		VARIABLES.clear();
		VARIABLES_NAMES_NOT_BOUND.clear();
	}
	
	Map<String,Variable> VARIABLES = new HashMap<>();
	Collection<String> VARIABLES_NAMES_NOT_BOUND = new HashSet<>();
}
