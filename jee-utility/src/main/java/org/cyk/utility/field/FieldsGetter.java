package org.cyk.utility.field;

import java.util.Collection;
import java.util.Set;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.regularexpression.RegularExpressionInstance;
import org.cyk.utility.string.StringLocatable;
import org.cyk.utility.string.StringLocatables;
import org.cyk.utility.string.StringLocation;
import org.cyk.utility.value.ValueUsageType;

public interface FieldsGetter extends FunctionWithPropertiesAsInput<Fields> {

	Boolean getIsRecursive();
	FieldsGetter setIsRecursive(Boolean value);
	
	Boolean getIsInheritanceFirst();
	FieldsGetter setIsInheritanceFirst(Boolean isInheritanceFirst);
	
	Class<?> getClazz();
	FieldsGetter setClazz(Class<?> aClass);
	
	RegularExpressionInstance getNameRegularExpression();
	RegularExpressionInstance getNameRegularExpression(Boolean injectIfNull);
	FieldsGetter setNameRegularExpression(RegularExpressionInstance nameRegularExpression);
	
	StringLocatables getNameTokens();
	StringLocatables getNameTokens(Boolean injectIfNull);
	FieldsGetter setNameTokens(StringLocatables nameTokens);
	FieldsGetter addNameTokens(Collection<StringLocatable> nameTokens);
	FieldsGetter addNameTokens(StringLocatable...nameTokens);
	FieldsGetter addNameToken(String string,StringLocation location);
	FieldsGetter addNameToken(String string);
	FieldsGetter addNameToken(FieldName fieldName,ValueUsageType valueUsageType);
	
	String getToken();
	FieldsGetter setToken(String token);
	
	StringLocation getTokenLocation();
	FieldsGetter setTokenLocation(StringLocation stringLocation);
	
	FieldName getFieldName();
	FieldsGetter setFieldName(FieldName fieldName);
	
	ValueUsageType getValueUsageType();
	FieldsGetter setValueUsageType(ValueUsageType valueUsageType);
	
	Set<Integer> getModifiers();
	FieldsGetter setModifiers(Set<Integer> modifiers);
	FieldsGetter addModifiers(Collection<Integer> modifiers);
	FieldsGetter addModifiers(Integer...modifiers);
	
	Set<Class<?>> getAnnotationClasses();
	FieldsGetter setAnnotationClasses(Set<Class<?>> annotationClasses);
	FieldsGetter addAnnotationClasses(Collection<Class<?>> annotationClasses);
	FieldsGetter addAnnotationClasses(Class<?>...annotationClasses);
	
	FieldsGetter execute(Class<?> aClass);
	FieldsGetter execute(Class<?> aClass,String name);
	
}
