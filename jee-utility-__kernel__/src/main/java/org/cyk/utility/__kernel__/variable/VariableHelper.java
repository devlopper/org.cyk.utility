package org.cyk.utility.__kernel__.variable;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.context.ContextHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.identifier.resource.RequestHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.system.OperatingSystemHelper;
import org.cyk.utility.__kernel__.system.SystemHelper;
import org.cyk.utility.__kernel__.value.Checker;
import org.cyk.utility.__kernel__.value.ValueHelper;

public interface VariableHelper {

	/* get */
	
	static Variable get(String name,Object context,Object request) {
		if(StringHelper.isBlank(name) || VARIABLES_NAMES_NOT_BOUND.contains(name))
			return null;
		Variable variable = VARIABLES.get(name);
		if(variable != null)
			return variable;
		Object value;
		new Variable().setName(name);
		
		if((value = OperatingSystemHelper.getProperty(name)) != null)
			return write(name,value,VariableLocation.ENVIRONMENT);
		
		if((value = SystemHelper.getProperty(name)) != null)
			return write(name,value,VariableLocation.SYSTEM);
		
		if(context == null)
			context = ContextHelper.get();
		if(context!=null && (value = ContextHelper.getParameter(name,context)) != null)
			return write(name,value,VariableLocation.CONTEXT);
		
		if(request != null && (value = RequestHelper.getParameter(name, request)) != null)
			return write(name,value,VariableLocation.REQUEST);
		
		LogHelper.log("configuration parameter "+name+" not found");
		VARIABLES_NAMES_NOT_BOUND.add(name);
		return null;
	}
	
	static Variable get(String name) {
		return get(name, null, null);
	}
	
	/* load */

	static void load(Class<?> klass) {
		if(CLASSES_FIELDS_NAMES_LOADED.contains(klass))
			return;
		CLASSES_FIELDS_NAMES_LOADED.add(klass);
		Collection<Field> jsonbs = FieldHelper.getJsonbs(klass);
		if(CollectionHelper.isEmpty(jsonbs))
			return;
		for(Field field : jsonbs)
			readFieldName(klass, field.getName());		
	}
	
	/* write */
	
	static Variable write(String name,Object value,VariableLocation location) {
		if(StringHelper.isBlank(name))
			return null;
		Variable variable = VARIABLES.get(name);
		if(variable == null) {
			variable = new Variable().setName(name).setValue(value).setLocation(location);
			VARIABLES.put(name, variable);
			LogHelper.logInfo("variable mapped : "+variable, VariableHelper.class);
		}else {
			variable.setValue(value).setLocation(location);
		}
		return variable;
	}
	
	static Variable write(String name,Object value) {
		if(StringHelper.isBlank(name))
			return null;
		return write(name, value, VariableLocation.SYSTEM);
	}
	
	/* read */
	
	static Object read(String name,Object context,Object request,Object nullValue,Checker valueChecker,Class<?> valueCheckerQualifierClass) {
		if(StringHelper.isBlank(name))
			return null;
		Variable variable = get(name, context,request);
		if(variable == null)
			return null;
		Object value = variable.getValue();
		if(valueChecker == null)
			valueChecker = DependencyInjection.injectByQualifiersClasses(Checker.class,valueCheckerQualifierClass);
		if(ValueHelper.isNull(value, valueChecker))
			value = nullValue;
		return value;
	}
	
	static Object read(String name,Object context,Object request,Object nullValue,Class<?> valueCheckerQualifierClass) {
		return read(name, context,request, nullValue,null,valueCheckerQualifierClass);
	}
	
	static Object read(String name,Object context,Object request,Class<?> valueCheckerQualifierClass) {
		return read(name, context,request, null,null,valueCheckerQualifierClass);
	}
	
	static Object read(String name,Object context,Class<?> valueCheckerQualifierClass) {
		return read(name, context,null, null,null,valueCheckerQualifierClass);
	}
	
	static Object read(String name,Class<?> valueCheckerQualifierClass) {
		return read(name, null,null, null,null,valueCheckerQualifierClass);
	}
	
	static Object read(String name) {
		return read(name, null,null, null,null,null);
	}
	
	static String readAsString(String name,Object context,Object request,Object nullValue,Class<?> valueCheckerQualifierClass) {
		return (String) read(name, context, request, nullValue,null,valueCheckerQualifierClass);
	}
	
	static String readAsString(String name,Object context,Object request,Class<?> valueCheckerQualifierClass) {
		return (String) read(name, context, request, null,null,valueCheckerQualifierClass);
	}
	
	static String readAsString(String name,Object context,Class<?> valueCheckerQualifierClass) {
		return (String) read(name, context, null, null,null,valueCheckerQualifierClass);
	}
	
	static String readAsString(String name,Class<?> valueCheckerQualifierClass) {
		return (String) read(name, null, null, null,null,valueCheckerQualifierClass);
	}
	
	static Boolean readAsBoolean(String name,Object context,Object request,Class<?> valueCheckerQualifierClass) {
		return ValueHelper.convertToBoolean(read(name, context, request, Boolean.FALSE.toString(),valueCheckerQualifierClass));
	}
	
	static Boolean readAsBoolean(String name,Object context,Class<?> valueCheckerQualifierClass) {
		return readAsBoolean(name,context, null,valueCheckerQualifierClass);
	}
	
	static Boolean readAsBoolean(String name,Class<?> valueCheckerQualifierClass) {
		return readAsBoolean(name,null,null,valueCheckerQualifierClass);
	}
	
	static Boolean is(String name,Object context,Object request,Class<?> valueCheckerQualifierClass) {
		return Boolean.TRUE.equals(readAsBoolean(name, context, request,valueCheckerQualifierClass));
	}
	
	static Boolean is(String name,Object context,Class<?> valueCheckerQualifierClass) {
		return Boolean.TRUE.equals(readAsBoolean(name, context, null,valueCheckerQualifierClass));
	}
	
	static Boolean is(String name,Class<?> valueCheckerQualifierClass) {
		return Boolean.TRUE.equals(readAsBoolean(name, null, null,valueCheckerQualifierClass));
	}
	
	static Integer readAsInteger(String name,Object context,Object request,Class<?> valueCheckerQualifierClass) {
		return ValueHelper.convertToInteger(read(name, context, request, Boolean.FALSE.toString(),valueCheckerQualifierClass));
	}
	
	static Integer readAsInteger(String name,Object context,Class<?> valueCheckerQualifierClass) {
		return readAsInteger(name,context, null,valueCheckerQualifierClass);
	}
	
	static Integer readAsInteger(String name,Class<?> valueCheckerQualifierClass) {
		return readAsInteger(name,null,null,valueCheckerQualifierClass);
	}
	
	/**/
	
	static void writeClassUniformResourceIdentifier(Class<?> klass,Object classifier,String uniformResourceIdentifier) {
		if(klass == null)
			return;
		write(VariableName.buildClassUniformResourceIdentifier(klass,classifier), uniformResourceIdentifier, VariableLocation.SYSTEM);
	}
	
	static void writeClassUniformResourceIdentifier(Class<?> klass,String uniformResourceIdentifier) {
		writeClassUniformResourceIdentifier(klass, null,uniformResourceIdentifier);
	}
	
	static void writeClassUniformResourceIdentifier(Class<?> klass,Object classifier,URI uri) {
		if(klass == null)
			return;
		writeClassUniformResourceIdentifier(klass,classifier, uri == null ? null : uri.toString());
	}
	
	static void writeClassUniformResourceIdentifier(Class<?> klass,URI uri) {
		writeClassUniformResourceIdentifier(klass, null, uri);
	}
	
	static void writeClassUniformResourceIdentifier(Class<?> klass,Object classifier,URL url) {
		if(klass == null)
			return;
		try {
			writeClassUniformResourceIdentifier(klass,classifier, url == null ? null : url.toURI());
		} catch (URISyntaxException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	static void writeClassUniformResourceIdentifier(Class<?> klass,URL url) {
		writeClassUniformResourceIdentifier(klass, null, url);
	}
	
	static String readClassUniformResourceIdentifier(Class<?> klass,Object classifier,Class<?> valueCheckerQualifierClass) {
		if(klass == null)
			return null;
		return readAsString(VariableName.buildClassUniformResourceIdentifier(klass,classifier),valueCheckerQualifierClass);
	}
	
	static String readClassUniformResourceIdentifier(Class<?> klass,Class<?> valueCheckerQualifierClass) {
		return readClassUniformResourceIdentifier(klass,null,valueCheckerQualifierClass);
	}
	
	static void writeFieldName(Class<?> klass,Object classifier,Collection<String> paths,String value) {
		if(klass == null || CollectionHelper.isEmpty(paths))
			return;
		write(VariableName.buildFieldName(klass,classifier, paths), value, VariableLocation.SYSTEM);
	}
	
	static void writeFieldName(Class<?> klass,Collection<String> paths,String value) {
		writeFieldName(klass,null,paths,value);
	}
	
	static void writeFieldName(Class<?> klass,Object classifier,String path,String value) {
		if(klass == null || StringHelper.isBlank(path))
			return;
		writeFieldName(klass,classifier,List.of(path),value);
	}
	
	static void writeFieldName(Class<?> klass,String path,String value) {
		writeFieldName(klass,null,path,value);
	}
	
	static Collection<Variable> getFieldsNames(Class<?> klass,Object classifier) {
		if(klass == null || MapHelper.isEmpty(VARIABLES))
			return null;
		Collection<Variable> fieldsNames = null;
		String name = VariableName.build(klass,classifier)+VariableName.SEPARATOR;
		for(Map.Entry<String,Variable> entry : VARIABLES.entrySet()) {
			if(!entry.getKey().startsWith(name))
				continue;
			if(fieldsNames == null)
				fieldsNames = new ArrayList<>();
			fieldsNames.add(entry.getValue());
		}
		return fieldsNames;
	}
	
	static String readFieldName(Class<?> klass,Object classifier,Collection<String> paths,Class<?> valueCheckerQualifierClass) {
		if(klass == null || CollectionHelper.isEmpty(paths))
			return null;
		String fieldName = FieldHelper.join(paths);
		if(StringHelper.isBlank(fieldName))
			return null;
		return readAsString(VariableName.buildFieldName(klass,classifier, paths),valueCheckerQualifierClass);
	}
	
	static String readFieldName(Class<?> klass,Collection<String> paths,Class<?> valueCheckerQualifierClass) {
		return readFieldName(klass,null,paths,valueCheckerQualifierClass);
	}
	
	static String readFieldName(Class<?> klass,Object classifier,Class<?> valueCheckerQualifierClass,String...paths) {
		if(klass == null || ArrayHelper.isEmpty(paths))
			return null;
		return readFieldName(klass,classifier, CollectionHelper.listOf(paths),valueCheckerQualifierClass);
	}
	
	static String readFieldName(Class<?> klass,Class<?> valueCheckerQualifierClass,String...paths) {
		return readFieldName(klass,null,valueCheckerQualifierClass,paths);
	}
	
	static String readFieldName(Class<?> klass,String...paths) {
		return readFieldName(klass,null,null,paths);
	}
	
	static String readFieldNameByValue(Class<?> klass,Object classifier,String value) {
		if(klass == null)
			return null;
		Collection<Variable> variables = getFieldsNames(klass, classifier);
		if(CollectionHelper.isEmpty(variables))
			return null;
		for(Variable variable : variables)
			if(variable.getValue().equals(value))
				return StringUtils.substringAfter(variable.getName(),VariableName.SEPARATOR);
		return null;
	}
	
	static void clear() {
		VARIABLES.clear();
		VARIABLES_NAMES_NOT_BOUND.clear();
		CLASSES_FIELDS_NAMES_LOADED.clear();
	}
	
	Map<String,Variable> VARIABLES = new HashMap<>();
	Collection<String> VARIABLES_NAMES_NOT_BOUND = new HashSet<>();
	Collection<Class<?>> CLASSES_FIELDS_NAMES_LOADED = new HashSet<>();

}
