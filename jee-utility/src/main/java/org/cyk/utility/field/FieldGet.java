package org.cyk.utility.field;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Set;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.string.StringLocation;
import org.cyk.utility.value.ValueUsageType;

public interface FieldGet extends FunctionWithPropertiesAsInput<Collection<Field>> {

	Boolean getIsRecursive();
	FieldGet setIsRecursive(Boolean value);
	
	Class<?> getClazz();
	FieldGet setClazz(Class<?> aClass);
	
	String getToken();
	FieldGet setToken(String token);
	
	StringLocation getTokenLocation();
	FieldGet setTokenLocation(StringLocation stringLocation);
	
	FieldName getFieldName();
	FieldGet setFieldName(FieldName fieldName);
	
	ValueUsageType getValueUsageType();
	FieldGet setValueUsageType(ValueUsageType valueUsageType);
	
	Set<Integer> getModifiers();
	FieldGet setModifiers(Set<Integer> modifiers);
	FieldGet addModifiers(Collection<Integer> modifiers);
	FieldGet addModifiers(Integer...modifiers);
	
	Set<Class<?>> getAnnotationClasses();
	FieldGet setAnnotationClasses(Set<Class<?>> annotationClasses);
	FieldGet addAnnotationClasses(Collection<Class<?>> annotationClasses);
	FieldGet addAnnotationClasses(Class<?>...annotationClasses);
	
	FieldGet execute(Class<?> aClass);
	FieldGet execute(Class<?> aClass,String name);
	
}
