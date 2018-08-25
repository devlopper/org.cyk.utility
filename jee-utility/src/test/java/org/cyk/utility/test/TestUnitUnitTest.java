package org.cyk.utility.test;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class TestUnitUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void isThrowableCaught(){
		//TestUnit test = (TestUnit) __inject__(TestUnit.class).execute();
		//Throwable throwable = (Throwable) test.getProperties().getThrowable();
		//assertionHelper.assertEquals(ThrowableHelper.IMPLEMENTATION_OR_RUNNABLE_REQUIRED, throwable);
		//assertionHelper.assertEquals(ThrowableHelper.IMPLEMENTATION_OR_RUNNABLE_REQUIRED, __inject__(ThrowableHelper.class).getFirstCause(throwable));
	}
	
	@Test(expected=RuntimeException.class)
	public void isThrowableNotCaught(){
		__inject__(TestUnit.class).setIsCatchThrowable(Boolean.FALSE).execute();
	}
	
}
