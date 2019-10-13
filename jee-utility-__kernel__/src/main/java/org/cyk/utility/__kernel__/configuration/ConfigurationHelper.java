package org.cyk.utility.__kernel__.configuration;

import org.cyk.utility.__kernel__.context.ContextHelper;
import org.cyk.utility.__kernel__.identifier.resource.RequestHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.system.OperatingSystemHelper;
import org.cyk.utility.__kernel__.system.SystemHelper;
import org.cyk.utility.__kernel__.value.Checker;
import org.cyk.utility.__kernel__.value.ValueHelper;

public interface ConfigurationHelper {

	static Variable getVariable(String name,Object context,Object request) {
		if(StringHelper.isBlank(name))
			return null;
		Variable variable = new Variable().setName(name);
		
		//operating system
		if(variable.getValue() == null)
			variable.setValue(OperatingSystemHelper.getProperty(name));
		if(variable.getValue() != null)
			return variable.setLocation(Location.ENVIRONMENT);
		
		//system
		variable.setValue(SystemHelper.getProperty(name));
		if(variable.getValue() != null)
			return variable.setLocation(Location.SYSTEM);
		
		//context
		if(context == null)
			context = ContextHelper.get();
		if(context!=null)
			variable.setValue(ContextHelper.getParameter(name,context));
		if(variable.getValue() != null)
			return variable.setLocation(Location.CONTEXT);
		
		//request
		if(request != null)
			variable.setValue(RequestHelper.getParameter(name, request));
		if(variable.getValue() != null)
			return variable.setLocation(Location.REQUEST);
		
		LogHelper.log("configuration parameter "+name+" not found");
		return null;
	}
	
	static Variable getVariable(String name) {
		return getVariable(name, null, null);
	}
	
	static Object getValue(String name,Object request,Object context,Object nullValue,Checker valueChecker) {
		if(StringHelper.isBlank(name))
			return null;
		Variable variable = getVariable(name, request, context);
		if(variable == null)
			return null;
		Object value = variable.getValue();
		if(ValueHelper.isNull(value, valueChecker))
			value = nullValue;
		return value;
	}
	
	static Object getValue(String name,Object request,Object context,Object nullValue) {
		return getValue(name, request, context, nullValue,ValueCheckerImpl.INSTANCE);
	}
	
	static Object getValue(String name,Object request,Object context) {
		return getValue(name, request, context, null,ValueCheckerImpl.INSTANCE);
	}
	
}
