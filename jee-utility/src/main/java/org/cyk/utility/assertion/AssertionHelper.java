package org.cyk.utility.assertion;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.helper.Helper;
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
	
	AssertionHelper assertEqualsLogEventCount(String message,Object expected);
	AssertionHelper assertEqualsLogEventCount(Object expected);
	
	AssertionHelper assertEqualsLastLogEventLevel(String message,LogLevel expected);
	AssertionHelper assertEqualsLastLogEventLevel(LogLevel expected);
	
	AssertionHelper assertEqualsLastLogEventMessage(String message,String expected);
	AssertionHelper assertEqualsLastLogEventMessage(String expected);
	
	AssertionHelper assertEqualsLastLogEventMarker(String message,Object expected);
	AssertionHelper assertEqualsLastLogEventMarker(Object expected);
	
	AssertionHelper assertEqualsLastLogEventProperties(Properties expected);
}
