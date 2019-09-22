package org.cyk.utility.assertion;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.field.FieldHelperImpl;
import org.cyk.utility.field.FieldValueGetter;

@Dependent @Deprecated
public class AssertionValueImpl extends AbstractObject implements AssertionValue, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public FieldValueGetter getFieldValueGetter() {
		return (FieldValueGetter) getProperties().getFromPath(Properties.FIELD,Properties.VALUE,Properties.GETTER);
	}
	
	@Override
	public AssertionValue setFieldValueGetter(FieldValueGetter fieldValueGetter) {
		getProperties().setFromPath(new Object[]{Properties.FIELD,Properties.VALUE,Properties.GETTER}, fieldValueGetter);
		if(getValueSource() == null)
			setValueSource(ValueSource.FIELD);
		return this;
	}
	
	@Override
	public AssertionValue setFieldValueGetter(Object object, String... names) {
		setFieldValueGetter(org.cyk.utility.__kernel__.field.FieldHelper.read(object,names));
		return this;
	}
	
	@Override
	public String getName() {
		return (String) getProperties().getFromPath(Properties.VALUE,Properties.NAME);
	}

	@Override
	public AssertionValue setName(String identifier) {
		getProperties().setFromPath(new Object[]{Properties.VALUE,Properties.NAME}, identifier);
		return this;
	}
	
	@Override
	public Object getValue() {
		return getProperties().getFromPath(Properties.VALUE,Properties.__THIS__);
	}

	@Override
	public AssertionValue setValue(Object value) {
		getProperties().setFromPath(new Object[]{Properties.VALUE,Properties.__THIS__}, value);
		if(getValueSource() == null)
			setValueSource(ValueSource.LITERAL);
		return this;
	}
	
	@Override
	public ValueSource getValueSource() {
		return (ValueSource) getProperties().getFromPath(Properties.VALUE,Properties.SOURCE);
	}
	
	@Override
	public AssertionValue setValueSource(ValueSource source) {
		getProperties().setFromPath(new Object[]{Properties.VALUE,Properties.SOURCE}, source);
		return this;
	}
	
	@Override
	public Object getValueFromSource() {
		Object value = null;
		ValueSource source = getValueSource();
		if(source == null)
			source = ValueSource.LITERAL;
		if(ValueSource.LITERAL.equals(source)){
			value = getValue();
		}else if(ValueSource.FIELD.equals(source)){
			FieldValueGetter fieldValueGetter = getFieldValueGetter();
			if(fieldValueGetter!=null)
				value = fieldValueGetter.execute().getOutput();
		}
		return value;
	}
	
	@Override
	public AssertionBuilder getParent() {
		return (AssertionBuilder) super.getParent();
	}
	
	@Override
	public AssertionValue setParent(Object parent) {
		return (AssertionValue) super.setParent(parent);
	}
}
