package org.cyk.utility.array;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class ArrayInstanceUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void injectTwoDimensionString_isNotNull() {
		assertionHelper.assertNotNull(__inject__(ArrayInstanceTwoDimensionString.class));
	}
	
	@Test
	public void setCellIntoTwoDimensionString() {
		ArrayInstanceTwoDimensionString arrayInstance = __inject__(ArrayInstanceTwoDimensionString.class);
		arrayInstance.setFirstDimensionElementCount(2).setSecondDimensionElementCount(3);
		arrayInstance.set(0, 0, "a");
		assertionHelper.assertEquals("a", arrayInstance.get(0, 0));
	}
}
