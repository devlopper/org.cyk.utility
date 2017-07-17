package org.cyk.utility.common;

import org.cyk.utility.common.helper.MethodHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class MethodHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void doTest1(){
		assertEquals("doTest1",MethodHelper.getInstance().getCurrentNameFromStackTrace());
	}
	
	/**/
	
	
}
