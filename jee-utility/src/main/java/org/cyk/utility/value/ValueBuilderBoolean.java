package org.cyk.utility.value;

import org.cyk.utility.field.FieldValueGetter;

@Deprecated
public interface ValueBuilderBoolean extends ValueBuilder<Boolean> {

	ValueBuilderBoolean setFieldValueGetter(FieldValueGetter fieldValueGetter);
	
}
