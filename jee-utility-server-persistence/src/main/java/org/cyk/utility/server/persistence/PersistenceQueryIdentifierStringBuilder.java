package org.cyk.utility.server.persistence;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndStringAsOutput;

public interface PersistenceQueryIdentifierStringBuilder extends FunctionWithPropertiesAsInputAndStringAsOutput {

	String getClassSimpleName();
	PersistenceQueryIdentifierStringBuilder setClassSimpleName(String name);
	PersistenceQueryIdentifierStringBuilder setClassSimpleName(Class<?> aClass);
	
	String getName();
	PersistenceQueryIdentifierStringBuilder setName(String name);
	PersistenceQueryIdentifierStringBuilder setName(Field field);
	PersistenceQueryIdentifierStringBuilder setName(Method method);
	
	Object getDerivedFromQueryIdentifier();
	PersistenceQueryIdentifierStringBuilder setDerivedFromQueryIdentifier(Object identifier);
	
	Boolean getIsDerivedFromQueryIdentifier();
	PersistenceQueryIdentifierStringBuilder setIsDerivedFromQueryIdentifier(Boolean value);
	
	Boolean getIsCountInstances();
	PersistenceQueryIdentifierStringBuilder setIsCountInstances(Boolean value);
}
