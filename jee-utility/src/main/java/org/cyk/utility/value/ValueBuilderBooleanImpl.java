package org.cyk.utility.value;

import java.io.Serializable;

import org.cyk.utility.field.FieldValueGetter;

public class ValueBuilderBooleanImpl extends AbstractValueBuilderImpl<Boolean> implements ValueBuilderBoolean, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public ValueBuilderBoolean setFieldValueGetter(FieldValueGetter fieldValueGetter) {
		super.setFieldValueGetter(fieldValueGetter);
		return this;
	}

}
