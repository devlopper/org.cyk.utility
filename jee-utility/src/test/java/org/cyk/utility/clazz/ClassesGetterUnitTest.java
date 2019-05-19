package org.cyk.utility.clazz;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class ClassesGetterUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void get() {
		assertionHelper.assertEquals(5, __inject__(ClassesGetter.class).addPackageNames(I.class.getPackage().getName()).addBasesClasses(I.class).execute().getOutput().getSize());
	}
	
	@Test
	public void getIsInterface() {
		assertionHelper.assertEquals(3, __inject__(ClassesGetter.class).addPackageNames(I.class.getPackage().getName()).addBasesClasses(I.class)
				.setIsInterface(Boolean.TRUE).execute().getOutput().getSize());
	}
	
	@Test
	public void getIsNotInterface() {
		assertionHelper.assertEquals(2, __inject__(ClassesGetter.class).addPackageNames(I.class.getPackage().getName()).addBasesClasses(I.class)
				.setIsInterface(Boolean.FALSE).execute().getOutput().getSize());
	}
}
