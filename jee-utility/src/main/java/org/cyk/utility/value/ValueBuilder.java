package org.cyk.utility.value;

import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.function.FunctionWithPropertiesAsInput;

@Deprecated
public interface ValueBuilder<VALUE> extends FunctionWithPropertiesAsInput<VALUE> {
	
	ValueBuilder<VALUE> setValue(VALUE value);
	VALUE getValue();
	
	FieldValueGetter getFieldValueGetter();
	ValueBuilder<VALUE> setFieldValueGetter(FieldValueGetter fieldValueGetter);
	
}
