package org.cyk.utility.field;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface FieldValueCopyFieldsGetter extends FunctionWithPropertiesAsInput<Fields> {

	Class<?> getSourceClass();
	FieldValueCopyFieldsGetter setSourceClass(Class<?> sourceClass);
	
	Class<?> getDestinationClass();
	FieldValueCopyFieldsGetter setDestinationClass(Class<?> destinationClass);
	
	FieldGetter getFieldGetter();
	FieldGetter getFieldGetter(Boolean injectIfNull);
	FieldValueCopyFieldsGetter setFieldGetter(FieldGetter fieldGetter);
	
	FieldInstanceValue getSource();
	FieldValueCopyFieldsGetter setSource(FieldInstanceValue source);
	
	FieldInstanceValue getDestination();
	FieldValueCopyFieldsGetter setDestination(FieldInstanceValue destination);

	
}
