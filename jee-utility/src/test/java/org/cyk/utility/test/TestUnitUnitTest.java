package org.cyk.utility.test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class TestUnitUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void isThrowableCaught(){
		//TestUnit test = (TestUnit) __inject__(TestUnit.class).execute();
		//Throwable throwable = (Throwable) test.getProperties().getThrowable();
		//assertionHelper.assertEquals(ThrowableHelper.IMPLEMENTATION_OR_RUNNABLE_REQUIRED, throwable);
		//assertionHelper.assertEquals(ThrowableHelper.IMPLEMENTATION_OR_RUNNABLE_REQUIRED, __inject__(ThrowableHelper.class).getFirstCause(throwable));
	}
	
	@Test
	public void isThrowableNotCaught(){
		assertThrows(RuntimeException.class, () -> {__inject__(TestUnit.class).setIsCatchThrowable(Boolean.FALSE).execute();}); 
	}
	
}
