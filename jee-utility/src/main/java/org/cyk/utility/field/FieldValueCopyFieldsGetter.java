package org.cyk.utility.field;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface FieldValueCopyFieldsGetter extends FunctionWithPropertiesAsInput<Fields> {

	Class<?> getSourceClass();
	FieldValueCopyFieldsGetter setSourceClass(Class<?> sourceClass);
	
	Class<?> getDestinationClass();
	FieldValueCopyFieldsGetter setDestinationClass(Class<?> destinationClass);
	
	FieldsGetter getFieldGetter();
	FieldsGetter getFieldGetter(Boolean injectIfNull);
	FieldValueCopyFieldsGetter setFieldGetter(FieldsGetter fieldGetter);
	
	FieldInstanceValue getSource();
	FieldValueCopyFieldsGetter setSource(FieldInstanceValue source);
	
	FieldInstanceValue getDestination();
	FieldValueCopyFieldsGetter setDestination(FieldInstanceValue destination);

	
}
