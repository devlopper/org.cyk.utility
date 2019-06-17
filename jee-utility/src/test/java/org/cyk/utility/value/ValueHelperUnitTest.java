package org.cyk.utility.value;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class ValueHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void returnOrThrowIfBlank_throwWhenNull() {
		assertThrows(RuntimeException.class, () -> {__inject__(ValueHelper.class).returnOrThrowIfBlank("myval", null);}); 
	}
	
	@Test
	public void returnOrThrowIfBlank_throwWhenBlankString() {
		assertThrows(RuntimeException.class, () -> {__inject__(ValueHelper.class).returnOrThrowIfBlank("myval", "");});
	}
	
	@Test
	public void returnOrThrowIfBlank_throwNothing() {
		__inject__(ValueHelper.class).returnOrThrowIfBlank("myval", "a");
	}
}
