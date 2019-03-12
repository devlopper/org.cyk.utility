package org.cyk.utility.__kernel__.helper;

import org.cyk.utility.__kernel__.stacktrace.StackTraceHelper;
import org.cyk.utility.__kernel__.test.arquillian.AbstractArquillianUnitTest;
import org.junit.Assert;
import org.junit.Test;

public class StackTraceHelperUnitTest extends AbstractArquillianUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void getCurrent(){
		StackTraceElement stackTraceElement = __inject__(StackTraceHelper.class).getCurrent();
		Assert.assertEquals("org.cyk.utility.__kernel__.helper.StackTraceHelperUnitTest", stackTraceElement.getClassName());
		Assert.assertEquals("getCurrent", stackTraceElement.getMethodName());
	}
	
	/* Deployment*/
	
	@org.jboss.arquillian.container.test.api.Deployment
	public static org.jboss.shrinkwrap.api.spec.JavaArchive createDeployment() {
		return new org.cyk.utility.__kernel__.test.arquillian.archive.builder.JavaArchiveBuilder()
				.addPackage("org.cyk.utility.__kernel__").addPackage("org.cyk.utility.__kernel__.stacktrace")
				.execute();
	}
}
