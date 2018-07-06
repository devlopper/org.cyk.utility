package org.cyk.utility.test.__kernel__.function;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class FunctionUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void injectNullIsNull(){
		assertionHelper.assertNull(__inject__(null));
	}
	
}
