package org.cyk.utility.field;

import java.util.Collection;
import java.util.Set;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.regularexpression.RegularExpressionInstance;
import org.cyk.utility.string.StringLocation;
import org.cyk.utility.value.ValueUsageType;

public interface FieldGetter extends FunctionWithPropertiesAsInput<Fields> {

	Boolean getIsRecursive();
	FieldGetter setIsRecursive(Boolean value);
	
	Boolean getIsInheritanceFirst();
	FieldGetter setIsInheritanceFirst(Boolean isInheritanceFirst);
	
	Class<?> getClazz();
	FieldGetter setClazz(Class<?> aClass);
	
	RegularExpressionInstance getNameRegularExpression();
	RegularExpressionInstance getNameRegularExpression(Boolean injectIfNull);
	FieldGetter setNameRegularExpression(RegularExpressionInstance nameRegularExpression);
	
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
