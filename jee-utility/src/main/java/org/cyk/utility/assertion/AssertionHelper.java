package org.cyk.utility.assertion;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.assertion.Assertion;
import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.__kernel__.field.FieldName;
import org.cyk.utility.__kernel__.internationalization.InternationalizationHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.helper.Helper;
import org.cyk.utility.log.LogLevel;
import org.cyk.utility.number.NumberHelperImpl;
import org.cyk.utility.value.Value;
import org.cyk.utility.value.ValueHelperImpl;

public interface AssertionHelper extends Helper {

	static void processAssertion(Assertion assertion,String messageIdentifierWhenValueIsNotTrue,String defaultDessageIdentifierWhenValueIsNotTrue,Object[] arguments,String identifier) {
		if(Boolean.TRUE.equals(assertion.getValue())){
			
		}else {
			messageIdentifierWhenValueIsNotTrue = ValueHelperImpl.__defaultToIfBlank__(messageIdentifierWhenValueIsNotTrue,defaultDessageIdentifierWhenValueIsNotTrue);
			assertion.setMessageWhenValueIsNotTrue(InternationalizationHelper.buildString(messageIdentifierWhenValueIsNotTrue, arguments, null, null));
			assertion.setIdentifier(String.format("assertion.%s", identifier));	
		}
	}
	
	static Assertion buildAssertionComparison(Value value1,ComparisonOperator operator,Value value2,Boolean isAffirmation,String messageIdentifierWhenValueIsNotTrue) {
		ValueHelperImpl.__throwIfBlank__("assertion comparison value1", value1);
		ValueHelperImpl.__throwIfBlank__("assertion comparison value2", value2);
		ValueHelperImpl.__throwIfBlank__("assertion comparison operator", operator);
		isAffirmation = ValueHelperImpl.__defaultToIfNull__(isAffirmation,Boolean.TRUE);	
		Assertion assertion = DependencyInjection.inject(Assertion.class);		
		Number number1 = (Number) value1.get();
		Number number2 = (Number) value2.get();
		assertion.setValue(NumberHelperImpl.__compare__(number1, number2, operator));	
		if(!isAffirmation)
			assertion.setValue(!assertion.getValue());
		processAssertion(assertion, messageIdentifierWhenValueIsNotTrue,"assertion.comparison", new Object[] {number1,operator.getSymbol(),number2}, "comparison."+operator.name().toLowerCase());
		return assertion;
	}

	static Assertion buildAssertionComparison(Value value1,ComparisonOperator operator,Value value2) {
		return buildAssertionComparison(value1, operator, value2, Boolean.TRUE, null);
	}
	
	static Assertion buildAssertionNull(Value value,Boolean isAffirmation,String messageIdentifierWhenValueIsNotTrue) {
		ValueHelperImpl.__throwIfBlank__("assertion null value", value);
		isAffirmation = ValueHelperImpl.__defaultToIfNull__(isAffirmation,Boolean.TRUE);	
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
	
	AssertionHelper assertNull(String message,Object object);
	AssertionHelper assertNull(Object object);
	AssertionHelper assertNullLastLogEvent(String message);
	AssertionHelper assertNullLastLogEvent();
	
	AssertionHelper assertNotNull(String message,Object object);
	AssertionHelper assertNotNull(Object object);
	AssertionHelper assertNotNull(String message,Object object,FieldName fieldName,ValueUsageType valueUsageType);
	AssertionHelper assertNotNull(Object object,FieldName fieldName,ValueUsageType valueUsageType);
	
	AssertionHelper assertTrue(String message,Boolean condition);
	AssertionHelper assertTrue(Boolean condition);
	
	AssertionHelper assertFalse(String message,Boolean condition);
	AssertionHelper assertFalse(Boolean condition);
	
	AssertionHelper assertEquals(String message,Object expected,Object actual);
	AssertionHelper assertEquals(Object expected,Object actual);
	<T> AssertionHelper assertEqualsByFieldValue(T expected,T actual,String...fieldNames);
	<T> AssertionHelper assertEqualsByFieldValue(Properties expected,T actual);
	
	AssertionHelper assertNotEquals(String message,Object unexpected,Object actual);
	AssertionHelper assertNotEquals(Object unexpected,Object actual);
	
	AssertionHelper assertEqualsNumber(String message,Object expected,Object actual);
	AssertionHelper assertEqualsNumber(Object expected,Object actual);
	
	AssertionHelper assertNotEqualsNumber(String message,Object expected,Object actual);
	AssertionHelper assertNotEqualsNumber(Object expected,Object actual);
	
	AssertionHelper assertEqualsLogEventCount(String message,Object expected);
	AssertionHelper assertEqualsLogEventCount(Object expected);
	
	AssertionHelper assertEqualsLastLogEventLevel(String message,LogLevel expected);
	AssertionHelper assertEqualsLastLogEventLevel(LogLevel expected);
	
	AssertionHelper assertEqualsLastLogEventMessage(String message,String expected);
	AssertionHelper assertEqualsLastLogEventMessage(String expected);
	
	AssertionHelper assertStartsWithLastLogEventMessage(String message,String expected);
	AssertionHelper assertStartsWithLastLogEventMessage(String expected);
	
	AssertionHelper assertContainsLastLogEventMessage(String message,String expected);
	AssertionHelper assertContainsLastLogEventMessage(String expected);
	
	AssertionHelper assertEqualsLastLogEventMarker(String message,Object expected);
	AssertionHelper assertEqualsLastLogEventMarker(Object expected);
	
	AssertionHelper assertEqualsLastLogEventProperties(Properties expected);
	
	AssertionHelper setIsLogAssertionEnable(Boolean value);
	Boolean getIsLogAssertionEnable();
	
}
