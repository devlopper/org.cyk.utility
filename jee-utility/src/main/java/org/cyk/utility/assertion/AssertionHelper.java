package org.cyk.utility.assertion;

public interface AssertionHelper {

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
	
	
}
