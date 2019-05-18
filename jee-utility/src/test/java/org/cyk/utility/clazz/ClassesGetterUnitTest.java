package org.cyk.utility.clazz;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class ClassesGetterUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void getI1ImplementationClassSimpleName() {
		System.out.println(__inject__(ClassesGetter.class).addPackageNames(I.class.getPackage().getName()).addBasesClasses(I.class).execute().getOutput());
		//assertionHelper.assertEquals("I1Impl", __inject__(ClassHelper.class).getImplementationClassSimpleName(I1.class));
	}
	
	
}
