package org.cyk.utility.test.__kernel__.stacktrace;

import org.cyk.utility.__kernel__.stacktrace.StackTraceHelper;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class StackTraceHelperUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void getCurrent(){
		StackTraceElement stackTraceElement = __inject__(StackTraceHelper.class).getCurrent();
		assertionHelper.assertEquals("org.cyk.utility.test.__kernel__.stacktrace.StackTraceHelperUnitTest", stackTraceElement.getClassName());
		assertionHelper.assertEquals("getCurrent", stackTraceElement.getMethodName());
	}
	
}
