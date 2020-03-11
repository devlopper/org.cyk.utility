package org.cyk.utility.test;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class SetupUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void setup(){
		System.out.println(__inject__(TestUnit.class));
	}
	
}
