package org.cyk.utility.number;

import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class NumberHelperUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void lessThan___1_lt_2(){
		assertionHelper.assertTrue(__inject__(NumberHelper.class).compare(1, 2, ComparisonOperator.LT));
	}
	
}
