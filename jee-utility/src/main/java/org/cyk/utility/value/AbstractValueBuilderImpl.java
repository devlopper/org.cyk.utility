package org.cyk.utility.value;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public abstract class AbstractValueBuilderImpl<VALUE> extends AbstractFunctionWithPropertiesAsInputImpl<VALUE> implements ValueBuilder<VALUE>,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected VALUE __execute__() throws Exception {
		VALUE value = getValue();
		if(value == null){
			FieldValueGetter fieldValueGetter = getFieldValueGetter();
			if(fieldValueGetter!=null)
				value = (VALUE) fieldValueGetter.execute().getOutput();
		}
		return value;
	}
	
	@Override
	public ValueBuilder<VALUE> setValue(VALUE value) {
		getProperties().setValue(value);
		return this;
	}
	
	@Override
	public VALUE getValue() {
		return (VALUE) getProperties().getValue();
	}
	
	@Override
	public FieldValueGetter getFieldValueGetter() {
		return (FieldValueGetter) getProperties().getFromPath(Properties.FIELD,Properties.VALUE,Properties.GETTER);
	}
	
	@Override
	public ValueBuilder<VALUE> setFieldValueGetter(FieldValueGetter fieldValueGetter) {
		getProperties().setFromPath(new Object[]{Properties.FIELD,Properties.VALUE,Properties.GETTER}, fieldValueGetter);
		return this;
	}
	
}
