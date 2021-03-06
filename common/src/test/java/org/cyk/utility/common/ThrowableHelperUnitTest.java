package org.cyk.utility.common;

import javax.inject.Named;
import javax.inject.Singleton;

import org.cyk.utility.common.helper.AbstractHelper;
import org.cyk.utility.common.helper.ThrowableHelper;
import org.cyk.utility.common.helper.ThrowableHelper.Throwable;
import org.cyk.utility.common.test.Runnable;
import org.cyk.utility.common.test.Try;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class ThrowableHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void getInstanceOfRuntimeException(){
		java.lang.Throwable t1 = new E1("t1", null);
		java.lang.Throwable t11 = new E2("t11", t1);
		java.lang.Throwable t111 = new E3("t111", t11);
		java.lang.Throwable t1111 = new E4("t1111", t111);
		java.lang.Throwable t11111 = new E5("t11111", t1111);
		
		assertEquals(t1, ThrowableHelper.getInstance().getInstanceOf(t1, E1.class,ThrowableHelper.ThrowableMarkerCompileTime.class,RuntimeException.class));
		assertEquals(t1, ThrowableHelper.getInstance().getInstanceOf(t11, E1.class,ThrowableHelper.ThrowableMarkerCompileTime.class,RuntimeException.class));
		assertEquals(t1, ThrowableHelper.getInstance().getInstanceOf(t111, E1.class,ThrowableHelper.ThrowableMarkerCompileTime.class,RuntimeException.class));
		assertEquals(t1, ThrowableHelper.getInstance().getInstanceOf(t1111, E1.class,ThrowableHelper.ThrowableMarkerCompileTime.class,RuntimeException.class));
		assertEquals(t1, ThrowableHelper.getInstance().getInstanceOf(t11111, E1.class,ThrowableHelper.ThrowableMarkerCompileTime.class,RuntimeException.class));
		
		assertEquals(null, ThrowableHelper.getInstance().getInstanceOf(t1, E2.class,ThrowableHelper.ThrowableMarkerCompileTime.class,RuntimeException.class));
		assertEquals(t11, ThrowableHelper.getInstance().getInstanceOf(t11, E2.class,ThrowableHelper.ThrowableMarkerCompileTime.class,RuntimeException.class));
		assertEquals(t11, ThrowableHelper.getInstance().getInstanceOf(t111, E2.class,ThrowableHelper.ThrowableMarkerCompileTime.class,RuntimeException.class));
		assertEquals(t11, ThrowableHelper.getInstance().getInstanceOf(t1111, E2.class,ThrowableHelper.ThrowableMarkerCompileTime.class,RuntimeException.class));
		assertEquals(t11, ThrowableHelper.getInstance().getInstanceOf(t11111, E2.class,ThrowableHelper.ThrowableMarkerCompileTime.class,RuntimeException.class));
		
		assertEquals(null, ThrowableHelper.getInstance().getInstanceOf(t1, E3.class,ThrowableHelper.ThrowableMarkerCompileTime.class,RuntimeException.class));
		assertEquals(null, ThrowableHelper.getInstance().getInstanceOf(t11, E3.class,ThrowableHelper.ThrowableMarkerCompileTime.class,RuntimeException.class));
		assertEquals(t111, ThrowableHelper.getInstance().getInstanceOf(t111, E3.class,ThrowableHelper.ThrowableMarkerCompileTime.class,RuntimeException.class));
		assertEquals(t111, ThrowableHelper.getInstance().getInstanceOf(t1111, E3.class,ThrowableHelper.ThrowableMarkerCompileTime.class,RuntimeException.class));
		assertEquals(t111, ThrowableHelper.getInstance().getInstanceOf(t11111, E3.class,ThrowableHelper.ThrowableMarkerCompileTime.class,RuntimeException.class));
		
		assertEquals(null, ThrowableHelper.getInstance().getInstanceOf(t1, E4.class,ThrowableHelper.ThrowableMarkerCompileTime.class,RuntimeException.class));
		assertEquals(null, ThrowableHelper.getInstance().getInstanceOf(t11, E4.class,ThrowableHelper.ThrowableMarkerCompileTime.class,RuntimeException.class));
		assertEquals(null, ThrowableHelper.getInstance().getInstanceOf(t111, E4.class,ThrowableHelper.ThrowableMarkerCompileTime.class,RuntimeException.class));
		assertEquals(t1111, ThrowableHelper.getInstance().getInstanceOf(t1111, E4.class,ThrowableHelper.ThrowableMarkerCompileTime.class,RuntimeException.class));
		assertEquals(t1111, ThrowableHelper.getInstance().getInstanceOf(t11111, E4.class,ThrowableHelper.ThrowableMarkerCompileTime.class,RuntimeException.class));
		
		assertEquals(null, ThrowableHelper.getInstance().getInstanceOf(t1, E5.class,ThrowableHelper.ThrowableMarkerCompileTime.class,RuntimeException.class));
		assertEquals(null, ThrowableHelper.getInstance().getInstanceOf(t11, E5.class,ThrowableHelper.ThrowableMarkerCompileTime.class,RuntimeException.class));
		assertEquals(null, ThrowableHelper.getInstance().getInstanceOf(t111, E5.class,ThrowableHelper.ThrowableMarkerCompileTime.class,RuntimeException.class));
		assertEquals(null, ThrowableHelper.getInstance().getInstanceOf(t1111, E5.class,ThrowableHelper.ThrowableMarkerCompileTime.class,RuntimeException.class));
		assertEquals(t11111, ThrowableHelper.getInstance().getInstanceOf(t11111, E5.class,ThrowableHelper.ThrowableMarkerCompileTime.class,RuntimeException.class));
		
		assertEquals(t1, ThrowableHelper.getInstance().getInstanceOf(t11111, E1.class,ThrowableHelper.ThrowableMarkerCompileTime.class,RuntimeException.class));
		assertEquals(t11, ThrowableHelper.getInstance().getInstanceOf(t11111, E2.class,ThrowableHelper.ThrowableMarkerCompileTime.class,RuntimeException.class));
		assertEquals(t111, ThrowableHelper.getInstance().getInstanceOf(t11111, E3.class,ThrowableHelper.ThrowableMarkerCompileTime.class,RuntimeException.class));
		assertEquals(t1111, ThrowableHelper.getInstance().getInstanceOf(t11111, E4.class,ThrowableHelper.ThrowableMarkerCompileTime.class,RuntimeException.class));
		assertEquals(t11111, ThrowableHelper.getInstance().getInstanceOf(t11111, E5.class,ThrowableHelper.ThrowableMarkerCompileTime.class,RuntimeException.class));
	}
	
	@Test
	public void getFirstCause(){
		
	}
	
	@Test
	public void executeThrowableBuilder(){
		/*ThrowableHelper.Throwable throwable = new ThrowableHelper.Throwable.Builder.Adapter.Default<RuntimeException>(RuntimeException.class)
				.addManyParameters("message1","message2","another message again","one more...").execute();
		//System.out.println(throwable);
		/*assertEquals("java.lang.Throwable: message1"+Constant.LINE_DELIMITER
				+ "message2"+Constant.LINE_DELIMITER
				+ "another message again"
				+ "one more...", new ThrowableHelper.Throwable.Builder.Adapter.Default()
				.addManyParameters("message1","message2","another message again","one more...").execute());
		*/
	}
	
	@Test
	public void assertThrowMyBusinessExceptionNotNested(){
		new Try(new Runnable() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void __run__() throws java.lang.Throwable {
				throw new MyBusinessException("My_Business_Message");
			}
		}).setExpectedThrowableClass(MyBusinessException.class).setExpectedThrowableMessage("My_Business_Message").execute();
	}
	
	@Test
	public void assertThrowMyBusinessExceptionIdentifierNotNested(){
		new Try(new Runnable() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void __run__() throws java.lang.Throwable {
				throw new MyBusinessException("My_Business_Message").setIdentifier(123);
			}
		}).setExpectedThrowableClass(MyBusinessException.class).setExpectedThrowableIdentifier(123).execute();
	}
	
	@Test
	public void assertThrowMyBusinessExceptionNestedLevel1(){
		new Try(new Runnable() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void __run__() throws java.lang.Throwable {
				throw new RuntimeException(new MyBusinessException("My_Business_Message"));
			}
		}).setExpectedThrowableClass(MyBusinessException.class).setExpectedThrowableMessage("My_Business_Message").execute();
	}
	
	@Test
	public void assertThrowMyBusinessExceptionNestedLevel2(){
		new Try(new Runnable() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void __run__() throws java.lang.Throwable {
				throw new RuntimeException(new RuntimeException(new MyBusinessException("My_Business_Message")));
			}
		}).setExpectedThrowableClass(MyBusinessException.class).setExpectedThrowableMessage("My_Business_Message").execute();
	}
	
	@Test
	public void assertThrowMyBusinessExceptionNestedLevel3(){
		new Try(new Runnable() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void __run__() throws java.lang.Throwable {
				throw new RuntimeException(new RuntimeException(new RuntimeException(new MyBusinessException("My_Business_Message"))));
			}
		}).setExpectedThrowableClass(MyBusinessException.class).setExpectedThrowableMessage("My_Business_Message").execute();
	}
	
	@Test
	public void assertThrowable(){
		Throwable throwable = new Throwable.Builder.Adapter.Default<MyBusinessException>(MyBusinessException.class).setIdentifier(159).addManyParameters("message").execute();
		assertEquals(159, throwable.getIdentifier());
	}
	
	/**/
	
	public static class MyHelper extends AbstractHelper {
		private static final long serialVersionUID = 1L;
		
	}
	
	@Singleton
	public static class MyAnnotatedHelper extends AbstractHelper {
		private static final long serialVersionUID = 1L;
	}
	
	@Named
	public static class MyAnnotatedHelper2 extends AbstractHelper {
		private static final long serialVersionUID = 1L;
	}
	
	protected static class E1 extends Exception {
		private static final long serialVersionUID = 1L;
		public E1(String message,java.lang.Throwable throwable) {
			super(message,throwable);
		}
	}
	
	protected static class E2 extends Exception {
		private static final long serialVersionUID = 1L;
		public E2(String message,java.lang.Throwable throwable) {
			super(message,throwable);
		}
	}
	
	protected static class E3 extends Exception {
		private static final long serialVersionUID = 1L;
		public E3(String message,java.lang.Throwable throwable) {
			super(message,throwable);
		}
	}
	
	protected static class E4 extends Exception {
		private static final long serialVersionUID = 1L;
		public E4(String message,java.lang.Throwable throwable) {
			super(message,throwable);
		}
	}
	
	protected static class E5 extends Exception {
		private static final long serialVersionUID = 1L;
		public E5(String message,java.lang.Throwable throwable) {
			super(message,throwable);
		}
	}
	
	protected static class MyBusinessException extends ThrowableHelper.ThrowableMarkerCompileTime {
		private static final long serialVersionUID = 1L;
		public MyBusinessException(String message) {
			super(message);
		}
	}
}
