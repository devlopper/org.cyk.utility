package org.cyk.utility.value;

import org.cyk.utility.field.FieldValueGetter;

public interface ValueBuilderBoolean extends ValueBuilder<Boolean> {

	ValueBuilderBoolean setFieldValueGetter(FieldValueGetter fieldValueGetter);
	
}
