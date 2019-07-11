package org.cyk.utility.field;

import java.util.Map;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndVoidAsOutput;

public interface FieldValueCopy extends FunctionWithPropertiesAsInputAndVoidAsOutput {

	FieldValueCopy execute(Object source,Object destination,String fieldName);
	FieldValueCopy execute(Object source,Object destination,Map<String,String> fieldNameMap);
	FieldValueCopy execute(Object source,Object destination);
	
	FieldValueGetter getValueGetter();
	FieldValueGetter getValueGetter(Boolean injectIfNull);
	FieldValueCopy setValueGetter(FieldValueGetter valueGetter);
	
	FieldValueSetter getValueSetter();
	FieldValueSetter getValueSetter(Boolean injectIfNull);
	FieldValueCopy setValueSetter(FieldValueSetter valueSetter);
	
	Map<String,String> getFieldNameMap();
	FieldValueCopy setFieldNameMap(Map<String,String> fieldNameMap);
	FieldValueCopy setFieldName(String source,String destination);
	FieldValueCopy setFieldName(String fieldName);
	
	Boolean getIsAutomaticallyDetectFields();
	FieldValueCopy setIsAutomaticallyDetectFields(Boolean isAutomaticallyDetectFields);
	
	Boolean getIsOverridable();
	FieldValueCopy setIsOverridable(Boolean isOverridable);
	
	FieldValueCopy setSource(Object source);
	FieldValueCopy setDestination(Object destination);
	
}
