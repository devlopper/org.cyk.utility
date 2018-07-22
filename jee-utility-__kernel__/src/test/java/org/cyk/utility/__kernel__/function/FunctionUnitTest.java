package org.cyk.utility.__kernel__.function;

import org.cyk.utility.__kernel__.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Assert;
import org.junit.Test;

public class FunctionUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void injectNullIsNull(){
		Assert.assertNull(__inject__(null));
	}
	
}
