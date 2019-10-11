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

	static Object get(String name,Object request,Object context) {
		if(StringHelper.isBlank(name))
			return null;
		Object value = null;
		
		//request
		if(request != null)
			value = RequestHelper.getParameter(name, request);
		if(value != null)
			return value;
		
		//system
		value = SystemHelper.getProperty(name);
		if(value != null)
			return value;
		
		//context
		if(context == null)
			context = ContextHelper.get();
		if(context!=null)
			value = ContextHelper.getParameter(name,context);
		if(value != null)
			return value;
		
		//operating system
		if(value == null)
			value = OperatingSystemHelper.getProperty(name);
		if(value != null)
			return value;
		
		LogHelper.log("configuration parameter "+name+" not found");

		return value;
	}
	
	static Object get(String name,Object request,Object context,Object nullValue,Checker valueChecker) {
		if(StringHelper.isBlank(name))
			return null;
		Object value = get(name, request, context);
		
		if(ValueHelper.isNull(value, valueChecker))
			value = nullValue;
		return value;
	}
	
	static Object get(String name,Object request,Object context,Object nullValue) {
		return get(name, request, context, nullValue,ValueCheckerImpl.INSTANCE);
	}
	
}
