package org.cyk.utility.value;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class ValueHelperUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test(expected=RuntimeException.class)
	public void returnOrThrowIfBlank_throwWhenNull() {
		__inject__(ValueHelper.class).returnOrThrowIfBlank("myval", null);
	}
	
	@Test(expected=RuntimeException.class)
	public void returnOrThrowIfBlank_throwWhenBlankString() {
		__inject__(ValueHelper.class).returnOrThrowIfBlank("myval", "");
	}
	
	@Test
	public void returnOrThrowIfBlank_throwNothing() {
		__inject__(ValueHelper.class).returnOrThrowIfBlank("myval", "a");
	}
}
