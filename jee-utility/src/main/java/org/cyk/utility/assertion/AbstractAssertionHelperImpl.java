package org.cyk.utility.assertion;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.cyk.utility.__kernel__.field.FieldName;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.log.LogEventEntityRepository;
import org.cyk.utility.log.LogLevel;
import org.cyk.utility.number.NumberHelper;

public abstract class AbstractAssertionHelperImpl extends AbstractHelper implements AssertionHelper, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public AssertionHelper assertNotNull(String message, Object object,FieldName fieldName, ValueUsageType valueUsageType) {
		Field field = org.cyk.utility.__kernel__.field.FieldHelper.getByName(object.getClass(), fieldName, valueUsageType);
		if(field != null)
			assertNotNull(message, org.cyk.utility.__kernel__.field.FieldHelper.read(object, field));
		return this;
	}
	
	@Override
	public AssertionHelper assertNotNull(Object object,FieldName fieldName, ValueUsageType valueUsageType) {
		return assertNotNull(valueUsageType+" "+fieldName+" value of object "+object+" is null", object, fieldName, valueUsageType);
	}
	
	@Override
	public AssertionHelper assertEqualsNumber(String message, Object expected, Object actual) {
		assertEquals(message, NumberHelper.getBigDecimal(expected), NumberHelper.getBigDecimal(actual));
		return this;
	}
	
	@Override
	public AssertionHelper assertEqualsNumber(Object expected, Object actual) {
		assertEquals(NumberHelper.getBigDecimal(expected), NumberHelper.getBigDecimal(actual));
		return this;
	}
	
	@Override
	public AssertionHelper assertNotEqualsNumber(String message, Object expected, Object actual) {
		assertNotEquals(message, NumberHelper.getBigDecimal(expected), NumberHelper.getBigDecimal(actual));
		return this;
	}
	
	@Override
	public AssertionHelper assertNotEqualsNumber(Object expected, Object actual) {
		assertNotEquals(NumberHelper.getBigDecimal(expected), NumberHelper.getBigDecimal(actual));
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
