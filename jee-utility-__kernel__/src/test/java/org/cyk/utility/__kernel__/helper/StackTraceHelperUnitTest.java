package org.cyk.utility.__kernel__.helper;

import org.cyk.utility.__kernel__.stacktrace.StackTraceHelper;
import org.cyk.utility.__kernel__.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Assert;
import org.junit.Test;

public class StackTraceHelperUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void getCurrent(){
		StackTraceElement stackTraceElement = __inject__(StackTraceHelper.class).getCurrent();
		Assert.assertEquals("org.cyk.utility.__kernel__.helper.StackTraceHelperUnitTest", stackTraceElement.getClassName());
		Assert.assertEquals("getCurrent", stackTraceElement.getMethodName());
	}
	
}
