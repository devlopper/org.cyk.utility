package org.cyk.utility.method;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Set;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.string.StringLocation;

public interface MethodGetter extends FunctionWithPropertiesAsInput<Collection<Method>> {

	Boolean getIsRecursive();
	MethodGetter setIsRecursive(Boolean value);
	
	Class<?> getClazz();
	MethodGetter setClazz(Class<?> aClass);
	
	String getToken();
	MethodGetter setToken(String token);
	
	StringLocation getTokenLocation();
	MethodGetter setTokenLocation(StringLocation stringLocation);
	
	Set<Integer> getModifiers();
	MethodGetter setModifiers(Set<Integer> modifiers);
	MethodGetter addModifiers(Collection<Integer> modifiers);
	MethodGetter addModifiers(Integer...modifiers);
	
	Set<Class<?>> getAnnotationClasses();
	MethodGetter setAnnotationClasses(Set<Class<?>> annotationClasses);
	MethodGetter addAnnotationClasses(Collection<Class<?>> annotationClasses);
	MethodGetter addAnnotationClasses(Class<?>...annotationClasses);
	
}
