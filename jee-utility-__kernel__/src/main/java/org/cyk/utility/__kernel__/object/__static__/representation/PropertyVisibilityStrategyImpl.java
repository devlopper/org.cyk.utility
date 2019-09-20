package org.cyk.utility.__kernel__.object.__static__.representation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.json.bind.config.PropertyVisibilityStrategy;

public class PropertyVisibilityStrategyImpl implements PropertyVisibilityStrategy {

	@Override
	public boolean isVisible(Field field) {
		return true;
	}

	@Override
	public boolean isVisible(Method method) {
		if(method.getName().startsWith("get") && method.getParameterCount() > 0)
			return false;			
		return true;
	}
	
}