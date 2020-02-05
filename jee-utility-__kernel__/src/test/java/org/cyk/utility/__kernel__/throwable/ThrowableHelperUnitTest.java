package org.cyk.utility.__kernel__.throwable;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class ThrowableHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void getFirstCause_null(){
		assertThat(ThrowableHelper.getFirstCause(null)).isNull();
	}
	
	@Test
	public void getFirstCause_runtimeException(){
		assertThat(ThrowableHelper.getFirstCause(new RuntimeException(new java.lang.Throwable(new RuntimeException())))).isInstanceOf(RuntimeException.class);
	}
	
	@Test
	public void getFirstCause_throwable01(){
		assertThat(ThrowableHelper.getFirstCause(new RuntimeException(new Throwable01(null)))).isInstanceOf(Throwable01.class);
	}
	
	@Test
	public void getInstanceOf_null(){
		assertThat(ThrowableHelper.getInstanceOf(null)).isNull();
	}
	
	@Test
	public void getInstanceOf_empty(){
		assertThat(ThrowableHelper.getInstanceOf(new RuntimeException(new Throwable01(new RuntimeException())))).isNull();
	}
	
	@Test
	public void getInstanceOf_t01_t02_t03_r_instanceOfRuntimeException(){
		assertThat(ThrowableHelper.getInstanceOf(new Throwable01(new Throwable02(new Throwable03(new RuntimeException()))),RuntimeException.class)).isInstanceOf(RuntimeException.class);
	}
	
	@Test
	public void getInstanceOf_t01_t02_t03_r_instanceOfT03(){
		assertThat(ThrowableHelper.getInstanceOf(new Throwable01(new Throwable02(new Throwable03(new RuntimeException()))),Throwable03.class)).isInstanceOf(Throwable03.class);
	}
	
	@Test
	public void getInstanceOf_t01_t02_t03_r_instanceOfT02(){
		assertThat(ThrowableHelper.getInstanceOf(new Throwable01(new Throwable02(new Throwable03(new RuntimeException()))),Throwable02.class)).isInstanceOf(Throwable02.class);
	}
	
	@Test
	public void getInstanceOf_t01_t02_t03_r_instanceOfT01(){
		assertThat(ThrowableHelper.getInstanceOf(new Throwable01(new Throwable02(new Throwable03(new RuntimeException()))),Throwable01.class)).isInstanceOf(Throwable01.class);
	}
	
	@Test
	public void getInstanceOf_throwable01(){
		assertThat(ThrowableHelper.getInstanceOf(new RuntimeException(new Throwable02(new Throwable03(new Throwable01(null)))),Throwable01.class)).isInstanceOf(Throwable01.class);
	}
	
	@Test
	public void getInstanceOf_throwable02(){
		assertThat(ThrowableHelper.getInstanceOf(new RuntimeException(new Throwable01(new Throwable03(new Throwable02(null)))),Throwable02.class)).isInstanceOf(Throwable02.class);
	}
	
	@Test
	public void getInstanceOf_throwable03(){
		assertThat(ThrowableHelper.getInstanceOf(new RuntimeException(new Throwable01(new Throwable02(new Throwable03(null)))),Throwable03.class)).isInstanceOf(Throwable03.class);
	}
	
	/**/
	
	public static class Throwable01 extends RuntimeException {
		private static final long serialVersionUID = 1L;
		
		public Throwable01(RuntimeException exception) {
			super(exception);
		}
		
	}
	
	public static class Throwable02 extends RuntimeException {
		private static final long serialVersionUID = 1L;
		
		public Throwable02(RuntimeException exception) {
			super(exception);
		}
		
	}
	
	public static class Throwable03 extends RuntimeException {
		private static final long serialVersionUID = 1L;
		
		public Throwable03(RuntimeException exception) {
			super(exception);
		}
		
	}
}
