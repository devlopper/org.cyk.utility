package org.cyk.utility.condition;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.field.FieldValueGetter;

public class ConditionBuilderNullImpl extends AbstractConditionBuilderImpl implements ConditionBuilderNull, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Boolean __computeConditionValue__(Condition condition) {
		Boolean value = getValue();
		if(value == null)
			value = getFieldValueGetter().execute().getOutput() == null;
		return value;
	}
	/*
	@Override
	public Object getValue() {
		return getProperties().getFromPath(Properties.VALUE,Properties.__THIS__);
	}

	@Override
	public ConditionBuilderNull setValue(Object value) {
		getProperties().setFromPath(new Object[]{Properties.VALUE,Properties.__THIS__}, value);
		return this;
	}*/

	@Override
	public FieldValueGetter getFieldValueGetter() {
		return (FieldValueGetter) getProperties().getFromPath(Properties.FIELD,Properties.VALUE,Properties.GETTER);
	}
	
	@Override
	public ConditionBuilderNull setFieldValueGetter(FieldValueGetter fieldValueGetter) {
		getProperties().setFromPath(new Object[]{Properties.FIELD,Properties.VALUE,Properties.GETTER}, fieldValueGetter);
		return this;
	}
	
	@Override
	public String getValueName() {
		return (String) getProperties().getFromPath(Properties.VALUE,Properties.NAME);
	}

	@Override
	public ConditionBuilderNull setValueName(String identifier) {
		getProperties().setFromPath(new Object[]{Properties.VALUE,Properties.NAME}, identifier);
		return this;
	}

}
