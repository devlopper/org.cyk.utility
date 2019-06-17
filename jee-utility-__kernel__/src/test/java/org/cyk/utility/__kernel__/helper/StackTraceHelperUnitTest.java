package org.cyk.utility.__kernel__.helper;
import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.stacktrace.StackTraceHelper;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class StackTraceHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void getCurrent(){
		StackTraceElement stackTraceElement = __inject__(StackTraceHelper.class).getCurrent();
		assertThat(stackTraceElement.getClassName()).isEqualTo("org.cyk.utility.__kernel__.helper.StackTraceHelperUnitTest");
		assertThat(stackTraceElement.getMethodName()).isEqualTo("getCurrent");
	}
	
}
