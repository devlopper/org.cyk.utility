package org.cyk.utility.client.controller.component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.__kernel__.system.action.SystemAction;

@SuppressWarnings("rawtypes") @Deprecated
public interface ComponentBuilderGetter extends FunctionWithPropertiesAsInput<ComponentBuilder> {

	Field getField();
	ComponentBuilderGetter setField(Field field);
	
	Method getMethod();
	ComponentBuilderGetter setMethod(Method method);
	
	String getMethodName();
	ComponentBuilderGetter setMethodName(String methodName);
	
	Object getObject();
	ComponentBuilderGetter setObject(Object object);
	
	ComponentBuilderClassGetter getClassGetter();
	ComponentBuilderClassGetter getClassGetter(Boolean injectIfNull);
	ComponentBuilderGetter setClassGetter(ComponentBuilderClassGetter classGetter);
	
	Class<? extends ComponentBuilder<?>> getClazz();
	ComponentBuilderGetter setClazz(Class<? extends ComponentBuilder<?>> clazz);
	
	ComponentBuilderGetter addFieldNameStrings(String...fieldNameStrings);
	
	SystemAction getSystemAction();
	ComponentBuilderGetter setSystemAction(SystemAction systemAction);
}
