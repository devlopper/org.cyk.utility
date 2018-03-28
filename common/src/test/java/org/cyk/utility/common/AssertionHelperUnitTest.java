package org.cyk.utility.common;

import org.cyk.utility.common.helper.AssertionHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class AssertionHelperUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void assertNull(){
		AssertionHelper.getInstance().assertNull(null);
	}
	
	@Test
	public void assertNotNull(){
		AssertionHelper.getInstance().assertNotNull(new Object());
	}
	
	@Test
	public void assertTrue(){
		AssertionHelper.getInstance().assertTrue(Boolean.TRUE);
	}
	
	@Test
	public void assertFalse(){
		AssertionHelper.getInstance().assertFalse(Boolean.FALSE);
	}
	
	@Test
	public void assertEqualsString(){
		AssertionHelper.getInstance().assertEquals("abc", "abc");
	}
	
	@Test
	public void assertNotEqualsString(){
		AssertionHelper.getInstance().assertNotEquals("abc", "cba");
	}
	
	@Test
	public void assertEqualsNumner(){
		AssertionHelper.getInstance().assertEqualsNumber("1", "1");
		AssertionHelper.getInstance().assertEqualsNumber("1.0", "1");
		AssertionHelper.getInstance().assertEqualsNumber("1", "1.0");
		AssertionHelper.getInstance().assertEqualsNumber(1, "1");
		AssertionHelper.getInstance().assertEqualsNumber("1", 1);
		AssertionHelper.getInstance().assertEqualsNumber(1.0, "1");
		AssertionHelper.getInstance().assertEqualsNumber("1", 1.0);
	}
	
	@Test
	public void assertEqualsNull(){
		AssertionHelper.getInstance().assertEquals(null, null);
	}
	
	@Test
	public void assertNotEqualsNull(){
		AssertionHelper.getInstance().assertNotEquals(null, "a");
	}
}
