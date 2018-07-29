package org.cyk.utility.assertion;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.field.FieldValueGetter;

public class AssertionBuilderNullImpl extends AbstractAssertionBuilderImpl implements AssertionBuilderNull, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected Boolean __computeValue__(Assertion assertion, Boolean isAffirmation) {	
		Boolean value = super.__computeValue__(assertion, isAffirmation);
		if(value == null){
			FieldValueGetter fieldValueGetter = getFieldValueGetter();
			if(fieldValueGetter != null){
				value = fieldValueGetter.execute().getOutput() == null;
			}
		}
		return value;
	}
	
	@Override
	protected String __computeMessageWhenValueIsNotTrue__(Assertion assertion,Boolean isAffirmation) {
		String message = Boolean.TRUE.equals(isAffirmation) ? "la valeur doit être nulle" : "la valeur ne doit pas être nulle";
		return message;
	}
	
/*
	@Override
	protected Boolean __computeConditionValue__(Condition condition) {
		Boolean value = getValue();
		if(value == null)
			value = getFieldValueGetter().execute().getOutput() == null;
		return value;
	}
	*/
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
	public AssertionBuilderNull setFieldValueGetter(FieldValueGetter fieldValueGetter) {
		getProperties().setFromPath(new Object[]{Properties.FIELD,Properties.VALUE,Properties.GETTER}, fieldValueGetter);
		return this;
	}
	
	@Override
	public AssertionBuilderNull setFieldValueGetter(Object object, String... names) {
		setFieldValueGetter(__inject__(FieldValueGetter.class).setObject(object).setField(names));
		return this;
	}
	
	@Override
	public String getValueName() {
		return (String) getProperties().getFromPath(Properties.VALUE,Properties.NAME);
	}

	@Override
	public AssertionBuilderNull setValueName(String identifier) {
		getProperties().setFromPath(new Object[]{Properties.VALUE,Properties.NAME}, identifier);
		return this;
	}
	
	@Override
	public AssertionBuilderNull setIsAffirmation(Boolean value) {
		return (AssertionBuilderNull) super.setIsAffirmation(value);
	}

}
