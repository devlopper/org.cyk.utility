package org.cyk.utility.byte_;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class HashFunctionUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void hash(){
		String hash = __inject__(HashFunction.class).setBytes(__getResourceAsBytes__("myfile01.txt")).setAlgorithm("SHA-1").execute().getOutput();
		//assertionHelper.assertEquals("55D3DA3E0B15DEEF9109A6777FB07E1E8D266010", hash);
	}
	
}
