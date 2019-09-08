package org.cyk.utility.assertion;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.cyk.utility.__kernel__.assertion.Assertion;
import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.field.FieldHelperImpl;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.internationalization.InternalizationHelperImpl;
import org.cyk.utility.log.LogEventEntityRepository;
import org.cyk.utility.log.LogLevel;
import org.cyk.utility.number.NumberHelper;
import org.cyk.utility.number.NumberHelperImpl;
import org.cyk.utility.value.Value;
import org.cyk.utility.value.ValueHelperImpl;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractAssertionHelperImpl extends AbstractHelper implements AssertionHelper, Serializable {
	private static final long serialVersionUID = 1L;

	private static void __processAssertion__(Assertion assertion,String messageIdentifierWhenValueIsNotTrue,String defaultDessageIdentifierWhenValueIsNotTrue,Object[] arguments,String identifier) {
		if(Boolean.TRUE.equals(assertion.getValue())){
			
		}else {
			messageIdentifierWhenValueIsNotTrue = ValueHelperImpl.__defaultToIfBlank__(messageIdentifierWhenValueIsNotTrue,defaultDessageIdentifierWhenValueIsNotTrue);
			assertion.setMessageWhenValueIsNotTrue(InternalizationHelperImpl.__buildInternalizationString__(messageIdentifierWhenValueIsNotTrue, arguments, null, null));
			assertion.setIdentifier(String.format("assertion.%s", identifier));	
		}
	}
	
	public static Assertion __buildAssertionComparison__(Value value1,ComparisonOperator operator,Value value2,Boolean isAffirmation,String messageIdentifierWhenValueIsNotTrue) {
		ValueHelperImpl.__throwIfBlank__("assertion comparison value1", value1);
		ValueHelperImpl.__throwIfBlank__("assertion comparison value2", value2);
		ValueHelperImpl.__throwIfBlank__("assertion comparison operator", operator);
		isAffirmation = ValueHelperImpl.__defaultToIfNull__(isAffirmation,Boolean.TRUE);	
		Assertion assertion = __inject__(Assertion.class);		
		Number number1 = (Number) value1.get();
		Number number2 = (Number) value2.get();
		assertion.setValue(NumberHelperImpl.__compare__(number1, number2, operator));	
		if(!isAffirmation)
			assertion.setValue(!assertion.getValue());
		__processAssertion__(assertion, messageIdentifierWhenValueIsNotTrue,"assertion.comparison", new Object[] {number1,operator.getSymbol(),number2}, "comparison."+operator.name().toLowerCase());
		return assertion;
	}

	public static Assertion __buildAssertionComparison__(Value value1,ComparisonOperator operator,Value value2) {
		return __buildAssertionComparison__(value1, operator, value2, Boolean.TRUE, null);
	}
	
	public static Assertion __buildAssertionNull__(Value value,Boolean isAffirmation,String messageIdentifierWhenValueIsNotTrue) {
		ValueHelperImpl.__throwIfBlank__("assertion null value", value);
		isAffirmation = ValueHelperImpl.__defaultToIfNull__(isAffirmation,Boolean.TRUE);	
		Assertion assertion = __inject__(Assertion.class);		
		Object __value__ = value.get();
		assertion.setValue(__value__ == null);	
		if(!isAffirmation)
			assertion.setValue(!assertion.getValue());
		__processAssertion__(assertion, messageIdentifierWhenValueIsNotTrue,"assertion.null", null, "null");
		return assertion;
	}
	
	public static Assertion __buildAssertionNull__(Value value) {
		return __buildAssertionNull__(value, Boolean.TRUE, null);
	}
	
	@Override
	public AssertionHelper assertNotNull(String message, Object object,FieldName fieldName, ValueUsageType valueUsageType) {
		Field field = FieldHelperImpl.__getFieldByName__(object.getClass(), fieldName, valueUsageType);
		if(field != null)
			assertNotNull(message, FieldHelperImpl.__readFieldValue__(object, field));
		return this;
	}
	
	@Override
	public AssertionHelper assertNotNull(Object object,FieldName fieldName, ValueUsageType valueUsageType) {
		return assertNotNull(valueUsageType+" "+fieldName+" value of object "+object+" is null", object, fieldName, valueUsageType);
	}
	
	@Override
	public AssertionHelper assertEqualsNumber(String message, Object expected, Object actual) {
		assertEquals(message, __inject__(NumberHelper.class).getBigDecimal(expected), __inject__(NumberHelper.class).getBigDecimal(actual));
		return this;
	}
	
	@Override
	public AssertionHelper assertEqualsNumber(Object expected, Object actual) {
		assertEquals(__inject__(NumberHelper.class).getBigDecimal(expected), __inject__(NumberHelper.class).getBigDecimal(actual));
		return this;
	}
	
	@Override
	public AssertionHelper assertNotEqualsNumber(String message, Object expected, Object actual) {
		assertNotEquals(message, __inject__(NumberHelper.class).getBigDecimal(expected), __inject__(NumberHelper.class).getBigDecimal(actual));
		return this;
	}
	
	@Override
	public AssertionHelper assertNotEqualsNumber(Object expected, Object actual) {
		assertNotEquals(__inject__(NumberHelper.class).getBigDecimal(expected), __inject__(NumberHelper.class).getBigDecimal(actual));
		return this;
	}

	@Override
	public AssertionHelper assertNullLastLogEvent(String message) {
		if(Boolean.TRUE.equals(getIsLogAssertionEnable()))
			assertNull(message,__inject__(LogEventEntityRepository.class).getLastMessage());
		return this;
	}
	
	@Override
	public AssertionHelper assertNullLastLogEvent() {
		return assertNullLastLogEvent("Log message has been found");
	}
	
	@Override
	public AssertionHelper assertEqualsLastLogEventMessage(String message,String expected){
		if(Boolean.TRUE.equals(getIsLogAssertionEnable()))
			assertEquals(message,expected, __inject__(LogEventEntityRepository.class).getLast().getMessage());
		//assertEquals(org.apache.logging.log4j.Level.INFO, log4j2Appender.getLastEvent().getLevel());	
		return this;
	}
	
	@Override
	public AssertionHelper assertEqualsLastLogEventMessage(String expected){
		if(Boolean.TRUE.equals(getIsLogAssertionEnable()))
			assertEquals(expected, __inject__(LogEventEntityRepository.class).getLast().getMessage());
		return this;
	}

	@Override
	public AssertionHelper assertEqualsLastLogEventLevel(String message, LogLevel expected) {
		if(Boolean.TRUE.equals(getIsLogAssertionEnable()))
			assertEquals(message,expected, __inject__(LogEventEntityRepository.class).getLast().getLevel());
		return this;
	}

	@Override
	public AssertionHelper assertEqualsLastLogEventLevel(LogLevel expected) {
		if(Boolean.TRUE.equals(getIsLogAssertionEnable()))
			assertEquals(expected, __inject__(LogEventEntityRepository.class).getLast().getLevel());
		return this;
	}
	
	@Override
	public AssertionHelper assertEqualsLastLogEventMarker(String message, Object expected) {
		if(Boolean.TRUE.equals(getIsLogAssertionEnable()))
			assertEquals(message,expected, __inject__(LogEventEntityRepository.class).getLast().getMarker());
		return this;
	}
	
	@Override
	public AssertionHelper assertEqualsLastLogEventMarker(Object expected) {
		if(Boolean.TRUE.equals(getIsLogAssertionEnable()))
			assertEquals(expected, __inject__(LogEventEntityRepository.class).getLast().getMarker());
		return this;
	}
	
	@Override
	public AssertionHelper assertEqualsLastLogEventProperties(Properties expected) {
		if(Boolean.TRUE.equals(getIsLogAssertionEnable())){
			if(expected.has(Properties.LOG_LEVEL))
				assertEqualsLastLogEventLevel((LogLevel) expected.get(Properties.LOG_LEVEL));
			if(expected.has(Properties.MARKER))
				assertEqualsLastLogEventMarker(expected.get(Properties.MARKER));
			if(expected.has(Properties.MESSAGE))
				assertEqualsLastLogEventMessage((String) expected.get(Properties.MESSAGE));
		}
		return this;
	}
	
	@Override
	public AssertionHelper assertEqualsLogEventCount(String message, Object expected) {
		if(Boolean.TRUE.equals(getIsLogAssertionEnable()))
			assertEqualsNumber(message, expected, __inject__(LogEventEntityRepository.class).countAll());
		return null;
	}
	
	@Override
	public AssertionHelper assertEqualsLogEventCount(Object expected) {
		if(Boolean.TRUE.equals(getIsLogAssertionEnable()))
			assertEqualsNumber(expected, __inject__(LogEventEntityRepository.class).countAll());
		return null;
	}
	
	@Override
	public AssertionHelper assertContainsLastLogEventMessage(String message, String expected) {
		if(Boolean.TRUE.equals(getIsLogAssertionEnable()))
			assertThat(__inject__(LogEventEntityRepository.class).getLastMessage()).contains(expected);
		return this;
	}
	
	@Override
	public AssertionHelper assertContainsLastLogEventMessage(String expected) {
		if(Boolean.TRUE.equals(getIsLogAssertionEnable()))
			assertThat(__inject__(LogEventEntityRepository.class).getLastMessage()).contains(expected);
		return this;
	}
	
	@Override
	public AssertionHelper assertStartsWithLastLogEventMessage(String message, String expected) {
		if(Boolean.TRUE.equals(getIsLogAssertionEnable()))
			assertThat(__inject__(LogEventEntityRepository.class).getLastMessage()).startsWith(expected);
		return this;
	}
	
	@Override
	public AssertionHelper assertStartsWithLastLogEventMessage(String expected) {
		if(Boolean.TRUE.equals(getIsLogAssertionEnable()))
			assertThat(__inject__(LogEventEntityRepository.class).getLastMessage()).startsWith(expected);
		return this;
	}
	
	/**/
	
	@Override
	public Boolean getIsLogAssertionEnable() {
		return (Boolean) getProperties().getFromPath(Properties.IS,Properties.LOG);
	}
	
	@Override
	public AssertionHelper setIsLogAssertionEnable(Boolean value) {
		getProperties().setFromPath(new Object[]{Properties.IS,Properties.LOG}, value);
		return this;
	}
}
