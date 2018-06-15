package org.cyk.utility.assertion;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.log.LogEventPropertyAccessor;
import org.cyk.utility.log.LogEventRepository;
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
	public AssertionHelper assertEqualsLastLogEventMessage(String message,String expected,LogEventRepository<?> logEventRepository){
		assertEquals(message,expected, __inject__(LogEventPropertyAccessor.class).getMessage(logEventRepository.getLast()));
		//assertEquals(org.apache.logging.log4j.Level.INFO, log4j2Appender.getLastEvent().getLevel());	
		return this;
	}
	
	@Override
	public AssertionHelper assertEqualsLastLogEventMessage(String expected,LogEventRepository<?> logEventRepository){
		assertEquals(expected, __inject__(LogEventPropertyAccessor.class).getMessage(logEventRepository.getLast()));
		return this;
	}

	@Override
	public AssertionHelper assertEqualsLastLogEventLevel(String message, LogLevel expected,LogEventRepository<?> logEventRepository) {
		assertEquals(message,expected, __inject__(LogEventPropertyAccessor.class).getLevel(logEventRepository.getLast()));
		return this;
	}

	@Override
	public AssertionHelper assertEqualsLastLogEventLevel(LogLevel expected, LogEventRepository<?> logEventRepository) {
		assertEquals(expected, __inject__(LogEventPropertyAccessor.class).getLevel(logEventRepository.getLast()));
		return this;
	}
	
	@Override
	public AssertionHelper assertEqualsLastLogEventMarker(String message, Object expected,LogEventRepository<?> logEventRepository) {
		assertEquals(message,expected, __inject__(LogEventPropertyAccessor.class).getMarker(logEventRepository.getLast()));
		return this;
	}
	
	@Override
	public AssertionHelper assertEqualsLastLogEventMarker(Object expected, LogEventRepository<?> logEventRepository) {
		assertEquals(expected, __inject__(LogEventPropertyAccessor.class).getMarker(logEventRepository.getLast()));
		return this;
	}
	
	@Override
	public AssertionHelper assertEqualsLastLogEventProperties(Properties expected,LogEventRepository<?> logEventRepository) {
		if(expected.has(Properties.LOG_LEVEL))
			assertEqualsLastLogEventLevel((LogLevel) expected.get(Properties.LOG_LEVEL), logEventRepository);
		if(expected.has(Properties.MARKER))
			assertEqualsLastLogEventMarker(expected.get(Properties.MARKER), logEventRepository);
		if(expected.has(Properties.MESSAGE))
			assertEqualsLastLogEventMessage((String) expected.get(Properties.MESSAGE), logEventRepository);
		return this;
	}
	
	@Override
	public AssertionHelper assertEqualsLogEventCount(String message, Object expected,LogEventRepository<?> logEventRepository) {
		assertEqualsNumber(message, expected, logEventRepository.countAll());
		return null;
	}
	
	@Override
	public AssertionHelper assertEqualsLogEventCount(Object expected,LogEventRepository<?> logEventRepository) {
		assertEqualsNumber(expected, logEventRepository.countAll());
		return null;
	}
	
}
