package org.cyk.utility.__kernel__.helper;
import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.stacktrace.StackTraceHelper;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class StackTraceHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void getCurrentClassName(){
		assertThat(__inject__(StackTraceHelper.class).getCurrent().getClassName()).asString().startsWith(StackTraceHelper.class.getName());
	}
	
	@Test
	public void getCurrentMethodName(){
		assertThat(__inject__(StackTraceHelper.class).getCurrent().getMethodName()).asString().startsWith("getCurrent");
	}
	
	@Test
	public void getCallerMethodName01(){
		assertThat(__inject__(StackTraceHelper.class).getAt(3).getMethodName()).asString().isEqualTo("getCallerMethodName01");
	}
	
	@Test
	public void getCallerMethodName02(){
		assertThat(__inject__(StackTraceHelper.class).getCallerMethodName()).asString().isEqualTo("getCallerMethodName02");
	}
	
}
