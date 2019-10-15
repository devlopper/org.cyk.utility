package org.cyk.utility.__kernel__.configuration;

import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.context.ContextHelper;
import org.cyk.utility.__kernel__.identifier.resource.RequestHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.system.OperatingSystemHelper;
import org.cyk.utility.__kernel__.system.SystemHelper;
import org.cyk.utility.__kernel__.value.Checker;
import org.cyk.utility.__kernel__.value.ValueHelper;

public interface ConfigurationHelper {

	static Variable mapVariable(String name,Object value,Location location) {
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
		if(StringHelper.isBlank(name))
			return null;
		Variable variable = VARIABLES.get(name);
		if(variable != null)
			return variable;
		Object value;
		new Variable().setName(name);
		
		if((value = OperatingSystemHelper.getProperty(name)) != null)
			return mapVariable(name,value,Location.ENVIRONMENT);
		
		if((value = SystemHelper.getProperty(name)) != null)
			return mapVariable(name,value,Location.SYSTEM);
		
		if(context == null)
			context = ContextHelper.get();
		if(context!=null && (value = ContextHelper.getParameter(name,context)) != null)
			return mapVariable(name,value,Location.CONTEXT);
		
		if(request != null && (value = RequestHelper.getParameter(name, request)) != null)
			return mapVariable(name,value,Location.REQUEST);
		
		LogHelper.log("configuration parameter "+name+" not found");
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
		if(ValueHelper.isNull(value, valueChecker))
			value = nullValue;
		return value;
	}
	
	static Object getValue(String name,Object context,Object request,Object nullValue) {
		return getValue(name, context,request, nullValue,ValueCheckerImpl.INSTANCE);
	}
	
	static Object getValue(String name,Object context,Object request) {
		return getValue(name, context,request, null,ValueCheckerImpl.INSTANCE);
	}
	
	static Object getValue(String name,Object context) {
		return getValue(name, context,null, null,ValueCheckerImpl.INSTANCE);
	}
	
	static Object getValue(String name) {
		return getValue(name, null,null, null,ValueCheckerImpl.INSTANCE);
	}
	
	static String getValueAsString(String name,Object context,Object request) {
		return (String) getValue(name, context, request, null,ValueCheckerImpl.INSTANCE);
	}
	
	static String getValueAsString(String name,Object context) {
		return (String) getValue(name, context, null, null,ValueCheckerImpl.INSTANCE);
	}
	
	static String getValueAsString(String name) {
		return (String) getValue(name, null, null, null,ValueCheckerImpl.INSTANCE);
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
		return getValueAsBoolean(name, context, request);
	}
	
	static Boolean is(String name,Object context) {
		return getValueAsBoolean(name, context, null);
	}
	
	static Boolean is(String name) {
		return getValueAsBoolean(name, null, null);
	}
	
	Map<String,Variable> VARIABLES = new HashMap<>();
}
