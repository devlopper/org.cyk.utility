package org.cyk.utility.client.controller.component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.cyk.utility.annotation.Annotations;
import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.__kernel__.string.Strings;

@SuppressWarnings("rawtypes") @Deprecated
public interface ComponentBuilderClassGetter extends FunctionWithPropertiesAsInput<Class> {

	Class<?> getClazz();
	ComponentBuilderClassGetter setClazz(Class<?> clazz);
	
	Strings getFieldNameStrings();
	Strings getFieldNameStrings(Boolean injectIfNull);
	ComponentBuilderClassGetter setFieldNameStrings(Strings fieldNameStrings);
	ComponentBuilderClassGetter addFieldNameStrings(String...fieldNameStrings);
	
	Field getField();
	ComponentBuilderClassGetter setField(Field field);
	
	Method getMethod();
	ComponentBuilderClassGetter setMethod(Method method);
	
	String getMethodName();
	ComponentBuilderClassGetter setMethodName(String methodName);
	
	Class<?> getBaseClass();
	ComponentBuilderClassGetter setBaseClass(Class<?> baseClass);
	
	Annotations getAnnotations();
	Annotations getAnnotations(Boolean injectIfNull);
	ComponentBuilderClassGetter setAnnotations(Annotations annotations);
	ComponentBuilderClassGetter addAnnotations(Annotation...annotations);
	
	
}
