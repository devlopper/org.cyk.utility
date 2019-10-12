package org.cyk.utility.__kernel__.context;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.cyk.utility.__kernel__.string.StringHelper;

public interface ContextHelper {

	static void set(Object context) {
		if(CONSTANTS.size() == 0)
			CONSTANTS.add(context);
		else
			CONSTANTS.set(0, context);
	}
	
	static Object get() {
		Object context;
		if(CONSTANTS.size() == 0)
			context = null;
			//set(context = DependencyInjection.inject(ServletContext.class));
		else
			context = CONSTANTS.get(0);
		return context;
	}
	
	static String getParameter(String name,Object context) {
		if(StringHelper.isBlank(name) || context == null)
			return null;
		if(context instanceof ServletContext)
			return ((ServletContext)context).getInitParameter(name);
		throw new RuntimeException("get parameter from request of type <<"+context.getClass()+">> not yet implemented");
	}
	
	static String getParameter(String name) {
		return getParameter(name,get());
	}
	
	List<Object> CONSTANTS = new ArrayList<Object>();
	
}
