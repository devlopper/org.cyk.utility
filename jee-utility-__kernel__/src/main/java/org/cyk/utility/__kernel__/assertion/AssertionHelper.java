package org.cyk.utility.__kernel__.assertion;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.__kernel__.internationalization.InternationalizationHelper;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.ValueHelper;

public interface AssertionHelper {

	static void processAssertion(Assertion assertion,String messageIdentifierWhenValueIsNotTrue,String defaultDessageIdentifierWhenValueIsNotTrue,Object[] arguments,String identifier) {
		if(Boolean.TRUE.equals(assertion.getValue())){
			
		}else {
			messageIdentifierWhenValueIsNotTrue = ValueHelper.defaultToIfBlank(messageIdentifierWhenValueIsNotTrue,defaultDessageIdentifierWhenValueIsNotTrue);
			assertion.setMessageWhenValueIsNotTrue(InternationalizationHelper.buildString(messageIdentifierWhenValueIsNotTrue, arguments, null, null));
			assertion.setIdentifier(String.format("assertion.%s", identifier));	
		}
	}
	
	static Assertion buildAssertionComparison(Value value1,ComparisonOperator operator,Value value2,Boolean isAffirmation,String messageIdentifierWhenValueIsNotTrue) {
		ValueHelper.throwIfBlank("assertion comparison value1", value1);
		ValueHelper.throwIfBlank("assertion comparison value2", value2);
		ValueHelper.throwIfBlank("assertion comparison operator", operator);
		isAffirmation = ValueHelper.defaultToIfNull(isAffirmation,Boolean.TRUE);	
		Assertion assertion = DependencyInjection.inject(Assertion.class);		
		Number number1 = (Number) value1.get();
		Number number2 = (Number) value2.get();
		assertion.setValue(NumberHelper.compare(number1, number2, operator));	
		if(!isAffirmation)
			assertion.setValue(!assertion.getValue());
		processAssertion(assertion, messageIdentifierWhenValueIsNotTrue,"assertion.comparison", new Object[] {number1,operator.getSymbol(),number2}, "comparison."+operator.name().toLowerCase());
		return assertion;
	}

	static Assertion buildAssertionComparison(Value value1,ComparisonOperator operator,Value value2) {
		return buildAssertionComparison(value1, operator, value2, Boolean.TRUE, null);
	}
	
	static Assertion buildAssertionNull(Value value,Boolean isAffirmation,String messageIdentifierWhenValueIsNotTrue) {
		ValueHelper.throwIfBlank("assertion null value", value);
		isAffirmation = ValueHelper.defaultToIfNull(isAffirmation,Boolean.TRUE);	
		Assertion assertion = DependencyInjection.inject(Assertion.class);		
		Object __value__ = value.get();
		assertion.setValue(__value__ == null);	
		if(!isAffirmation)
			assertion.setValue(!assertion.getValue());
		processAssertion(assertion, messageIdentifierWhenValueIsNotTrue,"assertion.null", null, "null");
		return assertion;
	}
	
	static Assertion buildAssertionNull(Value value) {
		return buildAssertionNull(value, Boolean.TRUE, null);
	}
	
}
