package org.cyk.utility.clazz;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class ClassHelperImplUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void getI1ImplementationClassSimpleName() {
		assertionHelper.assertEquals("I1Impl", __inject__(ClassHelper.class).getImplementationClassSimpleName(I1.class));
	}
	
	@Test
	public void getI2ImplementationClassSimpleName() {
		assertionHelper.assertEquals("I2Impl", __inject__(ClassHelper.class).getImplementationClassSimpleName(I2.class));
	}
	
	@Test
	public void getI1ImplInterfaceSimpleName() {
		assertionHelper.assertEquals("I1", __inject__(ClassHelper.class).getInterfaceSimpleName(I1Impl.class));
	}
	
	@Test
	public void getI2ImplInterfaceSimpleName() {
		assertionHelper.assertEquals("I2", __inject__(ClassHelper.class).getInterfaceSimpleName(I2Impl.class));
	}
	
	@Test
	public void getI1ImplInterface() {
		assertionHelper.assertEquals(I1.class, __inject__(ClassHelper.class).getInterfaceByClassSimpleName(I1Impl.class));
	}
	
	@Test
	public void getI2ImplInterface() {
		assertionHelper.assertEquals(I2.class, __inject__(ClassHelper.class).getInterfaceByClassSimpleName(I2Impl.class));
	}
	
	@Test
	public void isIsNumberInteger() {
		assertionHelper.assertTrue(__inject__(ClassHelper.class).isInstanceOfNumber(Integer.class));
	}
	
	@Test
	public void isIsNumberLong() {
		assertionHelper.assertTrue(__inject__(ClassHelper.class).isInstanceOfNumber(Long.class));
	}
}
