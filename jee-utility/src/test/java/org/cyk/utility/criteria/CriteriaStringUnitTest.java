package org.cyk.utility.criteria;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class CriteriaStringUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void createNull(){
		Criteria criteria = __inject__(Criteria.class);
		assertionHelper.assertNull(criteria.getValuesMatch());
	}
	
}
