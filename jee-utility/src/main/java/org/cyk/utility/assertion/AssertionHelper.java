package org.cyk.utility.assertion;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.helper.Helper;
import org.cyk.utility.log.LogEventRepository;
import org.cyk.utility.log.LogLevel;

public interface AssertionHelper extends Helper {

	AssertionHelper assertNull(String message,Object object);
	AssertionHelper assertNull(Object object);
	
	AssertionHelper assertNotNull(String message,Object object);
	AssertionHelper assertNotNull(Object object);
	
	AssertionHelper assertTrue(String message,Boolean condition);
	AssertionHelper assertTrue(Boolean condition);
	
	AssertionHelper assertFalse(String message,Boolean condition);
	AssertionHelper assertFalse(Boolean condition);
	
	AssertionHelper assertEquals(String message,Object expected,Object actual);
	AssertionHelper assertEquals(Object expected,Object actual);
	<T> AssertionHelper assertEqualsByFieldValue(T expected,T actual,String...fieldNames);
	
	AssertionHelper assertNotEquals(String message,Object unexpected,Object actual);
	AssertionHelper assertNotEquals(Object unexpected,Object actual);
	
	AssertionHelper assertEqualsNumber(String message,Object expected,Object actual);
	AssertionHelper assertEqualsNumber(Object expected,Object actual);
	
	AssertionHelper assertEqualsLogEventCount(String message,Object expected, LogEventRepository<?> logEventRepository);
	AssertionHelper assertEqualsLogEventCount(Object expected, LogEventRepository<?> logEventRepository);
	
	AssertionHelper assertEqualsLastLogEventLevel(String message,LogLevel expected, LogEventRepository<?> logEventRepository);
	AssertionHelper assertEqualsLastLogEventLevel(LogLevel expected, LogEventRepository<?> logEventRepository);
	
	AssertionHelper assertEqualsLastLogEventMessage(String message,String expected, LogEventRepository<?> logEventRepository);
	AssertionHelper assertEqualsLastLogEventMessage(String expected, LogEventRepository<?> logEventRepository);
	
	AssertionHelper assertEqualsLastLogEventMarker(String message,Object expected, LogEventRepository<?> logEventRepository);
	AssertionHelper assertEqualsLastLogEventMarker(Object expected, LogEventRepository<?> logEventRepository);
	
	AssertionHelper assertEqualsLastLogEventProperties(Properties expected, LogEventRepository<?> logEventRepository);
}
