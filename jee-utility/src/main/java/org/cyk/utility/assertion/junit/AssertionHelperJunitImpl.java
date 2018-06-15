package org.cyk.utility.assertion.junit;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.assertion.AbstractAssertionHelperImpl;
import org.cyk.utility.assertion.AssertionHelper;
import org.junit.Assert;

@Singleton
public class AssertionHelperJunitImpl extends AbstractAssertionHelperImpl implements AssertionHelperJunit,Serializable {
	private static final long serialVersionUID = 1L;
	
	public AssertionHelper assertEquals(String message,Object expected,Object actual){
		Assert.assertEquals(message, expected, actual);
		return this;
	}
	
	public AssertionHelper assertEquals(Object expected,Object actual){
		Assert.assertEquals(expected, actual);
		return this;
	}

	@Override
	public AssertionHelper assertNull(String message, Object object) {
		Assert.assertNull(message, object);
		return this;
	}

	@Override
	public AssertionHelper assertNull(Object object) {
		Assert.assertNull(object);
		return this;
	}

	@Override
	public AssertionHelper assertNotNull(String message, Object object) {
		Assert.assertNotNull(message, object);
		return this;
	}

	@Override
	public AssertionHelper assertNotNull(Object object) {
		Assert.assertNotNull(object);
		return this;
	}

	@Override
	public AssertionHelper assertTrue(String message, Boolean condition) {
		Assert.assertTrue(message, condition);
		return this;
	}

	@Override
	public AssertionHelper assertTrue(Boolean condition) {
		Assert.assertTrue(condition);
		return this;
	}

	@Override
	public AssertionHelper assertFalse(String message, Boolean condition) {
		Assert.assertFalse(message, condition);
		return this;
	}

	@Override
	public AssertionHelper assertFalse(Boolean condition) {
		Assert.assertFalse(condition);
		return this;
	}

	@Override
	public <T> AssertionHelper assertEqualsByFieldValue(T expected, T actual, String... fieldNames) {
		throw new RuntimeException("not yet implemented");
		//return this;
	}

	@Override
	public AssertionHelper assertNotEquals(String message, Object unexpected, Object actual) {
		Assert.assertNotEquals(message, unexpected, actual);
		return this;
	}

	@Override
	public AssertionHelper assertNotEquals(Object unexpected, Object actual) {
		Assert.assertNotEquals(unexpected, actual);
		return this;
	}

}
