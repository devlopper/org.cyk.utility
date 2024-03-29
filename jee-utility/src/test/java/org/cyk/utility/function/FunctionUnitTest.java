package org.cyk.utility.function;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class FunctionUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void caller(){
		MyFunction function = __inject__(MyFunction.class);
		function.setCallerClass(FunctionUnitTest.class);
		function.setLoggable(Boolean.TRUE);
		//System.out.println("FunctionUnitTest.caller() : "+function.getLoggable());
		function.execute();
		//assertThat(instance.getIntField()).isEqualTo(2);
	}
	
	private static class MyFunction extends AbstractFunctionImpl<Object, Object> {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected Object __execute__() throws Exception {
			return null;
		}
		
	}
}
