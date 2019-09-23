package org.cyk.utility.assertion;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.assertion.Assertion;
import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.number.NumberHelper;

@Dependent @Deprecated
public class AssertionBuilderComparisonImpl extends AbstractAssertionBuilderImpl implements AssertionBuilderComparison, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected Boolean __computeValue__(Assertion assertion, Boolean isAffirmation) {	
		Boolean value = super.__computeValue__(assertion, isAffirmation);
		if(value == null){
			Number number1 = (Number) getAssertedValue1().getValueFromSource();
			Number number2 = (Number) getAssertedValue2().getValueFromSource();
			ComparisonOperator operator = getOperator();
			if(operator == null)
				__injectThrowableHelper__().throwRuntimeException(getClass()+" : operator is required.");
			value = __inject__(NumberHelper.class).compare(number1, number2, operator);
		}
		return value;
	}
	
	@Override
	protected String __computeMessageWhenValueIsNotTrue__(Assertion assertion,Boolean isAffirmation) {
		String message = "la comparaison n'est pas correcte : "+getAssertedValue1().getValueFromSource()+" "+getOperator()+" "+getAssertedValue2().getValueFromSource(); //Boolean.TRUE.equals(isAffirmation) ? "la valeur doit être nulle" : "la valeur ne doit pas être nulle";
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
	public AssertionValue getAssertedValue1() {
		return (AssertionValue) getProperties().getFromPath(Properties.ASSERTION,Properties.VALUE,Properties._1);
	}
	
	@Override
	public AssertionValue getAssertedValue1(Boolean instanciateIfNull) {
		AssertionValue assertionValue = getAssertedValue1();
		if(assertionValue == null && Boolean.TRUE.equals(instanciateIfNull)){
			setAssertedValue1(assertionValue = __inject__(AssertionValue.class).setParent(this));
		}
		return assertionValue;
	}
	
	@Override
	public AssertionBuilderComparison setAssertedValue1(AssertionValue assertionValue) {
		getProperties().setFromPath(new Object[]{Properties.ASSERTION,Properties.VALUE,Properties._1}, assertionValue);
		return this;
	}
	
	@Override
	public AssertionValue getAssertedValue2() {
		return (AssertionValue) getProperties().getFromPath(Properties.ASSERTION,Properties.VALUE,Properties._2);
	}
	
	@Override
	public AssertionValue getAssertedValue2(Boolean instanciateIfNull) {
		AssertionValue assertionValue = getAssertedValue2();
		if(assertionValue == null && Boolean.TRUE.equals(instanciateIfNull)){
			setAssertedValue2(assertionValue = __inject__(AssertionValue.class).setParent(this));
		}
		return assertionValue;
	}
	
	@Override
	public AssertionBuilderComparison setAssertedValue2(AssertionValue assertionValue) {
		getProperties().setFromPath(new Object[]{Properties.ASSERTION,Properties.VALUE,Properties._2}, assertionValue);
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
