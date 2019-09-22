package org.cyk.utility.assertion;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.assertion.Assertion;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.field.FieldHelperImpl;
import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.internationalization.InternalizationStringBuilder;

@Dependent @Deprecated
public class AssertionBuilderNullImpl extends AbstractAssertionBuilderImpl implements AssertionBuilderNull, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected Boolean __computeValue__(Assertion assertion, Boolean isAffirmation) {	
		Boolean value = super.__computeValue__(assertion, isAffirmation);
		if(value == null){
			AssertionValue assertionValue = getAssertedValue();
			if(assertionValue!=null){
				FieldValueGetter fieldValueGetter = assertionValue.getFieldValueGetter();
				if(fieldValueGetter != null){
					value = fieldValueGetter.execute().getOutput() == null;
				}
			}
		}
		return value;
	}
	
	@Override
	protected String __computeMessageWhenValueIsNotTrue__(Assertion assertion,Boolean isAffirmation) {
		String valueName = getValueName();
		if(__injectStringHelper__().isBlank(valueName)) {
			InternalizationStringBuilder valueNameInternalizationStringBuilder = __inject__(InternalizationStringBuilder.class);
			AssertionValue assertionValue = getAssertedValue();
			if(assertionValue!=null){
				FieldValueGetter fieldValueGetter = assertionValue.getFieldValueGetter();
				if(fieldValueGetter != null){
					if(fieldValueGetter.getField() == null)
						valueNameInternalizationStringBuilder.setKey(fieldValueGetter.getFieldName());
					else
						valueNameInternalizationStringBuilder.setKey(fieldValueGetter.getField().getName());
				}
			}
			valueName = valueNameInternalizationStringBuilder.execute().getOutput();	
		}else
			valueName = "????";
		//TODO message format must be from i18n file
		String message = "la valeur de <<"+valueName+">> "+(Boolean.TRUE.equals(isAffirmation) ? "doit être nulle" : "ne doit pas être nulle");
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
		//return (FieldValueGetter) getProperties().getFromPath(Properties.FIELD,Properties.VALUE,Properties.GETTER);
		return getAssertedValue(Boolean.TRUE).getFieldValueGetter();
	}
	
	@Override
	public AssertionBuilderNull setFieldValueGetter(FieldValueGetter fieldValueGetter) {
		getProperties().setFromPath(new Object[]{Properties.FIELD,Properties.VALUE,Properties.GETTER}, fieldValueGetter);
		getAssertedValue(Boolean.TRUE).setFieldValueGetter(fieldValueGetter);
		return this;
	}
	
	@Override
	public AssertionBuilderNull setFieldValueGetter(Object object, String... names) {
		setFieldValueGetter(org.cyk.utility.__kernel__.field.FieldHelper.read(object,names));
		getAssertedValue(Boolean.TRUE).setFieldValueGetter(object, names);
		return this;
	}
	
	@Override
	public String getValueName() {
		return getAssertedValue(Boolean.TRUE).getName();
		//return (String) getProperties().getFromPath(Properties.VALUE,Properties.NAME);
	}

	@Override
	public AssertionBuilderNull setValueName(String identifier) {
		getProperties().setFromPath(new Object[]{Properties.VALUE,Properties.NAME}, identifier);
		getAssertedValue(Boolean.TRUE).setName(identifier);
		return this;
	}
	
	@Override
	public AssertionValue getAssertedValue() {
		return (AssertionValue) getProperties().getFromPath(Properties.ASSERTION,Properties.VALUE);
	}
	
	@Override
	public AssertionValue getAssertedValue(Boolean instanciateIfNull) {
		AssertionValue assertionValue = getAssertedValue();
		if(assertionValue == null && Boolean.TRUE.equals(instanciateIfNull)){
			setAssertedValue(assertionValue = __inject__(AssertionValue.class).setParent(this));
		}
		return assertionValue;
	}
	
	@Override
	public AssertionBuilderNull setAssertedValue(AssertionValue assertionValue) {
		getProperties().setFromPath(new Object[]{Properties.ASSERTION,Properties.VALUE}, assertionValue);
		return this;
	}
	
	@Override
	public AssertionBuilderNull setIsAffirmation(Boolean value) {
		return (AssertionBuilderNull) super.setIsAffirmation(value);
	}

}
