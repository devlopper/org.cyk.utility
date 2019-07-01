package org.cyk.utility.__kernel__.function;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.Serializable;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class FunctionUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void execute(){
		MyFunction function = __inject__(MyFunction.class);
		function.execute();
		assertThat(function.try_().getBegin()).isNull();
		assertThat(function.try_().getEnd()).isNull();
		/*
		Assert.assertFalse(function.try_().begin().getRunned());
		Assert.assertFalse(function.try_().end().getRunned());
		
		Assert.assertFalse(function.catch_().begin().getRunned());
		Assert.assertFalse(function.catch_().end().getRunned());
		*/
	}
	
	@Test
	public void execute_phase_try_begin(){
		MyFunction function = __inject__(MyFunction.class);
		function.try_().begin().addRunnables(new Runnable() {	
			@Override
			public void run() {}
		});
		function.execute();
		assertThat(function.try_().getBegin()).isNotNull();
		assertThat(function.try_().getEnd()).isNull();
		assertThat(function.try_().getBegin().getRunned()).isTrue();
		assertThat(function == function.getExecutionPhaseTry().getParent()).isTrue();
		assertThat(function.getExecutionPhaseTry() == function.getExecutionPhaseTry().getBegin().getParent()).isTrue();
	}
	
	@Test
	public void execute_phase_try_end(){
		MyFunction function = __inject__(MyFunction.class);
		function.try_().end().addRunnables(new Runnable() {	
			@Override
			public void run() {}
		});
		function.execute();
		assertThat(function.try_().getBegin()).isNull();
		assertThat(function.try_().getEnd()).isNotNull();
		assertThat(function.try_().getEnd().getRunned()).isTrue();
	}
	
	@Test
	public void execute_phase_try_begin_end(){
		MyFunction function = __inject__(MyFunction.class);
		function.try_().begin().addRunnables(new Runnable() {	
			@Override
			public void run() {}
		});
		function.try_().end().addRunnables(new Runnable() {	
			@Override
			public void run() {}
		});
		function.execute();
		assertThat(function.try_().getBegin()).isNotNull();
		assertThat(function.try_().getEnd()).isNotNull();
		assertThat(function.try_().getBegin().getRunned()).isTrue();
		assertThat(function.try_().getEnd().getRunned()).isTrue();
	}
	
	@Test
	public void execute_output_is_myOutput(){
		MyFunction function = __inject__(MyFunction.class);
		function.execute();
		assertThat(function.getOutput()).isEqualTo("myOutput");
	}
	
	@Test
	public void execute_output_is_hello(){
		MyFunction function = __inject__(MyFunction.class);
		MyFunctionRunnable myFunctionRunnable = __inject__(MyFunctionRunnable.class);
		myFunctionRunnable.setRunnable(new Runnable() {
			@Override
			public void run() {
				myFunctionRunnable.getFunction().getProperties().setOutput("hello");
			}
		});
		function.try_().run().addFunctionRunnables(myFunctionRunnable);
		function.try_().setIsCodeFromFunctionExecutable(Boolean.FALSE);
		function.execute();
		assertThat(function.getOutput()).isEqualTo("hello");
	}
	
	//@Test
	public void execute_MyFunctionAll_1000000(){
		for(int index = 0 ; index < 10000000 ; index = index + 1)
			__inject__(MyFunctionAll.class).execute();
	}
	
	//@Test
	public void execute_MyFunctionAll_1000000_tryOnly(){
		for(int index = 0 ; index < 10000000 ; index = index + 1)
			__inject__(MyFunctionAll.class).setIsDoMonitoring(Boolean.FALSE).setIsDoTry(Boolean.TRUE).setIsDoCatch(Boolean.TRUE).setIsDoFinally(Boolean.FALSE).execute();
	}
	
	//@Test
	public void execute_mySimpleMethod_1000000(){
		for(int index = 0 ; index < 10000000 ; index = index + 1)
			mySimpleMethod();
	}
	
	/**/
	
	public static class MyFunction extends AbstractFunctionImpl<Object, Object> implements Serializable {
		private static final long serialVersionUID = 1L;
		@Override
		protected Object __execute__() throws Exception {
			return "myOutput";
		}
	}
	
	public static class MyFunctionRunnable extends AbstractFunctionRunnableImpl<MyFunction> implements Serializable {
		private static final long serialVersionUID = 1L;
			
	}
	
	public static class MyFunctionAll extends AbstractFunctionImpl<Object, Object> implements Serializable {
		private static final long serialVersionUID = 1L;
		@Override
		protected Object __execute__() throws Exception {
			return "myOutput";
		}
	}
	
	public static void mySimpleMethod() {
		
	}
	
}
