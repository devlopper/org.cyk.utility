package org.cyk.utility.assertion;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.log.LogEventEntityRepository;
import org.cyk.utility.log.LogLevel;
import org.cyk.utility.number.NumberHelper;

public abstract class AbstractAssertionHelperImpl extends AbstractHelper implements AssertionHelper, Serializable {
	private static final long serialVersionUID = 1L;

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
	public AssertionHelper assertEqualsLastLogEventMessage(String message,String expected){
		assertEquals(message,expected, __inject__(LogEventEntityRepository.class).getLast().getMessage());
		//assertEquals(org.apache.logging.log4j.Level.INFO, log4j2Appender.getLastEvent().getLevel());	
		return this;
	}
	
	@Override
	public AssertionHelper assertEqualsLastLogEventMessage(String expected){
		assertEquals(expected, __inject__(LogEventEntityRepository.class).getLast().getMessage());
		return this;
	}

	@Override
	public AssertionHelper assertEqualsLastLogEventLevel(String message, LogLevel expected) {
		assertEquals(message,expected, __inject__(LogEventEntityRepository.class).getLast().getLevel());
		return this;
	}

	@Override
	public AssertionHelper assertEqualsLastLogEventLevel(LogLevel expected) {
		assertEquals(expected, __inject__(LogEventEntityRepository.class).getLast().getLevel());
		return this;
	}
	
	@Override
	public AssertionHelper assertEqualsLastLogEventMarker(String message, Object expected) {
		assertEquals(message,expected, __inject__(LogEventEntityRepository.class).getLast().getMarker());
		return this;
	}
	
	@Override
	public AssertionHelper assertEqualsLastLogEventMarker(Object expected) {
		assertEquals(expected, __inject__(LogEventEntityRepository.class).getLast().getMarker());
		return this;
	}
	
	@Override
	public AssertionHelper assertEqualsLastLogEventProperties(Properties expected) {
		if(expected.has(Properties.LOG_LEVEL))
			assertEqualsLastLogEventLevel((LogLevel) expected.get(Properties.LOG_LEVEL));
		if(expected.has(Properties.MARKER))
			assertEqualsLastLogEventMarker(expected.get(Properties.MARKER));
		if(expected.has(Properties.MESSAGE))
			assertEqualsLastLogEventMessage((String) expected.get(Properties.MESSAGE));
		return this;
	}
	
	@Override
	public AssertionHelper assertEqualsLogEventCount(String message, Object expected) {
		assertEqualsNumber(message, expected, __inject__(LogEventEntityRepository.class).countAll());
		return null;
	}
	
	@Override
	public AssertionHelper assertEqualsLogEventCount(Object expected) {
		assertEqualsNumber(expected, __inject__(LogEventEntityRepository.class).countAll());
		return null;
	}
	
	@Override
	public AssertionHelper assertContainsLastLogEventMessage(String message, String expected) {
		assertThat(__inject__(LogEventEntityRepository.class).getLastMessage()).contains(expected);
		return this;
	}
	
	@Override
	public AssertionHelper assertContainsLastLogEventMessage(String expected) {
		assertThat(__inject__(LogEventEntityRepository.class).getLastMessage()).contains(expected);
		return this;
	}
	
	@Override
	public AssertionHelper assertStartsWithLastLogEventMessage(String message, String expected) {
		assertThat(__inject__(LogEventEntityRepository.class).getLastMessage()).startsWith(expected);
		return this;
	}
	
	@Override
	public AssertionHelper assertStartsWithLastLogEventMessage(String expected) {
		assertThat(__inject__(LogEventEntityRepository.class).getLastMessage()).startsWith(expected);
		return this;
	}
}
