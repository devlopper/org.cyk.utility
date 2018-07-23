package org.cyk.utility.server.persistence;

import java.lang.reflect.Field;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndStringAsOutput;

public interface PersistenceQueryIdentifierStringBuilder extends FunctionWithPropertiesAsInputAndStringAsOutput {

	String getClassSimpleName();
	PersistenceQueryIdentifierStringBuilder setClassSimpleName(String name);
	PersistenceQueryIdentifierStringBuilder setClassSimpleName(Class<?> aClass);
	
	String getFieldName();
	PersistenceQueryIdentifierStringBuilder setFieldName(String name);
	PersistenceQueryIdentifierStringBuilder setFieldName(Field field);
}
