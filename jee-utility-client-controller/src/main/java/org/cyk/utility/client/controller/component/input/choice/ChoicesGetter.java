package org.cyk.utility.client.controller.component.input.choice;

import java.lang.reflect.Field;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.__kernel__.object.Objects;

public interface ChoicesGetter extends FunctionWithPropertiesAsInput<Objects> {

	Class<?> getFieldDeclaringClass();
	ChoicesGetter setFieldDeclaringClass(Class<?> fieldDeclaringClass);
	
	Field getField();
	ChoicesGetter setField(Field field);
	
	String getQuery();
	ChoicesGetter setQuery(String query);
	
	Object getRequest();
	ChoicesGetter setRequest(Object request);
	
	Object getContext();
	ChoicesGetter setContext(Object context);
	
	Integer getMaximumNumberOfChoice();
	ChoicesGetter setMaximumNumberOfChoice(Integer maximumNumberOfChoice);
	
}
