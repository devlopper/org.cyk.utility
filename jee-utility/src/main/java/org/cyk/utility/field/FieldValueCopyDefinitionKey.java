package org.cyk.utility.field;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface FieldValueCopyDefinitionKey extends Objectable {

	FieldValueCopyDefinitionKey setSourceClass(Class<?> sourceClass);
	Class<?> getSourceClass();
	
	FieldValueCopyDefinitionKey setDestinationClass(Class<?> destinationClass);
	Class<?> getDestinationClass();
	
}
