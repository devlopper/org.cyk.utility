package org.cyk.utility.number;

import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class NumberHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void lessThan___1_lt_2(){
		assertionHelper.assertTrue(__inject__(NumberHelper.class).compare(1, 2, ComparisonOperator.LT));
	}
	
}
