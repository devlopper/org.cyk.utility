package org.cyk.utility.__kernel__.function;

import java.io.Serializable;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTestPerformance;
import org.junit.jupiter.api.Test;

public class FunctionUnitTestPerformance extends AbstractWeldUnitTestPerformance {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void execute_function_tryYes_catchYes_finallyYes_isDoMonitoringYes_100000000(){
		Function function = __inject__(Function.class);
		function.setIsDoTry(Boolean.TRUE).setIsDoCatch(Boolean.TRUE).setIsDoFinally(Boolean.TRUE).setIsDoMonitoring(Boolean.TRUE);
		execute(String.format("Function try(%s) catch(%s) finally(%s) monitoring(%s)", function.getIsDoTry(),function.getIsDoCatch(),function.getIsDoFinally(),function.getIsDoMonitoring())
				, 100000000, 1500, new Runnable() {
			@Override
			public void run() {
				function.execute();
			}
		});
	}
	
	@Test
	public void execute_function_tryYes_catchYes_finallyNo_isDoMonitoringNo_100000000(){
		Function function = __inject__(Function.class);
		function.setIsDoTry(Boolean.TRUE).setIsDoCatch(Boolean.TRUE).setIsDoFinally(Boolean.FALSE).setIsDoMonitoring(Boolean.FALSE);
		execute(String.format("Function try(%s) catch(%s) finally(%s) monitoring(%s)", function.getIsDoTry(),function.getIsDoCatch(),function.getIsDoFinally(),function.getIsDoMonitoring())
				, 100000000, 900, new Runnable() {
			@Override
			public void run() {
				function.execute();
			}
		});
	}
	
	@Test
	public void execute_function_custom_100000000(){
		FunctionCustom function = __inject__(FunctionCustom.class);
		execute("Function custom", 100000000, 900, new Runnable() {
			@Override
			public void run() {
				function.execute();
			}
		});
	}
	
	@Test
	public void execute_simpleMethod_100000000(){
		Function function = __inject__(Function.class);
		function.setIsDoTry(Boolean.TRUE).setIsDoCatch(Boolean.TRUE).setIsDoFinally(Boolean.TRUE).setIsDoMonitoring(Boolean.TRUE);
		execute("simple static method", 100000000, 100, new Runnable() {
			@Override
			public void run() {
				simpleMethod();
			}
		});
	}
	
	/**/
	
	public static class Function extends AbstractFunctionImpl<Object, Object> implements Serializable {
		private static final long serialVersionUID = 1L;
		@Override
		protected Object __execute__() throws Exception {
			return null;
		}
	}
	
	public static class FunctionCustom extends AbstractFunctionImpl<Object, Object> implements Serializable {
		private static final long serialVersionUID = 1L;
		@Override
		public org.cyk.utility.__kernel__.function.Function<Object, Object> execute() {
			return null;
		}
	}
	
	public static void simpleMethod() {
		
	}
	
}
