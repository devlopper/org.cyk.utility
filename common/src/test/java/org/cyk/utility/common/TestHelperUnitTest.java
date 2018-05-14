package org.cyk.utility.common;

import org.cyk.utility.common.helper.ThrowableHelper;
import org.cyk.utility.common.test.Runnable;
import org.cyk.utility.common.test.TestCase;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class TestHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void assertThrowMyBusinessExceptionNotNested(){
		TestCase testCase = new TestCase();
		testCase.assertThrowable(new Runnable() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void __run__() throws Throwable {
				throw new MyBusinessException("My_Business_Message");
			}
		}, MyBusinessException.class, null, "My_Business_Message");
	}
	/*
	@Test
	public void assertThrowMyBusinessExceptionIdentifierNotNested(){
		new Try(null,123,null){ 
			private static final long serialVersionUID = -8176804174113453706L;
			@Override protected void code() throws Throwable {
				throw new MyBusinessException("My_Business_Message").setIdentifier(123);
			}
		}.execute();	
	}
	
	@Test
	public void assertThrowMyBusinessExceptionNestedLevel1(){
		new Try(null,null,"My_Business_Message"){ 
			private static final long serialVersionUID = -8176804174113453706L;
			@Override protected void code() throws Throwable {
				throw ThrowableHelper.getInstance().getInstanceOf(new RuntimeException(new MyBusinessException("My_Business_Message")), MyBusinessException.class);
			}
		}.execute();
	}
	
	@Test
	public void assertThrowMyBusinessExceptionNestedLevel2(){
		new Try(null,null,"My_Business_Message"){ 
			private static final long serialVersionUID = -8176804174113453706L;
			@Override protected void code() throws Throwable {
				throw ThrowableHelper.getInstance().getInstanceOf(new RuntimeException(new RuntimeException(new MyBusinessException("My_Business_Message")))
						, MyBusinessException.class);
			}
		}.execute();
	}
	
	@Test
	public void assertThrowMyBusinessExceptionNestedLevel3(){
		new Try(null,null,"My_Business_Message"){ 
			private static final long serialVersionUID = -8176804174113453706L;
			@Override protected void code() throws Throwable {
				throw ThrowableHelper.getInstance().getInstanceOf(new RuntimeException(new RuntimeException(new RuntimeException(new MyBusinessException("My_Business_Message"))))
						, MyBusinessException.class);
			}
		}.execute();
	}
	*/
	/**/
	
	protected static class MyBusinessException extends ThrowableHelper.ThrowableMarkerCompileTime {
		private static final long serialVersionUID = 1L;
		public MyBusinessException(String message) {
			super(message);
		}
	}
	
}
