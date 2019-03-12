package org.cyk.utility.__kernel__.function;

import java.io.Serializable;

import org.cyk.utility.__kernel__.test.arquillian.AbstractArquillianUnitTest;
import org.junit.Assert;
import org.junit.Test;

public class FunctionUnitTest extends AbstractArquillianUnitTest {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void execute(){
		MyFunction function = __inject__(MyFunction.class);
		function.execute();
		Assert.assertNull(function.try_().getBegin());
		Assert.assertNull(function.try_().getEnd());
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
		Assert.assertNotNull(function.try_().getBegin());
		Assert.assertNull(function.try_().getEnd());
		Assert.assertTrue(function.try_().getBegin().getRunned());
		
		Assert.assertEquals(function, function.getExecutionPhaseTry().getParent());
		Assert.assertEquals(function.getExecutionPhaseTry(), function.getExecutionPhaseTry().getBegin().getParent());
	}
	
	@Test
	public void execute_phase_try_end(){
		MyFunction function = __inject__(MyFunction.class);
		function.try_().end().addRunnables(new Runnable() {	
			@Override
			public void run() {}
		});
		function.execute();
		Assert.assertNull(function.try_().getBegin());
		Assert.assertNotNull(function.try_().getEnd());
		Assert.assertTrue(function.try_().getEnd().getRunned());
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
		Assert.assertNotNull(function.try_().getBegin());
		Assert.assertNotNull(function.try_().getEnd());
		Assert.assertTrue(function.try_().getBegin().getRunned());
		Assert.assertTrue(function.try_().getEnd().getRunned());
	}
	
	@Test
	public void execute_output_is_myOutput(){
		MyFunction function = __inject__(MyFunction.class);
		function.execute();
		Assert.assertEquals("myOutput",function.getOutput());
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
		Assert.assertEquals("hello",function.getOutput());
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
	
	/* Deployment*/
	
	@org.jboss.arquillian.container.test.api.Deployment
	public static org.jboss.shrinkwrap.api.spec.JavaArchive createDeployment() {
		return new org.cyk.utility.__kernel__.test.arquillian.archive.builder.JavaArchiveBuilder()
				.addPackage("org.cyk.utility.__kernel__").addPackage("org.cyk.utility.__kernel__.function")
				.execute();
	}
}
