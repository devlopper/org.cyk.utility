package org.cyk.utility.assertion;

import java.io.Serializable;

import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.number.NumberHelper;

public class AssertionBuilderComparisonImpl extends AbstractAssertionBuilderImpl implements AssertionBuilderComparison, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected Boolean __computeValue__(Assertion assertion, Boolean isAffirmation) {	
		Boolean value = super.__computeValue__(assertion, isAffirmation);
		if(value == null){
			//TODO think better to handle value from field1 and field2. we might group it to a bean
			FieldValueGetter fieldValueGetter1 = getFieldValueGetter1();
			FieldValueGetter fieldValueGetter2 = getFieldValueGetter2();
			Number number1 = (Number) fieldValueGetter1.execute().getOutput();
			Number number2 = (Number) fieldValueGetter2.execute().getOutput();
			ComparisonOperator operator = getOperator();
			__inject__(NumberHelper.class).compare(number1, number2, operator);
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
	public FieldValueGetter getFieldValueGetter1() {
		return (FieldValueGetter) getProperties().getFromPath(Properties.FIELD,Properties.VALUE,Properties.GETTER,Properties._1);
	}
	
	@Override
	public AssertionBuilderComparison setFieldValueGetter1(FieldValueGetter fieldValueGetter) {
		getProperties().setFromPath(new Object[]{Properties.FIELD,Properties.VALUE,Properties.GETTER,Properties._1}, fieldValueGetter);
		return this;
	}
	
	@Override
	public AssertionBuilderComparison setFieldValueGetter1(Object object, String... names) {
		setFieldValueGetter1(__inject__(FieldValueGetter.class).setObject(object).setField(names));
		return this;
	}
	
	@Override
	public FieldValueGetter getFieldValueGetter2() {
		return (FieldValueGetter) getProperties().getFromPath(Properties.FIELD,Properties.VALUE,Properties.GETTER,Properties._2);
	}
	
	@Override
	public AssertionBuilderComparison setFieldValueGetter2(FieldValueGetter fieldValueGetter) {
		getProperties().setFromPath(new Object[]{Properties.FIELD,Properties.VALUE,Properties.GETTER,Properties._2}, fieldValueGetter);
		return this;
	}
	
	@Override
	public AssertionBuilderComparison setFieldValueGetter2(Object object, String... names) {
		setFieldValueGetter1(__inject__(FieldValueGetter.class).setObject(object).setField(names));
		return this;
	}
	
	@Override
	public ComparisonOperator getOperator() {
		return (ComparisonOperator) getProperties().getOperator();
	}
	
	@Override
	public AssertionBuilderComparison setOperator(ComparisonOperator operator) {
		getProperties().setOperator(operator);
		return this;
	}
	
	@Override
	public AssertionBuilderComparison setIsAffirmation(Boolean value) {
		return (AssertionBuilderComparison) super.setIsAffirmation(value);
	}

}
