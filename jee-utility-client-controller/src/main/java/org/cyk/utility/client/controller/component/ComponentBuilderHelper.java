package org.cyk.utility.client.controller.component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.cyk.utility.annotation.Annotations;
import org.cyk.utility.helper.Helper;
import org.cyk.utility.system.action.SystemAction;

public interface ComponentBuilderHelper extends Helper {

	Class<? extends ComponentBuilder<?>> getComponentBuilderClass(Class<?> klass,Class<?> baseClass,Field field,String[] fieldNames,Method method,String methodName,Annotations annotations);
	
	ComponentBuilder<?> getComponentBuilder(Class<? extends ComponentBuilder<?>> klass,Object object,Field field,String[] fieldNames,Method method,String methodName,SystemAction systemAction);
}
