package org.cyk.utility.common;

import org.cyk.utility.common.helper.StackTraceHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class StackTraceHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void trace(){
		System.out.println(StackTraceHelper.getInstance().getStackTraceAsString());
		System.out.println(StackTraceHelper.getInstance().getStackTraceAsString("org.cyk."));
	}
	
	/**/
	
	
}
