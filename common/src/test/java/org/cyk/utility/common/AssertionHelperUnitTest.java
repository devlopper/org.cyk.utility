package org.cyk.utility.common;

import org.cyk.utility.common.helper.AssertionHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class AssertionHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void equalsString(){
		new AssertionHelper.Assertion.Equals.Adapter.Default<Object>(Object.class,"computation result","computation result").execute();
	}
	
	@Test
	public void equalsNull(){
		new AssertionHelper.Assertion.Equals.Adapter.Default<Object>(Object.class,null,null).execute();
	}

}
