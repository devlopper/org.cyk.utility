package org.cyk.utility.client.controller.component;

import java.lang.reflect.Field;

import org.cyk.utility.annotation.Annotations;
import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.string.Strings;

@SuppressWarnings("rawtypes")
public interface ComponentBuilderClassGetter extends FunctionWithPropertiesAsInput<Class> {

	Class<?> getClazz();
	ComponentBuilderClassGetter setClazz(Class<?> clazz);
	
	Strings getFieldNameStrings();
	Strings getFieldNameStrings(Boolean injectIfNull);
	ComponentBuilderClassGetter setFieldNameStrings(Strings fieldNameStrings);
	
	Field getField();
	ComponentBuilderClassGetter setField(Field field);
	
	Annotations getAnnotations();
	Annotations getAnnotations(Boolean injectIfNull);
	ComponentBuilderClassGetter setAnnotations(Annotations annotations);
	
}
