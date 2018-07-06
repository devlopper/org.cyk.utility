package org.cyk.utility.field;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Set;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.string.StringLocation;
import org.cyk.utility.value.ValueUsageType;

public interface FieldGetter extends FunctionWithPropertiesAsInput<Collection<Field>> {

	Boolean getIsRecursive();
	FieldGetter setIsRecursive(Boolean value);
	
	Class<?> getClazz();
	FieldGetter setClazz(Class<?> aClass);
	
	String getToken();
	FieldGetter setToken(String token);
	
	StringLocation getTokenLocation();
	FieldGetter setTokenLocation(StringLocation stringLocation);
	
	FieldName getFieldName();
	FieldGetter setFieldName(FieldName fieldName);
	
	ValueUsageType getValueUsageType();
	FieldGetter setValueUsageType(ValueUsageType valueUsageType);
	
	Set<Integer> getModifiers();
	FieldGetter setModifiers(Set<Integer> modifiers);
	FieldGetter addModifiers(Collection<Integer> modifiers);
	FieldGetter addModifiers(Integer...modifiers);
	
	Set<Class<?>> getAnnotationClasses();
	FieldGetter setAnnotationClasses(Set<Class<?>> annotationClasses);
	FieldGetter addAnnotationClasses(Collection<Class<?>> annotationClasses);
	FieldGetter addAnnotationClasses(Class<?>...annotationClasses);
	
	FieldGetter execute(Class<?> aClass);
	FieldGetter execute(Class<?> aClass,String name);
	
}
